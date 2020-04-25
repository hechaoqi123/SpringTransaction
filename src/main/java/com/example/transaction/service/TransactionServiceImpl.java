package com.example.transaction.service;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 用于验证事务不生效的场景
 * @author hcq
 * @date 2020/4/25 11:22
 */
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private StudentMapper studentMapper;

    @Override
    public void innerCall(Student student) {
        saveRecordAndThrowRuntimeException(student);
    }

    @Override
    @Transactional(rollbackFor = {})
    public void saveRecordAndThrowRuntimeException(Student student){
        studentMapper.insert(student);
        throw new RuntimeException("抛出运行时异常");
    }

    @Override
    @Transactional(rollbackFor = {})
    public void checkedException(Student stu) throws IOException {
        studentMapper.insert(stu);
        throw new IOException("找不到XXX.xml");
    }

    /**
     * 指明IOException异常进行回滚
     * 此处需要注意的是不能在本方法中把IOException异常catch掉，否则也会导致事务无法回滚
     * @param stu 学生信息
     * @throws IOException IO异常
     */
    @Override
    @Transactional(rollbackFor = {IOException.class})
    public void checkedExceptionAndRollBack(Student stu) throws IOException {
        checkedException(stu);
    }
}
