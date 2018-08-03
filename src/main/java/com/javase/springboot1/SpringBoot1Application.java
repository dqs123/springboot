package com.javase.springboot1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//加载的时候会自动将dao文件夹下项目加载进去
@MapperScan("com.javase.springboot1.dao")
//开启全局事物
@EnableTransactionManagement
public class SpringBoot1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot1Application.class, args);
    }
}
