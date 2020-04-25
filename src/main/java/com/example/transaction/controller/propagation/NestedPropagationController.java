package com.example.transaction.controller.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import com.example.transaction.service.propagation.NestedPropagationService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务Propagation.NESTED级别的传播机制
 * @author hcq
 * @date 2020/4/25 17:05
 */
@RestController
@RequestMapping("/student/propagation/nested")
@AllArgsConstructor
@Slf4j
public class NestedPropagationController {

    private StudentMapper studentMapper;
    private NestedPropagationService nestedPropagationService;

    /**
     * 结论1：不存在事务时，开启一个事务（此级别非常类似于REQUIRED_NEW）
     */
    @RequestMapping("/noExistTransaction")
    public String noExistTransaction() {
        Student stu = new Student(IdUtils.get32UUID(), "张三", 22, "男");
        nestedPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     * 结论2：存在事务时，作为嵌套的事务去执行
     * 此场景也类似于REQUIRED_NEW
     */
    @RequestMapping("/existTransaction")
    @Transactional(rollbackFor = {})
    public String existTransaction() {
        studentMapper.insert(new Student("test007","小于",22,"女"));
        Student stu=new Student(IdUtils.get32UUID(),"小王",22,"男");
        try {
            nestedPropagationService.saveRecordAndThrowRuntimeException(stu);
        }catch (Exception e){
            log.info("捕获异常，防止其影响观察结果！");
        }
        return "SUCCESS";
    }

    /**
     * 验证死锁问题、
     * 执行结果显示
     *  1、内部嵌套事务可以读到外部事务未提交的数据，所以避免了REQUIRED_NEW级别下的死锁问题
     */
    @RequestMapping("/dieLock")
    @Transactional(rollbackFor = {})
    public String dieLock() {
        Student stu=new Student(IdUtils.get32UUID(),"小王",22,"男");
        studentMapper.insert(stu);
        try {
         nestedPropagationService.saveRecordAndThrowRuntimeException(stu);
        }catch (Exception e){
            log.info("捕获异常，防止其影响观察结果！");
        }
        return "SUCCESS";
    }

    /**
     * 因为内部事务可以读到外部事务未提交的数据，所以我很怀疑其实是同一个事务
     * 验证是否是同一事务？
     * 执行结果显示：
     * 内部是否和外部事务并不是同一个事务，否则此场景将会导致外部事务无法提交
     */
    @RequestMapping("/notTheSame")
    @Transactional(rollbackFor = {})
    public String notTheSame() {
        Student stu=new Student(IdUtils.get32UUID(),"小王",22,"男");
        studentMapper.insert(stu);
        try {
            nestedPropagationService.modifyRecordAndThrowRuntimeException(stu);
        }catch (Exception e){
            log.info("捕获异常，防止其影响观察结果！");
        }
        return "SUCCESS";
    }
}
