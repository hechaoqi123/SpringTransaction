package com.example.transaction.controller.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import com.example.transaction.service.propagation.NotSupportPropagationService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务Propagation.NOT_SUPPORTED级别的传播机制
 * @author hcq
 * @date 2020/4/25 16:27
 */
@RestController
@RequestMapping("/student/propagation/notSupported")
@AllArgsConstructor
@Slf4j
public class NotSupportedPropagationController {

    private StudentMapper studentMapper;
    private NotSupportPropagationService notSupportPropagationService;

    /**
     * 结论1：不存在事务则以非事务方式执行
     */
    @RequestMapping("/noExistTransaction")
    public String noExistTransaction() {
        Student stu = new Student(IdUtils.get32UUID(), "张三", 22, "男");
        notSupportPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     * 结论2：存在事务则将当前事务挂起，以非事务的方式去执行
     */
    @RequestMapping("/existTransaction")
    @Transactional(rollbackFor = {})
    public String existTransaction() {
        studentMapper.insert(new Student("test005","小于",22,"女"));
        Student stu=new Student(IdUtils.get32UUID(),"小王",22,"男");
        notSupportPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     *  此级别下存在死锁的问题
     */
    @RequestMapping("/dieLock")
    @Transactional(rollbackFor = {})
    public String dieLock() {
        Student stu=new Student(IdUtils.get32UUID(),"小王",22,"男");
        studentMapper.insert(stu);
        notSupportPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }
}
