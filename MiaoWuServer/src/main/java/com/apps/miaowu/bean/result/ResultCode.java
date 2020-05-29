package com.apps.miaowu.bean.result;

public abstract class ResultCode {
    public static final int SuccessCode = 200;
    public static final int CancelSuccessCode = 201;
    public static final int BadRequest = 400;
    public static final int ServerInnerError = 500;
    public static final int DATA_ALREADY_EXISTEDINT = 503;
    public static final int NO_AUTH = 403;
}
