package com.javase.springboot1.config;

import com.javase.springboot1.convert.DateConverter;
import com.javase.springboot1.intercept.SessionIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 18:35 2018/8/1
 */

/**
 * 只要有@Configuration注解,表示运行加载的时候,会按照配置自动加载
 */
@Configuration
public class SimpleConfig implements WebMvcConfigurer {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 添加视图映射(不写controller就可以直接访问登录页面)
     *
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
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //给当前的Spring容器中添加自定义格式转换器.
        registry.addConverter(new DateConverter());
    }

    /**
     * 创建拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionIntercepter(redisTemplate)).addPathPatterns("/**").excludePathPatterns("/", "/login.html", "/user/login")//排除登录页面
                .excludePathPatterns("/bootstrap/**");//排除静态资源
    }

    /**
     * 自定义redis模板
     *
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    //加上@bean表示不再用默认的,而是使用自定义的
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //将自定义redis 模板的key序列化器改为string类型
        template.setKeySerializer(new StringRedisSerializer());
        //将自定义的redis模板的value序列化器改为Jackson序列化器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
