package com.apps.miaowu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.apps.miaowu.dao")
public class MybatisConfig {

}
