package com.javase.springboot1.intercept;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 19:21 2018/8/1
 */

/**
 * session拦截器
 */
public class SessionIntercepter implements HandlerInterceptor {

    /**
     * 前置执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object username = request.getSession().getAttribute("username");
        if (username !=null){
            return true;
        }else {
            //如果没有用户,跳转到登录页面,重新登录
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }

    }
}
