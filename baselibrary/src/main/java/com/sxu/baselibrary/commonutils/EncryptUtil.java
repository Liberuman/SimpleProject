package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************************************
 * Description: 常用的字符串加密（手机号码，邮箱，证件号码等）
 *
 * Author: Freeman
 *
 * Date: 2017/5/4
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class EncryptUtil {

    /**
     * 加密手机号码(隐藏中间4位)
     * @param telNumber
     */
    public static String encryptTelNumber(String telNumber) {
        if (VerificationUtil.isValidTelNumber(telNumber)) {
            return telNumber.substring(0, 3) + "****" + telNumber.substring(7);
        }

        return telNumber;
    }

    /**
     * 加密邮件地址（隐藏用户名中第三位后面的部分）
     * @param email
     */
    public static String encryptEmail(String email) {
        if (VerificationUtil.isValidEmailAddress(email)) {
            String[] subEmail = email.split("@");
            String nickName = subEmail[0];
            if (nickName.length() > 3) {
                return nickName.substring(0, 3) + "******" + "@" + subEmail[1];
            }
        }

        return email;
    }

    /**
     * 加密身份证号码（显示前6位和最后2位）
     * @param idCard
     */
    public static String encryptIdCard(String idCard) {
        if (VerificationUtil.isValidIdCard(idCard)) {
            return idCard.substring(0, 6) + "**********" + idCard.substring(idCard.length() - 2);
        }

        return idCard;
    }

    /**
     * 加密其他证件类型号码（加密中间4位）
     * @param cardNumber
     * @return
     */
    public static String encryptOtherCard(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber)) {
            int len = cardNumber.length();
            if (len > 4) {
                int startLen = (len - 4) / 2;
                int startOffset = (len - 4) % 2;
                int endPos = startLen + startOffset + 4;
                if (endPos < len) {
                    return cardNumber.substring(0, startLen + startOffset) + "****" + cardNumber.substring(endPos);
                } else {
                    return cardNumber.substring(0, startLen + startOffset) + "****";
                }
            }
        }

        return cardNumber;
    }

    /**
     * 加密用户名（可能为用户名，手机号或邮箱）
     * @param nickName
     * @return
     */
    public static String encryptNickName(String nickName) {
        if(!TextUtils.isEmpty(nickName)) {
            if (VerificationUtil.isValidTelNumber(nickName)) {
                return encryptTelNumber(nickName);
            } else if (VerificationUtil.isValidEmailAddress(nickName)) {
                return encryptEmail(nickName);
            } else {
                return nickName;
            }
        }else{
            return "";
        }
    }

    /**
     * 通过Md5加密
     * @param content
     * @return
     */
    public static String encryptByMd5(String content) {
        return encryptByHash(content, "md5");
    }

    /**
     * 通过指定Hash算法加密
     * @param content
     * @param algorithm 支持的算法：MD5， SHA-1， SHA-224， SHA-256， SHA-384， SHA-512
     * @return
     */
    public static String encryptByHash(String content, String algorithm) {
        if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(algorithm)) {
            try {
                MessageDigest digest = MessageDigest.getInstance(algorithm.toUpperCase());
                byte[] md5 = digest.digest(content.getBytes());
                BigInteger bigInteger = new BigInteger(1, md5);
                return bigInteger.toString(16);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        return null;
    }

    public static String encryptByDES(String content) {
        if (!TextUtils.isEmpty(content)) {

        }

        return null;
    }

    public static String encryptBy3DES(String content) {
        if (!TextUtils.isEmpty(content)) {

        }

        return null;
    }

    public static String encryptByAES(String content) {
        if (!TextUtils.isEmpty(content)) {

        }

        return null;
    }

    private static String encrypt(String content, String key, String algorithm) {
        if (!TextUtils.isEmpty(content)) {

        }

        return null;
    }

    public static String encryptByRSA(String content) {
        if (!TextUtils.isEmpty(content)) {

        }

        return null;
    }
}
