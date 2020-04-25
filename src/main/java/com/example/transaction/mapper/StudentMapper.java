package com.example.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.transaction.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Student Mapper
 * 懒得写sql，所以这里直接用MybatisPlus来操作数据库了
 * @author hcq
 * @date 2020/4/24 22:23
 */
@Repository
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
