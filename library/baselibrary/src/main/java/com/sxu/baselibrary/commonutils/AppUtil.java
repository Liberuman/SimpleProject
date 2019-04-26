package com.sxu.baselibrary.commonutils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;

import java.util.List;

/*******************************************************************************
 * Description: APP相关的信息
 *
 * Author: Freeman
 *
 * Date: 2017/6/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class AppUtil {

	private AppUtil() {

	}

	/**
	 * 获取APP的VersionCode
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return 0;
	}

	/**
	 * 获取APP的版本号
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return null;
	}

	/**
	 * 获取APP的进程名
	 * @param context
	 * @return
	 */
	public static String getProcessName(Context context) {
		int pid = Process.myPid();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) {
			return null;
		}

		for (ActivityManager.RunningAppProcessInfo process : am.getRunningAppProcesses()) {
			if (process.pid == pid) {
				return process.processName;
			}
		}

		return null;
	}

	/**
	 * APP是否在运行中（前台或后台）
	 * @param context
	 * @return
	 */
	public static boolean appIsRunning(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) {
			return false;
		}

		List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
		if (appList == null) {
			return false;
		}

		for (ActivityManager.RunningAppProcessInfo appInfo : appList) {
			if (context.getPackageName().equals(appInfo.processName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * App是否在前台运行
	 * @param context
	 * @return
	 */
	public static boolean appIsForeground(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) {
			return false;
		}

		List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
		if (appList == null) {
			return false;
		}

		for (ActivityManager.RunningAppProcessInfo appInfo : appList) {
			if (context.getPackageName().equals(appInfo.processName)
					&& appInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据包名检查APP是否已安装
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean appIsInstalled(Application context, String packageName) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packageList = pm.getInstalledPackages(0);
		for (PackageInfo appInfo : packageList) {
			if (appInfo.packageName.equals(packageName)) {
				return true;
			}
		}

		return false;
	}
}


