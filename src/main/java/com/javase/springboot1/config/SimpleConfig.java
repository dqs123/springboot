package com.javase.springboot1.config;

import com.javase.springboot1.convert.DateConverter;
import com.javase.springboot1.intercept.SessionIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 18:35 2018/8/1
 */

/**
 *只要有@Configuration注解,表示运行加载的时候,会按照配置自动加载
 */
@Configuration
public class SimpleConfig implements WebMvcConfigurer {
    /**
     * 添加视图映射(不写controller就可以直接访问登录页面)
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //确定视图页面和登陆的路径
        registry.addViewController("/login.html").setViewName("base/login");
        registry.addViewController("/").setViewName("base/login");
    }


    /**
     * 创建自定义格式转换器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //给当前的Spring容器中添加自定义格式转换器.
        registry.addConverter(new DateConverter());
    }

    /**
     * 创建拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionIntercepter()).addPathPatterns("/**")
        .excludePathPatterns("/","/login.html","/user/login")//排除登录页面
        .excludePathPatterns("/bootstrap/**");//排除静态资源
    }
}
