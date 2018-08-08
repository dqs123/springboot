package com.javase.springboot1.intercept;

import com.javase.springboot1.contants.RedisContants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 19:21 2018/8/1
 */

/**
 * session拦截器
 */
public class SessionIntercepter implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    /**
     * 方便配置引用时,传递需要的变量
     *
     * @param redisTemplate
     */
    public SessionIntercepter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 前置执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object username = request.getSession().getAttribute("username");
        if (username != null) {
            return true;
        } else {
            //如果没有用户,跳转到登录页面,重新登录
            /**
             * 判断当前sessionId在redis中是否存在
             *  1.如果存在,正常返回,并刷新redis
             *  2.如果不存在,跳转到登录页面
             */
            String jsessionIdCookie = getJsessionIdCookie(request);//获取留言器的sessionId
            if (jsessionIdCookie != null) {
                //拼接字符串,并查询redis中是否存在
                String key = RedisContants.LOGIN_SESSION + jsessionIdCookie;
                Object o = redisTemplate.opsForValue().get(key);
                if (o != null) {
                    //如果存在,刷新redis的时间
                    redisTemplate.expire(key, 30, TimeUnit.MINUTES);
                    return true;
                } else {
                    //不存在从新登陆
                    request.getRequestDispatcher("/login.html").forward(request, response);
                    return false;
                }
            } else {
                request.getRequestDispatcher("/login.html").forward(request, response);
                return false;
            }
        }
    }

    public String getJsessionIdCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("sessionId")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}