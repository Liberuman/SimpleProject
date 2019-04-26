package com.sxu.baselibrary.commonutils;

import android.text.TextUtils;
import android.util.Base64;

import java.net.URLEncoder;
import java.util.Arrays;

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

	private EncodeUtil() {

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
				return Arrays.toString(Base64.encode(content.getBytes("UTF-8"), Base64.DEFAULT));
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
				return Arrays.toString(Base64.decode(content.getBytes("UTF-8"), Base64.DEFAULT));
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		return null;
	}
}
