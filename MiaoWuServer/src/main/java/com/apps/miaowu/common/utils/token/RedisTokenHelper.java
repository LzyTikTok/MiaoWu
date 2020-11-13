package com.apps.miaowu.common.utils.token;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.apps.miaowu.common.utils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenHelper implements TokenHelper {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean check(String token) {
        if(token != null || token.length() != 0){
            String userId = redisUtil.get(token);
            if(userId != null){
                //已使用TimeUnit，未测试
                redisUtil.expire(token, RedisUtil.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
                return true;
            }
        }
        return false;
    }

    @Override
    public TokenModel create(String id) {
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel tokenModel = new TokenModel(id, token);
        redisUtil.set(token, id == null ? null : id);
        return tokenModel;
    }

    @Override
    public boolean delete(String id) {
        try {
            redisUtil.delete(id) ;
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public TokenModel get(String authStr) {
        String res = redisUtil.get(authStr);
        TokenModel tokenModel = new TokenModel(res, authStr);
        // if(StringUtils.isNullOrEmpty(authStr)){
        //     String[] modelArr = authStr.split("_");
        //     if(modelArr.length == 2){
        //         String userId = modelArr[0];
        //         String token = modelArr[1];
        //         tokenModel = new TokenModel(userId, token);
        //     }
        // }
        return tokenModel;
    }
    
}