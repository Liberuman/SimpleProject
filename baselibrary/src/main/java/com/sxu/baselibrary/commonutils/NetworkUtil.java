package com.sxu.baselibrary.commonutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/*******************************************************************************
 * Description: 网络相关的工具类
 *
 * Author: Freeman
 *
 * Date: 2018/1/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class NetworkUtil {

	public final static String NETWORK_TYPE_2G = "2G";
	public final static String NETWORK_TYPE_3G = "3G";
	public final static String NETWORK_TYPE_4G = "4G";
	public final static String NETWORK_TYPE_WIFI = "Wifi";
	public final static String NETWORK_TYPE_NONE = "unknown";

	public final static String CARRIER_TYPE_CMCC = "CMCC"; // 中国移动
	public final static String CARRIER_TYPE_CUCC = "CUCC"; // 中国联通
	public final static String CARRIER_TYPE_CTCC = "CTCC"; // 中国电信
	public final static String CARRIER_TYPE_UNKNOWN = "UNKNOWN";

	/**
	 * 网络是否连接
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}

		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected() && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
				|| networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
			return true;
		}

		return false;
	}

	/**
	 * 获取网络类型
	 * @param context
	 * @return
	 */
	public static String getNetworkType(Context context) {
		String networkType = NETWORK_TYPE_NONE;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return networkType;
		}

		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {
			return networkType;
		}

		int type = networkInfo.getType();
		if (type == ConnectivityManager.TYPE_MOBILE) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm != null) {
				networkType = getMobileNetworkType(tm.getNetworkType());
			}
		} else if (type == ConnectivityManager.TYPE_WIFI) {
			networkType = NETWORK_TYPE_WIFI;
		} else {
			/**
			 * Nothing
			 */
		}

		return networkType;
	}

	/**
	 * 获取手机网络类型
	 * @param networkType
	 * @return
	 */
	private static String getMobileNetworkType(int networkType) {
		String networkTypeStr = NETWORK_TYPE_NONE;
		switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_IDEN:
				networkTypeStr = NETWORK_TYPE_2G;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
			case TelephonyManager.NETWORK_TYPE_EHRPD:
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				networkTypeStr = NETWORK_TYPE_3G;
				break;
			case TelephonyManager.NETWORK_TYPE_LTE:
				networkTypeStr = NETWORK_TYPE_4G;
				break;
		}

		return networkTypeStr;
	}

	/**
	 * 获取运行商信息
	 * @param context
	 * @return
	 */
	public static String getCarrier(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return CARRIER_TYPE_UNKNOWN;
		}
		String subscriberId = tm.getSubscriberId();
		if (subscriberId == null || subscriberId.equals("")) {
			return CARRIER_TYPE_UNKNOWN;
		}

		if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002") || subscriberId.startsWith("46007")) {
			return CARRIER_TYPE_CMCC;
		} else if (subscriberId.startsWith("46001")) {
			return CARRIER_TYPE_CUCC;
		} else if (subscriberId.startsWith("46003")) {
			return CARRIER_TYPE_CTCC;
		}

		return CARRIER_TYPE_UNKNOWN;
	}
}
