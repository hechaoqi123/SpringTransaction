package com.example.transaction.controller.isolation;

import com.alibaba.fastjson.JSONObject;
import com.example.transaction.entity.Student;
import com.example.transaction.service.isolation.ReadCommitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用于模拟读已提交（READ_COMMITTED）隔离级别下的不可重复读、幻读现象
 * 此隔离级别下会出现的问题：幻读、不可重复读
 * @author hcq
 * @date 2020/4/25 18:19
 */
@RestController
@RequestMapping("/student/isolation")
@AllArgsConstructor
public class ReadCommitController {

    private ReadCommitService readCommitService;

    /**
     * 模拟不可重复读的现象
     */
    @RequestMapping("/readCommit")
    public Student readUnCommit(){
        // 从数据库中查找id等于1943b39d5d684c60895614e3d4f7357f的学生信息
        String stuId="1943b39d5d684c60895614e3d4f7357f";
        return readCommitService.repetitionQueryById(stuId);
    }

    /**
     * 模拟幻读
     */
    @RequestMapping("/phantomRead")
    public List<Student> phantom(){
        Student stu=new Student("1943b39d5d684c60895614e3d4f7357f","小王",22,"男");
        return readCommitService.modifyAllStudentAndQueryAll(stu);
    }

}
