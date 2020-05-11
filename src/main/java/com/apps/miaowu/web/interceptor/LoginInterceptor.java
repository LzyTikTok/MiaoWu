package com.apps.miaowu.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.apps.miaowu.utils.RedisUtil;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisUtil RedisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        return super.preHandle(request, response, handler);
    }

    

}