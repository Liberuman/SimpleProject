package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;
import android.util.Base64;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************************************
 * Description: 常用的加密方式以及字符串部分隐藏（手机号码，邮箱，证件号码等）
 *
 * Author: Freeman
 *
 * Date: 2017/5/4
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class EncryptUtil {

    private static final int KEY_LEN_DES = 8;
    private static final int KEY_LEN_3DES_16 = 16;
    private static final int KEY_LEN_3DES_24 = 24;
    private static final int KEY_LEN_AES_16 = 16;
    private static final int KEY_LEN_AES_24 = 24;

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
                e.printStackTrace(System.err);
            }
        }

        return null;
    }

    /**
     * 使用DES算法加密
     * @param content
     * @param key 密钥长度为64，即8个ASCII字符
     * @return
     */
    public static String encryptByDES(String content, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be empty!");
        } else if (key.length() != KEY_LEN_DES) {
            throw new IllegalArgumentException("key'length must be 8!");
        }

        return encrypt(content, key, "DES", "DES");
    }

    /**
     * 使用DES算法解密
     * @param content
     * @param key 密钥长度为64，即8个ASCII字符
     * @return
     */
    public static String decryptByDES(String content, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be empty!");
        } else if (key.length() != KEY_LEN_DES) {
            throw new IllegalArgumentException("key'length must be 8!");
        }

        return decrypt(content, key, "DES", "DES");
    }

    /**
     * 使用3DES算法加密
     * @param content
     * @param key 密钥长度为128或192，即16或24个ASCII字符
     * @return
     */
    public static String encryptBy3DES(String content, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be empty!");
        } else if (key.length() != KEY_LEN_3DES_16 && key.length() != KEY_LEN_3DES_24) {
            throw new IllegalArgumentException("key'length must be 16 or 24!");
        }

        return encrypt(content, key, "DESede", "DESede");
    }

    /**
     * 使用3DES算法解密
     * @param content
     * @param key 密钥长度为128或192，即16或24个ASCII字符
     * @returnde
     */
    public static String decryptBy3DES(String content, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be empty!");
        } else if (key.length() != KEY_LEN_3DES_16 && key.length() != KEY_LEN_3DES_24) {
            throw new IllegalArgumentException("key'length must be 16 or 24!");
        }

        return decrypt(content, key, "DESede", "DESede");
    }

    /**
     * 使用AES算法加密
     * @param content
     * @param key 密钥长度为128，192或256位，即16，24或32个ASCII字符，但默认只有128位可用
     * @return
     */
    public static String encryptByAES(String content, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be empty!");
        } else if (key.length() != KEY_LEN_AES_16 && key.length() != KEY_LEN_AES_24) {
            throw new IllegalArgumentException("key'length must be 16 or 24!");
        }

        return encrypt(content, key, "AES", "AES");
    }

    /**
     * 使用AES算法解密
     * @param content
     * @param key 密钥长度为128，192或256位，即16，24或32个ASCII字符，但默认只有128位可用
     * @return
     */
    public static String decryptByAES(String content, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be empty!");
        }  else if (key.length() != KEY_LEN_AES_16 && key.length() != KEY_LEN_AES_24) {
            throw new IllegalArgumentException("key'length must be 16 or 24!");
        }

        return decrypt(content, key, "AES", "AES");
    }

    /**
     * 使用RSA算法加密 注意：生成的密文长度和密文长度无关，但明文长度不能超过密钥长度
     * @param content
     * @param key key的长度通常大于等于1024位，即128个ASCII字符，通常使用1024， 2048， 3072等
     * @return
     */
    public static String encryptByRSA(String content, Key key) {
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("content can't be empty!");
        }

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key, new SecureRandom());
            byte[] result = cipher.doFinal(content.getBytes());
            BigInteger bigInteger = new BigInteger(1, result);
            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    /**
     * 使用RSA算法解密
     * @param content
     * @param key
     * @return
     */
    public static String decryptByRSA(String content, Key key) {
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("content can't be empty!");
        }

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key, new SecureRandom());
            return new String(cipher.doFinal(content.getBytes()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    /**
     * 对称加密过程
     * @param content
     * @param key
     * @param algorithm
     * @param transformation
     * @return
     */
    private static String encrypt(String content, String key, String algorithm, String transformation) {
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("content can't be empty!");
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(content.getBytes());
            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    /**
     * 对称解密过程
     * @param content
     * @param key
     * @param algorithm
     * @param transformation
     * @return
     */
    private static String decrypt(String content, String key, String algorithm, String transformation) {
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("content can't be empty!");
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(Base64.decode(content.getBytes(), Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    public static PrivateKey generatePrivateKey(String key) {
        try {
            byte[] decodeKey = Base64.decode(key, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    public static PublicKey generatePublicKey(String key) {
        try {
            byte[] decodeKey = Base64.decode(key, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return null;
    }
}
