package com.javase.springboot1.Handler;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Commment(表示在不知道是什么类的时候,放在spring容器加载的)
 * @Controller(给控制器)
 * @Service(表示服务层)
 * @Repository(表示接口层)
 *
 * @Author: dqs
 * @Description:
 * @Date: Created on 17:34 2018/8/2
 */


@Component
public class SimpleErrorHandler extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest,includeStackTrace);
        errorAttributes.put("msg","信息错误...");
        return errorAttributes;
    }

}
