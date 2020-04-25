package com.example.transaction.controller.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import com.example.transaction.service.propagation.RequiredPropagationService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务Propagation.REQUIRED级别的传播机制
 *  Propagation.REQUIRED 是默认的传播机制
 * @author hcq
 * @date 2020/4/24 22:34
 */
@RestController
@RequestMapping("/student/propagation/required")
@AllArgsConstructor
@Slf4j
public class RequiredPropagationController {

    private StudentMapper studentMapper;
    private RequiredPropagationService requiredPropagationService;

    /**
     * 结论1：不存在事务则创建一个事务 （经常被使用到的场景）
     */
    @RequestMapping("/noExistTransaction")
    public String noExistTransaction(){
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        requiredPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     * 结论2：若存在事务则在原事务中运行 （经常被使用到的场景）
     *
     *  通过观察test001是否被回滚？可以验证Controller中与Service中的是否是同一个事务
     *  若test001被回滚则说明是同一个事务
     *  若test001未回滚则表示不是同一个事务
     */
    @RequestMapping("/existTransaction")
    @Transactional(rollbackFor = {})
    public String  existTransaction(){
        studentMapper.insert(new Student("test001","小红",22,"女"));
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        try {
            requiredPropagationService.saveRecordAndThrowRuntimeException(stu);
        }catch (Exception e){
           log.info("捕获异常，防止其影响观察结果！");
        }
        return "SUCCESS";
    }
}
