package com.example.transaction.service.isolation;

import com.example.transaction.entity.Student;

import java.util.List;

/**
 * @author hcq
 * @date 2020/4/25 18:20
 */
public interface ReadCommitService {
    /**
     * 通过学号查询学生信息
     * @param stuId 学生学号
     * @return 学生信息
     */
    Student repetitionQueryById(String stuId);

    /**
     * 幻读
     * 先更新表中所有的数据，然后再查询表中所有的数据
     */
    List<Student> modifyAllStudentAndQueryAll(Student stu);

}
