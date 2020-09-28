package com.apps.miaowu.bean.result;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

/**
 * @Author lzy
 * @create 2020/9/23 12:23
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResultEnum {
    SUCCESS(ResultCode.SuccessCode,"操作成功"),
    UNAUTH(ResultCode.Unauthorized,"无权限"),
    BAD_REQUEST(ResultCode.BadRequest,"坏请求");
    private int code;
    private String msg;

}
