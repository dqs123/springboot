package com.javase.springboot1.intercept;

import com.alibaba.fastjson.JSON;
import com.javase.springboot1.entity.BaseModel;
import com.javase.springboot1.util.AuthCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Author: dqs
 * @Date: Created on 20:35 2018/8/9
 */

/**
 * 设置接口的权限
 */
public class AuthInterceptor implements HandlerInterceptor {

    //声明一个静态的密钥,方便与前端传来的对比
    private String SIGN_KEY = "MQWKQZKMEDUTUOM8A3RCKXB9UE8ZREC1";

    /**
     * 1.接口传递的参数校验
     * request获取接口中所有的参数
     * 2.如何校验数据是否安全
     * 2.1 获取请求URL,并获取请求中的参数
     * 2.2 修改参数,并再次模拟请求(防止修改参数)
     * 2.3 校验数据安全最简单的做法就是对所有参数加上一个额外的密钥进行加密,然后对比传输过来的密钥,两者一致,则数据安全
     * 3.校验结果处理
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        //判定当前请求是HandlerMethod,也就是调用的方法
        if(handler instanceof HandlerMethod){
            //获取当前的HandlerMethod对象,HandlerMethod对象是Spring基于反射封装的对象
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //查找当前方法上是否有AuthCheck注解
            boolean hasAnnotation = handlerMethod.hasMethodAnnotation(AuthCheck.class);
            if(hasAnnotation){ //当有注解时,进行鉴权
                boolean isAuth = checkAuth(request);
                if(isAuth){
                    flag =  true;
                }
            }else{ //没有注解,则不需要鉴权,直接返回true
                flag = true;
            }
        }else{ //没有调用方法,直接返回true
            flag = true;
        }

        if(flag){
            return true;
        }else{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            BaseModel model = new BaseModel();
            model.setCodeMessage(401,"无访问权限");
            writer.write(JSON.toJSONString(model));
            return false;
        }
    }

    public boolean checkAuth(HttpServletRequest request){
        //获取前端提交的加密字符串
        String sign = request.getParameter("sign");
        //获取request中所有的参数,参数值可能会有多个.
        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取map中的key集合
        Set<String> strings = parameterMap.keySet();
        //用来存放重新拼接的字符串的
        List<String> paramList = new ArrayList<>();
        for (String key : strings) {
            if (!key.equals("sign")) {  //排除掉前端请求参数中携带的加密字符串
                String[] paramValues = parameterMap.get(key);
                if (paramValues.length > 1) { //当值为数组时,将数组转为字符串,使用逗号对数组中的元素进行分隔.
                    String join = StringUtils.join(paramValues, ",");
                    paramList.add(key + "=" + join);
                } else {
                    paramList.add(key + "=" + paramValues[0]);
                }
            }
        }
        //将集合转为数组
        Object[] objects = paramList.toArray();
        //排序
        Arrays.sort(objects);

        //将数组中的元素拼接成一个字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i]);
            sb.append("&");
        }
        //加入密钥
        sb.append("signkey=" + SIGN_KEY);
        //对需要加密对比的字符串进行MD5
        String signStr = DigestUtils.md5DigestAsHex(sb.toString().getBytes());
        System.out.println(signStr);
        if(signStr.equals(sign)){ //使用前端提交的加密字符串与当前服务器生成的进行比较
            return true;
        }else{
            return false;
        }
    }



}
