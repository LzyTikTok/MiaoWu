package com.apps.miaowu.utils.token;

public interface TokenHelper {
      
    /**
     * 根据用户id生成token
     * @param id
     * @return
     */
    TokenModel create(String id);
    
    /**
     * 检查是否存在该token
     * @param token
     * @return
     */
    boolean check(String token);
    
    /**
     * 从前端返回的校验字符串中获取token
     * @param authStr
     * @return
     */
    TokenModel get(String authStr);
    
    /**
     * 根据用户id删除token
     * @param mode
     * @return
     */
    boolean delete(String id);
}