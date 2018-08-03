package com.javase.springboot1.entity;

/**
 * @Author: dqs
 * @Date: Created on 14:39 2018/8/3
 */

/**
 * 创建学生的实体类
 */
public class Student {
    private Integer id;
    private  String username;
    private String password;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Student(Integer id, String username, String password, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", age=" + age + '}';
    }
}
