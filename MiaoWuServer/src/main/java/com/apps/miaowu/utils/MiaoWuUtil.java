package com.apps.miaowu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MiaoWuUtil {
    public static String getDate(){
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date date = new Date();
        String tp = sd.format(date);
        return tp;
    }
}
