package com.sxu.baselibrary.commonutils;
import android.util.Log;

import com.sxu.baselibrary.BuildConfig;

/*******************************************************************************
 * Description: Log工具类
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class LogUtil {

	public static String TAG = "DEFAULT";

	/**
	 * 是否需要开启Log
	 */
	private static boolean needLog = BuildConfig.DEBUG;

	public static void i(String content) {
		if (needLog) {
			Log.i(TAG, getLogPrefix() + content);
		}
	}

	public static void i(String tag, String content) {
		if (needLog) {
			Log.i(tag, getLogPrefix() + content);
		}
	}

	public static void d(String content) {
		if (needLog) {
			Log.d(TAG, getLogPrefix() + content);
		}
	}

	public static void d(String tag, String content) {
		if (needLog) {
			Log.d(tag, getLogPrefix() + content);
		}
	}

	public static void e(String content) {
		if (needLog) {
			Log.e(TAG, getLogPrefix() + content);
		}
	}

	public static void e(String tag, String content) {
		if (needLog) {
			Log.e(tag, getLogPrefix() + content);
		}
	}

	public static void v(String content) {
		if (needLog) {
			Log.v(TAG, getLogPrefix() + content);
		}
	}

	public static void v(String tag, String content) {
		if (needLog) {
			Log.v(tag, getLogPrefix() + content);
		}
	}

	public static void w(String content) {
		if (needLog) {
			Log.w(TAG, getLogPrefix() + content);
		}
	}

	public static void w(String tag, String content) {
		if (needLog) {
			Log.w(tag, getLogPrefix() + content);
		}
	}

	private static String getLogPrefix() {
		return getClassName() + "(" + getLineNumber() + ")" + "$" + getMethodName() + ": ";
	}

	/**
	 * 获取Log所在的类名 （getStackTrace的索引根据调用的顺序来决定，可通过打印Log栈来获取）
	 * @return
	 */
	private static String getClassName() {
		try {
			String classPath = Thread.currentThread().getStackTrace()[5].getClassName();
			return classPath.substring(classPath.lastIndexOf(".") + 1);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return null;
	}

	/**
	 * 获取Log所在的方法名
	 * @return
	 */
	private static String getMethodName() {
		try {
			return Thread.currentThread().getStackTrace()[5].getMethodName();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return null;
	}

	/**
	 * 获取Log所在的行
	 * @return
	 */
	private static int getLineNumber() {
		try {
			return Thread.currentThread().getStackTrace()[5].getLineNumber();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return 0;
	}
}
