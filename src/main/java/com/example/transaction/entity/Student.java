package com.example.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库实体
 * @author hcq
 * @date 2020/4/24 22:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    /**
     * 学号、主键
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;
}
