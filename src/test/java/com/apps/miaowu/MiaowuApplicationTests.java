package com.apps.miaowu;

import com.apps.miaowu.bean.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
class MiaowuApplicationTests {

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

}
