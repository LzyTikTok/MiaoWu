package com.apps.miaowu;

import com.alibaba.fastjson.JSON;
import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.User;
import com.apps.miaowu.bean.UserExample;
import com.apps.miaowu.bean.extend.UserExtend;
import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.config.ElasticSearchConfig;
import com.apps.miaowu.dao.ArticleMapper;
import com.apps.miaowu.dao.UserMapper;
import com.apps.miaowu.dao.extend.ArticleMapperExtend;
import com.apps.miaowu.service.AnimalService;
import com.apps.miaowu.service.ArticleService;
import com.apps.miaowu.service.UserService;
import com.apps.miaowu.utils.LogUtils;
import com.apps.miaowu.utils.RedisUtil;
import com.apps.miaowu.utils.rabbitMQ.Producer;
import com.apps.miaowu.utils.token.TokenModel;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MiaowuApplication.class})
class MiaowuApplicationTests {

    @Autowired
    ArticleService articleService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Autowired
    Producer producer;

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Resource
    UserMapper userMapper;

    @Autowired
    AnimalService animalService;

    @Test
    public void contextLoads(){
        producer.produce();
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
        TokenModel tokenModel = (TokenModel)apiResult.getData();
//        System.out.println(str);
//        TokenModel tokenModel = JSON.parseObject(str, TokenModel.class);
        APIResult login = userService.getInfo(tokenModel.getToken());
        System.out.println(login.getData());
    }

    @Test
    void TestJson(){
        User user = new User();
        user.setName("测试");
        User user2 = new User();
        user2.setName("测试2号");
        Map<String,Object> map = new HashMap<>();
        map.put("user", user);
        map.put("follow", user2);
        String jsonString = JSON.toJSONString(map);
        System.out.println(jsonString);
        String res = JSON.toJSONString(user) + JSON.toJSONString(user2);
        System.out.println(JSON.toJSONString(user));
        System.out.println(res);
    }

    @Test
    void testLogOut(){
        APIResult login = userService.login("18378980517", "test6942231");
        TokenModel tokenModel = (TokenModel)login.getData();
        APIResult logout = userService.logout(tokenModel.getToken());
        System.out.println(logout);
    }

    @Test
    void testPage(){
        Integer count = 5, page = 2;
        APIResult articleWithLabelByPage = articleService.findArticleWithLabelByPage(count, page);
        System.out.println(articleWithLabelByPage.getData());
    }

    @Test
    void testArticle(){
        APIResult apiResult = articleService.cascadeFindById((1L));
        System.out.println(apiResult);
    }

    @Test
    void testLog(){
        Logger logger = LogUtils.getBusinessLogger();
        logger.info("test log, for business");
    }

    @Test
    void testES() {
        System.out.println(articleService.findArticleByKeyAndValueFuzzily("title","求收养").getData());
    }


    @Test
    void testGetAnimalById(){
        APIResult animals = animalService.findById(2L);
        System.out.println(animals.getData());
    }

}
