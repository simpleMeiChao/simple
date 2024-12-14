package com.mc.utils;

import com.mc.enums.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getNowDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.DATE_TIME.getConstant());
        return simpleDateFormat.format(new Date());
    }

    public static String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.DATE.getConstant());
        return simpleDateFormat.format(new Date());
    }

    public static String getNowDateTimeMills() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.DATE_TIME_MILLISECOND.getConstant());
        return simpleDateFormat.format(new Date());
    }
}
