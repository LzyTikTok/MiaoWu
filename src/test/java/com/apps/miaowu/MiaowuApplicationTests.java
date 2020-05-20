package com.apps.miaowu;

import com.alibaba.fastjson.JSON;
import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.service.UserService;
import com.apps.miaowu.utils.RedisUtil;
import com.apps.miaowu.utils.token.TokenModel;

import redis.clients.jedis.Jedis;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MiaowuApplication.class})
class MiaowuApplicationTests {

    @Autowired
    ArticleService articleService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {

    }

    @Test
    void testClass(){
        Animal animal = new Animal();
        Class<?> clz = animal.getClass();
        Field[] fields = clz.getFields();
        for(Field field: fields) {
            System.out.println(field);
        }
    }

    @Test
    void testDate(){
//        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//        Date date = LocalDateTime.now();
//        String tp = sd.format(date);
//        System.out.println(tp);
        System.out.println(LocalDateTime.now());
    }

    @Test
    void testRedis(){
       redisUtil.set("key","123");
       System.out.println(redisUtil.get("key"));
    //    System.out.println(redisUtil.get("key"));
    //    Jedis jedis = new Jedis("localhost");
       System.out.println("连接成功");
//        //查看服务是否运行
    //    System.out.println("服务正在运行: "+jedis.ping());
    }

    @Test
    void testSelect(){
//        articleMapperExtend.
    }

    @Test
    void testHtml(){
        String html = "<p><b>1、什么是Vue?</b></p><p>vue真的太好用了，是前后段分离必不可少的开发框架之一……</p><p><br></p><p><i><u>2、Vue能干什么？</u></i></p><p>模拟数据</p><p><br></p>"; // 前端传过来的富文本内容
        String temp = HtmlUtils.htmlEscapeHex(html);
        System.err.println("存数据库=\r\n" + temp);

        String returnHtml = HtmlUtils.htmlUnescape(temp);
        System.out.println("回调===\r\n" + returnHtml);
    }

    @Test
    void testSelect2(){
        APIResult apiResult = articleService.cascadeFindById(1L);
        System.out.println(apiResult);
    }

    @Test
    void testLogin(){
        APIResult apiResult = userService.login("18378980517", "test6942231");
        String str = apiResult.getData().toString();
        System.out.println(str);
        TokenModel tokenModel = JSON.parseObject(str, TokenModel.class);
        APIResult login = userService.getInfo(tokenModel.getToken());
        User user = JSON.parseObject((String) login.getData(), User.class);
        System.out.println(user);
    }

    @Test
    void TestJson(){
        User user = new User();
        user.setName("测试");
        User user2 = new User();
        user2.setName("测试2号");
        String res = JSON.toJSONString(user) + JSON.toJSONString(user2);
        System.out.println(JSON.toJSONString(user));
        System.out.println(res);
    }

}
