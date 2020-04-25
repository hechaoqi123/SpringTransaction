package com.example.transaction.service;

import com.example.transaction.entity.Student;

/**
 * 验证事务的time out属性
 * @author hcq
 * @date 2020/4/24 22:26
 */
public interface TimeOutService {

    /**
     * 保存学生信息：用于验证Spring事务的超时时间
     * @param student 学生信息
     */
    void saveRecordBySpringTimeOut(Student student);

}
