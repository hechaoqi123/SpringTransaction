package com.example.transaction.controller.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import com.example.transaction.service.propagation.MandatoryPropagationService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务Propagation.MANDATORY级别的传播机制
 * @author hcq
 * @date 2020/4/25 16:19
 */
@RestController
@RequestMapping("/student/propagation/mandatory")
@AllArgsConstructor
@Slf4j
public class MandatoryPropagationController {

    private StudentMapper studentMapper;
    private MandatoryPropagationService mandatoryPropagationService;

    /**
     * 结论1：不存在事务抛出异常
     */
    @RequestMapping("/noExistTransaction")
    public String noExistTransaction() {
        Student stu = new Student(IdUtils.get32UUID(), "张三", 22, "男");
        mandatoryPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     * 结论2：若存在事务则在原事务中运行
     */
    @RequestMapping("/existTransaction")
    @Transactional(rollbackFor = {})
    public String existTransaction() {
        studentMapper.insert(new Student("test003","小周",22,"女"));
        Student stu=new Student(IdUtils.get32UUID(),"小罗",22,"男");
        try {
            mandatoryPropagationService.saveRecordAndThrowRuntimeException(stu);
        }catch (Exception e){
            log.info("捕获异常，防止其影响观察结果！");
        }
        return "SUCCESS";
    }
}
