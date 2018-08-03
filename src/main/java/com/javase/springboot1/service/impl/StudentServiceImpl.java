package com.javase.springboot1.service.impl;

import com.javase.springboot1.dao.IStudentDao;
import com.javase.springboot1.entity.Student;
import com.javase.springboot1.service.IStudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: dqs
 * @Date: Created on 15:10 2018/8/3
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private IStudentDao studentDao;

    @Override
    public List<Student> queryById(Student student) {
        return studentDao.queryById(student);
    }

    @Override
    //开启事物(只在增删改上操作即可)
    @Transactional
    public int addStudent(Student student) {
        int addStudent = studentDao.addStudent(student);
        //模拟服务层中遇到的错误,让事物回滚
        Integer.parseInt("a");
        return addStudent;
    }
}
