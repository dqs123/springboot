package com.javase.springboot1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javase.springboot1.contants.RedisContants;
import com.javase.springboot1.entity.Student;
import com.javase.springboot1.service.IStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot1ApplicationTests {

    @Resource
    private IStudentService studentService;

    /**
     * redis中写入String类型数据的模板
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(studentService.queryStudent(new Student(Integer.parseInt("1"), null, null, null)));
    }

    @Test
    public void test1() {
        System.out.println(studentService.addStudent(new Student(null, "孙达", "123", Integer.parseInt("11"))));
    }

    @Test
    public void testqueryBuId() {
        System.out.println(studentService.queryById(5));

    }

    @Test
    public void testStringRedis() {
        stringRedisTemplate.opsForValue().set("user_11", "huahua");
        String user_11 = stringRedisTemplate.opsForValue().get("user_11");
        System.out.println(user_11);
    }

    /**
     * 将person - json写入redis
     * 手动将对象转为json
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testSetPerson() throws JsonProcessingException {
        Student student = new Student(1, "张三", "男", 18);
        String key = RedisContants.STUDENT_INFO + student.getId();
        String s = JSONObject.toJSONString(student);
        stringRedisTemplate.opsForValue().set(key, s);
        String s1 = stringRedisTemplate.opsForValue().get(key);
        System.out.println("student_info_1的信息是:" + s1);
    }

    /**
     * 使用自定义redis模板,将不再需要手动转换json-object
     * 自动json-对象相互转换
     */
    @Test
    public void testSimpleRedisTemplate() {
        Student student = new Student(2, "张三", "男", 18);
        String key = RedisContants.STUDENT_INFO + student.getId();
        redisTemplate.opsForValue().set(key, student);
        System.out.println("================获取值================");
        Student p1 = (Student) redisTemplate.opsForValue().get(key);
        System.out.println(p1);
    }

}
