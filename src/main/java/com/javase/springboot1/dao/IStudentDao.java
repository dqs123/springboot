package com.javase.springboot1.dao;

/**
 * @Author: dqs
 * @Date: Created on 14:43 2018/8/3
 */

import com.javase.springboot1.entity.Student;

import java.util.List;

/**
 * 创建学生的dao接口
 */
public interface IStudentDao {
    /**
     * 查询学生信息
     * @param student
     * @return
     */
    List<Student> queryById(Student student);

    /**
     * 添加学生
     * @param student
     * @return
     */
    int addStudent(Student student);
}
