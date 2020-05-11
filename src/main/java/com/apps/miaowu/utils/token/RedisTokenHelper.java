package com.apps.miaowu.utils.token;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.apps.miaowu.utils.RedisUtil;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenHelper implements TokenHelper {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean check(TokenModel model) {
        boolean result = false;
        if(model != null){
            String userId = model.getUserId().toString();
            String token = model.getToken();
            String authenticatedToken = redisUtil.get(userId);
            if(authenticatedToken != null && authenticatedToken.equals(token)){
                //已使用TimeUnit，未测试
                redisUtil.expire(userId, RedisUtil.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
            }
        }
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public TokenModel create(Integer id) {
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel tokenModel = new TokenModel(id, token);
        redisUtil.set(token, id == null ? null : String.valueOf(id));
        return tokenModel;
    }

    @Override
    public boolean delete(Integer id) {
        System.out.println("未完成");
        return false;
        // return redisUtil.delete(id == null ? null : St);
    }

    @Override
    public TokenModel get(String authStr) {
        TokenModel tokenModel = null;
        if(StringUtils.isNullOrEmpty(authStr)){
            String[] modelArr = authStr.split("_");
            if(modelArr.length == 2){
                int userId = Integer.parseInt(modelArr[0]);
                String token = modelArr[1];
                tokenModel = new TokenModel(userId, token);
            }
        }
        return tokenModel;
    }
    
}