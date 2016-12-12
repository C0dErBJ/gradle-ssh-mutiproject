package com.ZP.util;

/**
 * ProjectName: XSY
 * PackageName: com.ZP.utils
 * User: C0dEr
 * Date: 2016-10-11
 * Time: 18:20
 * Description:
 */
public class StringHelper {
    public static String TrimEnd(String oStr, String endWith) {
        if (oStr.endsWith(endWith)) {
            return oStr.substring(0, oStr.length() - endWith.length() - 1);
        } else {
            return oStr;
        }
    }

    public static String TrimStart(String oStr, String startWith) {
        if (oStr.startsWith(startWith, 0)) {
            return oStr.substring(startWith.length(), oStr.length() - 1);
        } else {
            return oStr;
        }
    }

    public static String Trim(String oStr, String fix) {
        String newStr = oStr;
        if (oStr.startsWith(fix, 0)) {
            newStr = oStr.substring(fix.length(), oStr.length() - 1);
        }
        if (oStr.endsWith(fix)) {
            newStr = oStr.substring(0, oStr.length() - fix.length() - 1);
        }
        return newStr;
    }

    public static String EncryptPhone(String phone, char replaceChar) {
        if (ValideHelper.isNullOrEmpty(phone)) {
            return phone;
        }
        char[] numList = phone.toCharArray();
        for (int i = numList.length / 2 - 2; i <= numList.length / 2 + 4; i++) {
            numList[i] = replaceChar;
        }
        return String.valueOf(numList);
    }
}
