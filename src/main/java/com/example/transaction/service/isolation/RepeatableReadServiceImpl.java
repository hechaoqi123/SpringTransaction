package com.example.transaction.service.isolation;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.transaction.entity.Student;
import com.example.transaction.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hcq
 * @date 2020/4/25 18:20
 */
@Service
@AllArgsConstructor
@Slf4j
public class RepeatableReadServiceImpl implements RepeatableReadService {

    private StudentMapper studentMapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {})
    @SuppressWarnings("All")
    public Student repetitionQueryById(String stuId) {
        Student stu = studentMapper.selectById(stuId);
        log.info("第一次读到的数据为:{}", JSONObject.toJSONString(stu));

        // 别问我为啥要休眠30秒，因为我要在数据库改数据提交事务呀
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 别问我为啥不直接selectById，还不是因为这令人难受的mybatis一级缓存
        stu = studentMapper.selectList(new QueryWrapper<>()).get(0);
        log.info("第二次读到的数据为:{}", JSONObject.toJSONString(stu));
        return stu;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {})
    public List<Student> modifyAllStudentAndQueryAll(Student stu) {
        // 更改表中所有的表数据
        studentMapper.update(stu, new UpdateWrapper<>());
        // 休眠30秒，因为我要在数据库添加数据提交事务
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 返回表中的所有数据
        return studentMapper.selectList(new QueryWrapper<>());
    }

}
