package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;
import android.util.Base64;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;

/*******************************************************************************
 * Description: 字符串编码
 *
 * Author: Freeman
 *
 * Date: 2018/8/9
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public final class EncodeUtil {

	/**
	 * Md5编码
	 * @param content
	 * @return
	 */
	public static String encodeByMd5(String content) {
		return encodeByHash(content, "md5");
	}

	/**
	 * 通过指定算法编码
	 * @param content
	 * @param algorithm 支持的算法：MD5， SHA-1， SHA-224， SHA-256， SHA-384， SHA-512
	 * @return
	 */
	public static String encodeByHash(String content, String algorithm) {
		if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(algorithm)) {
			try {
				MessageDigest digest = MessageDigest.getInstance(algorithm.toUpperCase());
				byte[] md5 = digest.digest(content.getBytes());
				BigInteger bigInteger = new BigInteger(md5).abs();
				return bigInteger.toString(26);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		return null;
	}

	/**
	 * 使用utf-8字符集进行URL编码
	 * @param content
	 * @return
	 */
	public static String encodeByUrl(String content) {
		return encodeByUrl(content, "UTF-8");
	}

	/**
	 * 使用指定字符集进行URL编码
	 * @param content
	 * @return
	 */
	public static String encodeByUrl(String content, String charset) {
		if (!TextUtils.isEmpty(content)) {
			try {
				return URLEncoder.encode(content, charset);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		return null;
	}

	/**
	 * 使用utf-8字符集进行URL解码
	 * @param content
	 * @return
	 */
	public static String decodeByUrl(String content) {
		return decodeByUrl(content, "UTF-8");
	}

	/**
	 * 使用指定字符集进行URL解码
	 * @param content
	 * @return
	 */
	public static String decodeByUrl(String content, String charset) {
		if (!TextUtils.isEmpty(content)) {
			try {
				return URLEncoder.encode(content, charset);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		return null;
	}

	/**
	 * 使用Base64编码
	 * @param content
	 * @return
	 */
	public static String encodeByBase(String content) {
		if (!TextUtils.isEmpty(content)) {
			try {
				return Base64.encode(content.getBytes(), Base64.DEFAULT).toString();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		return null;
	}

	/**
	 * 使用Base64解码
	 * @param content
	 * @return
	 */
	public static String decodeByBase(String content) {
		if (!TextUtils.isEmpty(content)) {
			try {
				return Base64.decode(content.getBytes(), Base64.DEFAULT).toString();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		return null;
	}
}
