package com.example.transaction.controller;

import com.example.transaction.entity.Student;
import com.example.transaction.service.TimeOutService;
import com.example.transaction.util.IdUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于验证事务超时的场景
 * @author hcq
 * @date 2020/4/24 22:49
 */
@RestController
@RequestMapping("/student/timeOut")
@AllArgsConstructor
public class TimeOutController {

    private TimeOutService timeOutService;

    /**
     * Spring事务超时回滚
     */
    @RequestMapping("/springTimeOut")
    public String  springTimeOut(){
        Student stu=new Student(IdUtils.get32UUID(),"张三",22,"男");
        timeOutService.saveRecordBySpringTimeOut(stu);
        return "SUCCESS";
    }

}
