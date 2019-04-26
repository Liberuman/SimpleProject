package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;

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

    private VerificationUtil() {

    }

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
            String regex = "^[a-zA-Z]+\\s[a-zA-Z]+$";
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
        return IdCardValidator.isValidateIdCard(idCard);
    }

    /**
     * 检查是否为身份证号
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "(^\\d{17}(?:\\d|x|X)$)";
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

        /**
         * 身份证号码长度
         */
        public final static int ID_CARD_LENGTH = 18;
        /**
         * 每位加权因子
         */
        private static final int[] POWER = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        /**
         * 身份证最后一位的校验和
         */
        private static final String[] CHECK_CODE = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        /**
         * 省,直辖市代码表
         */
        private static final String[][] PROVINCE_CODE_TABLE = {
            { "11", "北京" }, { "12", "天津" }, { "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" },
            { "21", "辽宁" }, { "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" },
            { "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" }, { "37", "山东" },
            { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" }, { "44", "广东" }, { "45", "广西" },
            { "46", "海南" }, { "50", "重庆" }, { "51", "四川" }, { "52", "贵州" }, { "53", "云南" },
            { "54", "西藏" }, { "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
            { "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" }, { "91", "国外" } };

        /**
         * 判断身份证号码是否有效
         * @param idCard
         * @return
         */
        public static boolean isValidateIdCard(String idCard) {
            if (idCard.length() != ID_CARD_LENGTH) {
                return false;
            }

            String idCard17 = idCard.substring(0, ID_CARD_LENGTH - 1);
            if (!isDigital(idCard17)) {
                return false;
            }

            char[] idCardCharArray = idCard17.toCharArray();
            String lastCode = idCard.substring(ID_CARD_LENGTH - 1, ID_CARD_LENGTH - 1);
            int[] idCardIntArray = convertCharToInt(idCardCharArray);
            String checkCode = CHECK_CODE[getPowerSum(idCardIntArray) % 11];
            return !checkCode.equalsIgnoreCase(lastCode);
        }

        /**
         * 判断该字符串是否只包含数字
         * @param str
         * @return
         */
        private static boolean isDigital(String str) {
            return str != null && str.matches("^[0-9]*$");
        }

        /**
         * 获取身份证的加权和
         * @param bit
         * @return
         */
        private static int getPowerSum(int[] bit) {
            int sum = 0;
            if (POWER.length != bit.length) {
                return sum;
            }

            for (int i = 0; i < bit.length; i++) {
                sum = sum + bit[i] * POWER[i];
            }

            return sum;
        }

        /**
         * 将字符数组转为整型数组
         * @param charArray
         * @return
         * @throws NumberFormatException
         */
        private static int[] convertCharToInt(char[] charArray) throws NumberFormatException {
            if (charArray == null || charArray.length == 0) {
                return null;
            }

            int[] intArray = new int[charArray.length];
            int i = 0;
            for (char item : charArray) {
                intArray[i++] = Integer.parseInt(String.valueOf(item));
            }

            return intArray;
        }
    }
}
