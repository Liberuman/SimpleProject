package com.sxu.baselibrary.commonutils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import java.io.File;

/*******************************************************************************
 * Description: 启动其他的APP
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class LaunchUtil {

	/**
	 * 打开浏览器
	 * @param context
	 */
	public static void openBrowse(Context context, String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			context.startActivity(intent);
		}
	}

	/**
	 * 打开应用市场
	 * @param context
	 */
	public static void openAppMarket(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
			context.startActivity(intent);
		}
	}

	/**
	 * 发送短信
	 * @param context
	 * @param telNumber
	 * @param content
	 */
	public static void sendMessage(Context context, String telNumber, String content) {
		if (!VerificationUtil.isValidTelNumber(telNumber)) {
			ToastUtil.show("请输入正确的手机号");
			return;
		}

		Uri uri = Uri.parse("smsto:" + telNumber);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.putExtra("sms_body", content);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	/**
	 * 打开短信界面
	 * @param context
	 * @param telNumber
	 */
	public static void openMessage(Context context, String telNumber) {
		Uri uri = Uri.parse("smsto:" + telNumber);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	/**
	 * 打开拨号界面
	 * @param context
	 * @param telNumber
	 */
	public static void openPhone(Context context, String telNumber) {
		Uri uri = Uri.parse("tel:" + telNumber);
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	/**
	 * 安装APP
	 * @param context
	 * @param fileName
	 */
	public static void installApp(Context context, String fileName) {
		Uri uri = Uri.fromFile(new File(fileName));
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.setDataAndType(uri, "application/vnd.android.package-archive");
			context.startActivity(intent);
		}
	}

	/**
	 * 打开应用的设置界面
	 * @param context
	 */
	public static void openAppSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.setData(Uri.fromParts("package", context.getPackageName(), null));
			context.startActivity(intent);
		}
	}

	/**
	 * 回到桌面
	 * @param context
	 */
	public static void backToLauncher(Context context) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			context.startActivity(intent);
		}
	}
}
