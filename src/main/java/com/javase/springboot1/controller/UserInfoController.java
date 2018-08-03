package com.javase.springboot1.controller;

        import com.github.pagehelper.PageHelper;
        import com.github.pagehelper.PageInfo;
        import com.javase.springboot1.entity.Student;
        import com.javase.springboot1.entity.UserInfo;
        import com.javase.springboot1.service.IStudentService;
        import org.apache.commons.logging.Log;
        import org.apache.commons.logging.LogFactory;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.util.ResourceUtils;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.multipart.MultipartFile;

        import javax.annotation.Resource;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpSession;
        import java.io.File;
        import java.util.*;

/**
 * @Author: dqs
 * @Description:
 * @Date: Created on 16:35 2018/7/31
 */
@Controller
@RequestMapping("/user")
public class UserInfoController {

    Log log =LogFactory.getLog(UserInfoController.class);


    @Resource
    private IStudentService studentService;

    @RequestMapping("/login")
    public String login(String username,String psaaword,Date loginDate,HttpServletRequest request){
        //进行身份验证
        //判断是否为空
        HttpSession session = request.getSession();
        session.setAttribute("username",username);
        return "base/success";
    }

    @RequestMapping("/testPage")
    public String testPage(Model model, HttpServletRequest request){

        model.addAttribute("username","&gt;张三");
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("");
        userInfo.setAge(18);
        userInfo.setSex("女");
        try {
            Integer.parseInt("a");
        }catch (Exception e){
            log.error("未知错误信息...");
        }

        model.addAttribute("userInfo",userInfo);

        HttpSession session = request.getSession();
        session.setAttribute("userInfo",userInfo);

        model.addAttribute("nowDate",new Date());
        return "test";
    }

    @RequestMapping("/test2Page")
    public String test2Page(Model model, HttpServletRequest request){
        model.addAttribute("username","");
        model.addAttribute("id","12");

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("玲儿");
        userInfo.setAge(18);
        userInfo.setSex("女");
        model.addAttribute("userInfo",userInfo);

        HttpSession session = request.getSession();
        session.setAttribute("userInfo",userInfo);
        model.addAttribute("nowDate",new Date());
        return "test2";
    }

    @RequestMapping("/test3Page")
    public String test3Page(Model model){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("玲儿");
        userInfo.setAge(18);
        userInfo.setSex("女");
        model.addAttribute("userInfo",userInfo);

        List<UserInfo> users = new ArrayList<>();
        users.add(new UserInfo("灵儿","0"));
        users.add(new UserInfo("丫丫","1"));
        users.add(new UserInfo("肥东","1"));
        users.add(new UserInfo("雪儿","0"));
        model.addAttribute("users",users);
        return "test3";
    }


    @RequestMapping("/pageHelp")
    @ResponseBody
    public Object testPageHelp(){
        //ThreadLoad-->每个变量都会创建一个独立的副本
        PageHelper.startPage(1,5);
        List<Student> students = studentService.queryById(new Student());
        //Mybatis执行完之后,ThreadLoad本地变量会被清空
        System.out.println(students);
        PageInfo pageInfo = new PageInfo(students,3);
        return pageInfo;
    }

    //上传文件
    @RequestMapping("/upload")
    @ResponseBody
    public Object testUpload(MultipartFile pic){

        try {
            //获取文件的初始值
            String filename = pic.getOriginalFilename();
            //使用Spring的资源工具类去classpath中查找文件路径(上传的文件在target中)
            String path = ResourceUtils.getFile("classpath:static/upload/").getPath();
            File file = new File(path, filename);
            //上传文件
            pic.transferTo(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回上传结果
        Map<String,String> map =new HashMap<>();
        map.put("msg","上传成功");
        return map;
    }
}
