package com.apps.miaowu.bean.result;

public abstract class ResultCode {
    public static final int SuccessCode = 200;
    public static final int NoSuchContent = 204;  //无内容
    public static final int BadRequest = 400;
    public static final int ServerInnerError = 500;
    public static final int DATA_ALREADY_EXISTEDINT = 503;  //todo 舍弃 503
    public static final int Unauthorized = 403;
}
