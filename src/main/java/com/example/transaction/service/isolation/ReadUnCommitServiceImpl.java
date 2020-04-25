package com.example.transaction.service.isolation;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hcq
 * @date 2020/4/25 18:20
 */
@Service
@AllArgsConstructor
public class ReadUnCommitServiceImpl implements ReadUnCommitService {

    private StudentMapper studentMapper;

    @Override
    @Transactional( isolation = Isolation.READ_UNCOMMITTED,rollbackFor = {})
    public Student getStudentById(String stuId) {
        return  studentMapper.selectById(stuId);
    }

    @Override
    @Transactional( isolation = Isolation.READ_UNCOMMITTED,rollbackFor = {})
    public void saveRecordAndThrowRuntimeException(Student student) {

        studentMapper.insert(student);
        // 线程休眠2分钟，用于读未提交的数据
        try {
            Thread.sleep(2*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 防止垃圾数据，所以此处直接给回滚掉了
        throw new RuntimeException("抛出运行时异常");
    }

}
