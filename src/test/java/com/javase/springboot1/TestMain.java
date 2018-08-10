package com.javase.springboot1;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Author: dqs
 * @Date: Created on 21:42 2018/8/9
 */
public class TestMain {
    public static void main(String[] args) {
        //随机的生成一个数字加字母的32位字符串
        String random = RandomStringUtils.random(32, true, true).toUpperCase();
        System.out.println(random);
    }
}
