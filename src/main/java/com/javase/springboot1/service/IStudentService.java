package com.javase.springboot1.service;

import com.javase.springboot1.entity.Student;

import java.util.List;

/**
 * @Author: dqs
 * @Date: Created on 15:09 2018/8/3
 */

/**
 * 创建学生的服务层
 */
public interface IStudentService {
    /**
     * 查询学生信息
     *
     * @param student
     * @return
     */
    List<Student> queryStudent(Student student);

    /**
     * 查询单个学生的信息
     *
     * @param id
     * @return
     */
    Student queryById(Integer id);

    /**
     * 增加学生
     *
     * @param student
     * @return
     */
    int addStudent(Student student);

}
