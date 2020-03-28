package com.apps.miaowu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MiaowuApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaowuApplication.class, args);
    }

}
