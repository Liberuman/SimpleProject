package com.sxu.baselibrary.datasource.http.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 兼容汉字md5
 */
public class MD5Util {
	public static String toMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		StringBuffer hexValue = null;

		if (str != null) {

			byte[] byteArray = new byte[0];
			try {
				byteArray = str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			byte[] md5Bytes = md5.digest(byteArray);
			hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = md5Bytes[i] & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		}
		return null;
	}
}
