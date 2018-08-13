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
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(50);
		//100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
		for (ActivityManager.RunningTaskInfo info : list) {
			if (context.getPackageName().equals(info.topActivity.getPackageName())
					|| context.getPackageName().equals(info.baseActivity.getPackageName())) {
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
		List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (!CollectionUtil.isEmpty(taskList) && context.getPackageName().equals(taskList.get(0).topActivity.getPackageName())) {
			return true;
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

	/**
	 * 指定的Activity是否运行在前台
	 * @param context
	 * @param className
	 * @return
	 */
	public static boolean activityIsForeground(Context context, String className) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
		return !CollectionUtil.isEmpty(taskList) && className.equals(taskList.get(0).topActivity.getClassName());
	}
}


