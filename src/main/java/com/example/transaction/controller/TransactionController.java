package com.example.transaction.controller;

import com.example.transaction.entity.Student;
import com.example.transaction.service.TransactionService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 用于验证事务不生效的场景
 * @author hcq
 * @date 2020/4/24 22:27
 */
@RestController
@RequestMapping("/student/transaction")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    /**
     * 非事务方法内部调用 事务不生效
     */
    @RequestMapping("/innerCall")
    public String  innerCall(){
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        transactionService.innerCall(stu);
        return "SUCCESS";
    }

    /**
     * 检查异常（Checked Exception）事务不回滚
     */
    @RequestMapping("/checkedException")
    public String  checkedException() throws IOException {
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        transactionService.checkedException(stu);
        return "SUCCESS";
    }

    /**
     * 检查异常（Checked Exception）事务回滚
     */
    @RequestMapping("/checkedExceptionAndRollBack")
    public String  checkedExceptionAndRollBack() throws IOException {
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        transactionService.checkedExceptionAndRollBack(stu);
        return "SUCCESS";
    }
}
