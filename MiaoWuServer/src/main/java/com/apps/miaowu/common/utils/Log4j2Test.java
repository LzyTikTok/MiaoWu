package com.apps.miaowu.common.utils;

import org.slf4j.Logger;

public class Log4j2Test {

    public static Logger businessLogger = LogUtils.getBusinessLogger();
    public static Logger dbLogger = LogUtils.getDBLogger();
    public static Logger exceptionLogger = LogUtils.getExceptionLogger();
    public static Logger platformLogger = LogUtils.getPlatformLogger();



    public static void main(String[] args) {
        businessLogger.info("business_info");
        dbLogger.info("db_info");
        exceptionLogger.error("exp");
        platformLogger.info("plat");
    }
}
