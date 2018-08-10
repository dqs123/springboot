package com.javase.springboot1.contants;

/**
 * @Author: dqs
 * @Date: Created on 11:39 2018/8/7
 */

/**
 * redis的key常量类
 * 放所有redis可以的前缀,方便后期维护redis的key
 */
public class RedisContants {
    /**
     * student详细信息
     *
     * @deprecated student_info_1 : 表示id为1的student详情
     */
    public static final String STUDENT_INFO = "student_info_";
    /**
     * 用户登录信息
     *
     * @deprecated login_session_1  表示用户1的登录信息
     */
    public static final String LOGIN_SESSION = "login_session_";

}
