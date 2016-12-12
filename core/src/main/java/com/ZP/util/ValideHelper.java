package com.ZP.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ProjectName: XSY
 * PackageName: com.ZP.utils
 * User: C0dEr
 * Date: 2016-10-11
 * Time: 11:33
 * Description:
 */
public class ValideHelper {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNullOrEmpty(Object str) {
        return str == null;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isIP(String ip) {
        String reg = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean isPhone(String phone) {
        if (isNullOrEmpty(phone)) {
            return false;
        }
        String reg = "^1[34578]\\d{9}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isAGoodPassowrd(String pwd) {
        if (isNullOrEmpty(pwd)) {
            return false;
        }
        String reg = "((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$";//长度8~16位,数字、字母、字符至少包含两种
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }

    public static boolean isAGoodPayPassowrd(String pwd) {
        if (isNullOrEmpty(pwd)) {
            return false;
        }
        String reg = "\\d{6}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }

}
