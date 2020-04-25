package com.example.transaction.controller.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.service.propagation.NeverPropagationService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务Propagation.NEVER级别的传播机制
 * @author hcq
 * @date 2020/4/25 16:43
 */
@RestController
@RequestMapping("/student/propagation/never")
@AllArgsConstructor
@Slf4j
public class NeverPropagationController {

    private NeverPropagationService neverPropagationService;

    /**
     * 结论1：不存在事务时，以非事务的方式去执行
     */
    @RequestMapping("/noExistTransaction")
    public String noExistTransaction() {
        Student stu = new Student(IdUtils.get32UUID(), "张三", 22, "男");
        neverPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     * 结论2：存在事务时抛出异常
     */
    @RequestMapping("/existTransaction")
    @Transactional(rollbackFor = {})
    public String existTransaction() {
        Student stu=new Student(IdUtils.get32UUID(),"小王",22,"男");
        neverPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

}
