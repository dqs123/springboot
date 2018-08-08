package com.javase.springboot1.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javase.springboot1.contants.RedisContants;
import com.javase.springboot1.entity.Student;
import com.javase.springboot1.entity.UserInfo;
import com.javase.springboot1.service.IStudentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 16:35 2018/7/31
 */
@Controller
@RequestMapping("/user")
public class UserInfoController {

    Log log = LogFactory.getLog(UserInfoController.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IStudentService studentService;

    @RequestMapping("/login")
    public String login(String username, String psaaword, Date loginDate, HttpServletRequest request, HttpServletResponse response) {
        //进行身份验证
        //判断是否为空
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        /**
         * 获取用户的sessionId,并将其保存到redis内
         */
        /**
         *   自定义sessionId,使用当前用户信息加上时间戳生成一个md5字符串,
         *   加时间戳的目的是保证每次生成的sessionId都不同
         */
        String sessionId = "username" + username + System.currentTimeMillis();
        String sessionIdMD5 = DigestUtils.md5DigestAsHex(sessionId.getBytes());

        //将创建的sessionId保存至cookie中
        Cookie cookie = new Cookie("sessionId", sessionIdMD5);
        response.addCookie(cookie);
        //自定义sessionId和session保存在redis中
        String key = RedisContants.LOGIN_SESSION + sessionIdMD5;
        redisTemplate.opsForValue().set(key, username, 30, TimeUnit.MINUTES);

        return "base/success";
    }

    @RequestMapping("/testPage")
    public String testPage(Model model, HttpServletRequest request) {

        model.addAttribute("username", "&gt;张三");
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("");
        userInfo.setAge(18);
        userInfo.setSex("女");
        try {
            Integer.parseInt("a");
        } catch (Exception e) {
            log.error("未知错误信息...");
        }

        model.addAttribute("userInfo", userInfo);

        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);

        model.addAttribute("nowDate", new Date());
        return "test";
    }

    @RequestMapping("/test2Page")
    public String test2Page(Model model, HttpServletRequest request) {
        model.addAttribute("username", "");
        model.addAttribute("id", "12");

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("玲儿");
        userInfo.setAge(18);
        userInfo.setSex("女");
        model.addAttribute("userInfo", userInfo);

        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);
        model.addAttribute("nowDate", new Date());
        return "test2";
    }

    @RequestMapping("/test3Page")
    public String test3Page(Model model) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("玲儿");
        userInfo.setAge(18);
        userInfo.setSex("女");
        model.addAttribute("userInfo", userInfo);

        List<UserInfo> users = new ArrayList<>();
        users.add(new UserInfo("灵儿", "0"));
        users.add(new UserInfo("丫丫", "1"));
        users.add(new UserInfo("肥东", "1"));
        users.add(new UserInfo("雪儿", "0"));
        model.addAttribute("users", users);
        return "test3";
    }

    @RequestMapping("/pageHelp")
    @ResponseBody
    public Object testPageHelp() {
        //ThreadLoad-->每个变量都会创建一个独立的副本
        PageHelper.startPage(1, 5);
        List<Student> students = studentService.queryStudent(new Student());
        //Mybatis执行完之后,ThreadLoad本地变量会被清空
        System.out.println(students);
        PageInfo pageInfo = new PageInfo(students, 3);
        return pageInfo;
    }

    //上传文件
    @RequestMapping("/upload")
    @ResponseBody
    public Object testUpload(MultipartFile pic) {

        try {
            //获取文件的初始值
            String filename = pic.getOriginalFilename();
            //使用Spring的资源工具类去classpath中查找文件路径(上传的文件在target中)
            String path = ResourceUtils.getFile("classpath:static/upload/").getPath();
            File file = new File(path, filename);
            //上传文件
            pic.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回上传结果
        Map<String, String> map = new HashMap<>();
        map.put("msg", "上传成功");
        return map;
    }

    /**
     * 查询单个学生的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Object getStudentById(@PathVariable("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        Student student = studentService.queryById(id);
        map.put("student", student);
        return map;
    }

}
