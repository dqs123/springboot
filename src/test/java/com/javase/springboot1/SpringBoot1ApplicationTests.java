package com.javase.springboot1;

import com.javase.springboot1.entity.Student;
import com.javase.springboot1.service.IStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot1ApplicationTests {

    @Resource
    private IStudentService studentService;
    @Test
    public void contextLoads() {
        System.out.println(studentService.queryById(new Student(Integer.parseInt("1"),null,null,null)));
    }

    @Test
    public void test1(){
        System.out.println(studentService.addStudent(new Student(null,"孙达","123",Integer.parseInt("11"))));
    }
}
