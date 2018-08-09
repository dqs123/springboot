package com.javase.springboot1.service.impl;

import com.javase.springboot1.contants.RedisContants;
import com.javase.springboot1.dao.IStudentDao;
import com.javase.springboot1.entity.Student;
import com.javase.springboot1.service.IStudentService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dqs
 * @Date: Created on 15:10 2018/8/3
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private IStudentDao studentDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Student queryById(Integer id) {
        /**
         * 1.先从缓存中查找
         * 2.
         *    (1)如果存在,返回给页面
         *    (2)如果不存在从数据库中查找
         */
        String key = RedisContants.STUDENT_INFO + id;
        Object o = redisTemplate.opsForValue().get(key);
        if (o != null) {//如果redis中有信息,直接返回
            return (Student) o;
        } else {
            //从数据库查询学生的单条信息
            Student student = new Student();
            student.setId(id);
            List<Student> byField = studentDao.queryStudent(student);
            if (byField.size() > 0) {
                Student student1 = byField.get(0);
                //将查询结果缓存至redis,并返回(方便下一次查询)
                redisTemplate.opsForValue().set(key, student);
                redisTemplate.expire(key, 30, TimeUnit.DAYS);
                return student1;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Student> queryStudent(Student student) {
        return studentDao.queryStudent(student);
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
