package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;

import static java.lang.Double.parseDouble;

/*******************************************************************************
 * Description: 字符串转换操作
 *
 * Author: Freeman
 *
 * Date: 2018/3/23
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ConvertUtil {

    /**
     * 字符串转整型
     * @param value
     * @return
     */
    public static int stringToInt(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                if(value.contains(".")){
                    return  (int)Double.parseDouble(value);
                }else {
                    return Integer.parseInt(value);
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        return 0;
    }

    public static int stringToInt(String value,int defaultValue) {
        if (!TextUtils.isEmpty(value)) {
            try {
                if(value.contains(".")){
                    return  (int)Double.parseDouble(value);
                }else {
                    return Integer.parseInt(value);
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        return defaultValue;
    }


    /**
     * 字符串转长整型
     * @param value
     * @return
     */
    public static long stringToLong(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        return 0L;
    }

    /**
     * 字符串转单精度
     * @param value
     * @return
     */
    public static float stringToFloat(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Float.parseFloat(value);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        return 0F;
    }

    /**
     * 字符串转双精度
     * @param value
     * @return
     */
    public static double stringToDouble(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return parseDouble(value);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        return 0D;
    }

    /**
     * 字符串转布尔类型
     * @param value
     * @return
     */
    public static boolean stringToBoolean(String value) {
        return "1".equals(value) || Boolean.parseBoolean(value);
    }
}
