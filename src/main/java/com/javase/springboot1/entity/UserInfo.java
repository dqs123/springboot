package com.javase.springboot1.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 11:19 2018/7/31
 */
@Component
@ConfigurationProperties(prefix = "user")
public class UserInfo {
    private String username;
    private String sex;
    private Integer age;
    private Date birthday;
    private List hobbys;
    private Map comments;

    public UserInfo() {
    }

    public UserInfo(String username, String sex) {
        this.username = username;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List getHobbys() {
        return hobbys;
    }

    public void setHobbys(List hobbys) {
        this.hobbys = hobbys;
    }

    public Map getComments() {
        return comments;
    }

    public void setComments(Map comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", sex='" + sex + '\'' + ", age=" + age + ", birthday=" + birthday + ", hobbys=" + hobbys + ", comments=" + comments + '}';
    }
}
