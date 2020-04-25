package com.example.transaction.service.propagation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于验证事务Propagation.NOT_SUPPORTED级别的传播机制
 *
 * @author hcq
 * @date 2020/4/25 11:25
 */
@Service
@AllArgsConstructor
public class NotSupportsPropagationServiceImpl implements NotSupportPropagationService {

    private StudentMapper studentMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = {})
    public void saveRecordAndThrowRuntimeException(Student student) {
        studentMapper.insert(student);
        throw new RuntimeException("抛出运行时异常");
    }
}
