package com.example.transaction.service.propagation;

import com.example.transaction.entity.Student;

/**
 * 用于验证事务Propagation.MANDATORY级别的传播机制
 * @author hcq
 * @date 2020/4/25 11:25
 */
public interface MandatoryPropagationService {

    /**
     *  保存学生信息并抛出运行时异常
     * @param student 学生信息
     */
    void saveRecordAndThrowRuntimeException(Student student);
}
