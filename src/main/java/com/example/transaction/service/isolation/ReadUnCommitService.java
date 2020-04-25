package com.example.transaction.service.isolation;

import com.example.transaction.entity.Student;

/**
 * @author hcq
 * @date 2020/4/25 18:20
 */
public interface ReadUnCommitService {
    /**
     * 通过学号查询学生信息
     * @param stuId 学生学号
     * @return 学生信息
     */
    Student getStudentById(String stuId);

    /**
     *  保存学生信息并抛出运行时异常
     * @param student 学生信息
     */
    void saveRecordAndThrowRuntimeException(Student student);
}
