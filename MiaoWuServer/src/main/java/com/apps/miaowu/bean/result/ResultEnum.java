package com.apps.miaowu.bean.result;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

/**
 * @Author lzy
 * @create 2020/9/23 12:23
 */
public enum ResultEnum {
    SUCCESS(ResultCode.SuccessCode, "success"),
    UNAUTH(ResultCode.Unauthorized,"unauthorized"),
    BAD_REQUEST(ResultCode.BadRequest,"bad request"),
    ILLEGAL_PARAM(ResultCode.BadRequest, "illegal param"),
    NO_CONTENT(ResultCode.NoSuchContent,"no content");
    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
