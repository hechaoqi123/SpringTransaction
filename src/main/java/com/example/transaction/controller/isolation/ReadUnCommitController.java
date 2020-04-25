package com.example.transaction.controller.isolation;

import com.alibaba.fastjson.JSONObject;
import com.example.transaction.entity.Student;
import com.example.transaction.service.isolation.ReadUnCommitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于模拟读未提交（READ_UNCOMMITTED）隔离级别下的脏读现象
 * 此隔离级别下会出现的问题：脏读、幻读、不可重复读
 * @author hcq
 * @date 2020/4/25 18:19
 */
@RestController
@RequestMapping("/student/isolation")
@AllArgsConstructor
public class ReadUnCommitController {

    private ReadUnCommitService readUnCommitService;

    /**
     * 模拟脏读的现象
     */
    @RequestMapping("/readUnCommit")
    public Student readUnCommit(){
        // 从数据库中查找id等于1943b39d5d684c60895614e3d4f7357f的学生信息
        String stuId="1943b39d5d684c60895614e3d4f7357f";
        return readUnCommitService.getStudentById(stuId);
    }

    /**
     * 可以先请求这个接口，然后3分钟内请求readUnCommit模拟脏读
     * 也可以直接通过数据库开启事务，然后请求readUnCommit接口模拟脏读
     */
    @RequestMapping("/saveRecord")
    public String saveRecord(){
        Student stu=new Student("1943b39d5d684c60895614e3d4f7357f","小王",22,"男");
        readUnCommitService.saveRecordAndThrowRuntimeException(stu);
        return "SUCCESS";
    }


}
