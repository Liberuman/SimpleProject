package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/*******************************************************************************
 * Description: 验证工具类
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class VerificationUtil {

    /**
     * 判断手机号码是否有效
     * @param telNumber
     * @return
     */
    public static boolean isValidTelNumber(String telNumber) {
        if (!TextUtils.isEmpty(telNumber)) {
            String regex = "(\\+\\d+)?1[3456789]\\d{9}$";
            return Pattern.matches(regex, telNumber);
        }

        return false;
    }

    /**
     * 判断邮箱地址是否有效
     * @param emailAddress
     * @return
     */
    public static boolean isValidEmailAddress(String emailAddress) {

        if (!TextUtils.isEmpty(emailAddress)) {
            String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            return Pattern.matches(regex, emailAddress);
        }

        return false;
    }

    /**
     * 是否是有效的银行卡
     * @param bankCard
     * @return
     */
    public static boolean isValidBankCard(String bankCard) {
        if (!TextUtils.isEmpty(bankCard)) {
            String regex = "^[1-9](\\d{15} | \\d{18})$";
            return Pattern.matches(regex, bankCard);
        }
        return false;
    }

    /**
     * 判断内容是否由字母，数字，下划线组成
     * @param content
     * @return
     */
    public static boolean isValidContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            String regex = "^[\\w\\u4e00-\\u9fa5\\-]+$";
            return Pattern.matches(regex, content);
        }

        return false;
    }

    /**
     * 判断验证码是否合法（6位数字）
     * @param code
     * @return
     */
    public static boolean isValidCode(String code) {
        if (!TextUtils.isEmpty(code)){
            String regex = "\\d{6}";
            return Pattern.matches(regex, code);
        }

        return false;
    }

    /**
     * 判断密码是否合法 [密码由6位以上的字母和数字组成， 至少包含一个字母和数字，]
     * @param psd
     * @return
     */
    public static boolean isValidPassWord(String psd){
         /*   分开来注释一下：
            ^ 匹配一行的开头位置
            (?![0-9]+$) 预测该位置后面不全是数字
            (?![a-zA-Z]+$) 预测该位置后面不全是字母
            [0-9A-Za-z] {6,10} 由6-10位数字或这字母组成, {6,}至少要6，最多不限制
           $ 匹配行结尾位置
         */

        if(!TextUtils.isEmpty(psd)){
            String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";
            return Pattern.matches(regex, psd);
        }

        return false;
    }

    /**
     * 判断内容是否为中文(判断中文名)
     * @param content
     * @return
     */
    public static boolean isChineseText(String content) {
        if (!TextUtils.isEmpty(content)) {
            String regex = "^[\\u4e00-\\u9fa5]+$";
            return Pattern.matches(regex, content);
        }

        return false;
    }

    /**
     * 判断内容是否为英文或空格(匹配英文名)
     * @param content
     * @return
     */
    public static boolean isEnglishText(String content) {
        if (!TextUtils.isEmpty(content)) {
            String regex = "/^[a-zA-Z]+\\s[a-zA-Z]+$/";
            return Pattern.matches(regex, content);
        }

        return false;
    }

    /**
     * 判断身份证号码是否有效
     * @param idCard
     * @return
     */
    public static boolean isValidIdCard(String idCard) {
        return IdCardValidator.isValidateIdcard(idCard);
    }

    /**
     * 检查是否为身份证号
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    /**
     * 是否为15位身份证号
     * @param idCard
     * @return
     */
    public static boolean is15IdCard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    /**
     * 是否为18位身份证号
     * @param idCard
     * @return
     */
    public boolean is18IdCard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    /**
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     *
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     *
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     *
     * 第十八位数字(校验码)的计算方法为：
     *      1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     *      2.将这17位数字和系数相乘的结果相加。
     *      3.用加出来和除以11，看余数是多少？
     *      4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为: 1 0 X 9 8 7 6 5 4 3 2;
     *      5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     */
    private static class IdCardValidator {

        // 省,直辖市代码表
        private static final String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" },
                { "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" },
                { "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" },
                { "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" },
                { "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" },
                { "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" },
                { "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" },
                { "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
                { "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" },
                { "91", "国外" } };

        // 每位加权因子
        private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

        // 判断18位身份证号码是否有效
        private static boolean isValidate18IdCard(String idcard) {
            if (idcard.length() != 18) {
                return false;
            }
            String idcard17 = idcard.substring(0, 17);
            String idcard18Code = idcard.substring(17, 18);
            char c[];
            String checkCode;
            if (isDigital(idcard17)) {
                c = idcard17.toCharArray();
            } else {
                return false;
            }

            if (null != c) {
                int bit[] = converCharToInt(c);
                int sum17 = getPowerSum(bit);

                // 将和值与11取模得到余数进行校验码判断
                checkCode = getCheckCodeBySum(sum17);
                if (null == checkCode) {
                    return false;
                }
                // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
                if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                    return false;
                }
            }

            return true;
        }

        // 将15位的身份证转成18位身份证
        public static String convertIdcarBy15bit(String idcard) {
            String idcard18 = null;
            if (idcard.length() != 15) {
                return null;
            }

            if (isDigital(idcard)) {
                // 获取出生年月日
                String birthday = idcard.substring(6, 12);
                Date birthdate = null;
                try {
                    birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cday = Calendar.getInstance();
                cday.setTime(birthdate);
                String year = String.valueOf(cday.get(Calendar.YEAR));

                idcard18 = idcard.substring(0, 6) + year + idcard.substring(8);

                char c[] = idcard18.toCharArray();
                String checkCode = "";

                if (null != c) {
                    int bit[] = converCharToInt(c);
                    int sum17;
                    sum17 = getPowerSum(bit);
                    // 获取和值与11取模得到余数进行校验码
                    checkCode = getCheckCodeBySum(sum17);
                    // 获取不到校验位
                    if (null == checkCode) {
                        return null;
                    }

                    // 将前17位与第18位校验码拼接
                    idcard18 += checkCode;
                }
            } else {
                return null;
            }

            return idcard18;
        }

        // 是否全部由数字组成
        public static boolean isDigital(String str) {
            return !(str == null || "".equals(str)) && str.matches("^[0-9]*$");
        }

        // 将身份证的每位和对应位的加权因子相乘之后，再得到和值
        public static int getPowerSum(int[] bit) {
            int sum = 0;
            if (power.length != bit.length) {
                return sum;
            }

            for (int i = 0; i < bit.length; i++) {
                for (int j = 0; j < power.length; j++) {
                    if (i == j) {
                        sum = sum + bit[i] * power[j];
                    }
                }
            }

            return sum;
        }

        // 将和值与11取模得到余数进行校验码判断
        private static String getCheckCodeBySum(int sum17) {
            String checkCode = null;
            switch (sum17 % 11) {
                case 10:
                    checkCode = "2";
                    break;
                case 9:
                    checkCode = "3";
                    break;
                case 8:
                    checkCode = "4";
                    break;
                case 7:
                    checkCode = "5";
                    break;
                case 6:
                    checkCode = "6";
                    break;
                case 5:
                    checkCode = "7";
                    break;
                case 4:
                    checkCode = "8";
                    break;
                case 3:
                    checkCode = "9";
                    break;
                case 2:
                    checkCode = "x";
                    break;
                case 1:
                    checkCode = "0";
                    break;
                case 0:
                    checkCode = "1";
                    break;
            }

            return checkCode;
        }

        // 将字符数组转为整型数组
        private static int[] converCharToInt(char[] c) throws NumberFormatException {
            int[] a = new int[c.length];
            int k = 0;
            for (char temp : c) {
                a[k++] = Integer.parseInt(String.valueOf(temp));
            }

            return a;
        }

        // 验证身份证号码是否有效
        public static boolean isValidateIdcard(String idcard) {
            if (!TextUtils.isEmpty(idcard)) {
                if (idcard.length() == 15 && is15IdCard(idcard)) {
                    return isValidate18IdCard(convertIdcarBy15bit(idcard));
                } else if (idcard.length() == 18) {
                    return isValidate18IdCard(idcard);
                }
            }

            return false;
        }
    }
}
