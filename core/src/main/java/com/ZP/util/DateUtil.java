package com.ZP.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static String format = "yyyy-MM-dd HH:mm:ss";

    public static String convert(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }
}
