package com.example.transaction.service;

import com.example.transaction.entity.Student;

import java.io.IOException;

/**
 * 用于验证事务不生效的场景
 * @author hcq
 * @date 2020/4/25 11:21
 */
public interface TransactionService {

    /**
     * 非事务方法调用事务方法
     * @param student 学生信息
     */
    void innerCall(Student student);

    /**
     * 保存学生信息 并抛出一个运行时异常
     * @param student 学生信息
     */
    void saveRecordAndThrowRuntimeException(Student student);

    /**
     * 保存学生信息，抛出检查异常checkedException（也叫做编译时异常）
     * @param stu 学生信息
     * @throws IOException IO异常
     */
    void checkedException(Student stu) throws IOException;

    /**
     * 保存学生信息，抛出检查异常checkedException并回滚（也叫做编译时异常）
     * @param stu 学生信息
     * @throws IOException IO异常
     */
    void checkedExceptionAndRollBack(Student stu) throws IOException;
}
