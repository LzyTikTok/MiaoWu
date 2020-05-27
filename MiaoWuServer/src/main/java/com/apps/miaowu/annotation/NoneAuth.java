package com.apps.miaowu.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;



/**
 * 使用本注解的方法不会进行登录验证
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface NoneAuth{

}