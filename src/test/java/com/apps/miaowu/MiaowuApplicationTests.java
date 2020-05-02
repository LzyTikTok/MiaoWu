package com.apps.miaowu;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.utils.RedisUtil;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class MiaowuApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

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
//        Jedis jedis = new Jedis("localhost");
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
    }

}
