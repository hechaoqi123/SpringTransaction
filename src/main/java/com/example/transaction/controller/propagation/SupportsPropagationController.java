package com.example.transaction.controller.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import com.example.transaction.service.propagation.SupportsPropagationService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务Propagation.SUPPORTS级别的传播机制
 *
 * @author hcq
 * @date 2020/4/25 15:59
 */
@RestController
@RequestMapping("/student/propagation/supports")
@AllArgsConstructor
@Slf4j
public class SupportsPropagationController {

    private StudentMapper studentMapper;
    private SupportsPropagationService supportsPropagationService;

    /**
     * 结论1：不存在事务则以非事务方式执行
     */
    @RequestMapping("/noExistTransaction")
    public String noExistTransaction() {
        Student stu = new Student(IdUtils.get32UUID(), "张三", 22, "男");
        supportsPropagationService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }

    /**
     * 结论2：若存在事务则在原事务中运行
     */
    @RequestMapping("/existTransaction")
    @Transactional(rollbackFor = {})
    public String existTransaction() {
        studentMapper.insert(new Student("test002","小丽",22,"女"));
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        try {
            supportsPropagationService.saveRecordAndThrowRuntimeException(stu);
        }catch (Exception e){
            log.info("捕获异常，防止其影响观察结果！");
        }
        return "SUCCESS";
    }
}
