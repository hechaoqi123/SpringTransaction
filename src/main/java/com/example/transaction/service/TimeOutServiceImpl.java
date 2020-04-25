package com.example.transaction.service;

import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import com.example.transaction.service.TimeOutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 验证Spring事务的超时时间
 * @author hcq
 * @date 2020/4/24 22:26
 */
@Service
@AllArgsConstructor
public class TimeOutServiceImpl implements TimeOutService {

    private  StudentMapper studentMapper;

    /**
     * 采用@Transactional注解提供的超时时间
     *  事务超时后，仅代表不能执行sql，不代表不能提交
     *  也就是说如果在事务超时之前此事务中的sql已经被全部执行完毕，那么这个事务就可以被提交
     * @throws  TransactionTimedOutException 事务超时后执行sql将抛出此异常
     * @param student 学生信息
     */
    @Override
    @Transactional(timeout = 2,rollbackFor = {})
    public void saveRecordBySpringTimeOut(Student student) {
        // 线程等待三秒，模拟Spring事务超时的场景
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        studentMapper.insert(student);
    }

}
