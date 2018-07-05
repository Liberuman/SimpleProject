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
	public static void sendSms(Context context, String telNumber, String content) {
		if (VerificationUtil.isValidTelNumber(telNumber)) {
			Uri uri = Uri.parse("smsto:" + telNumber);
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
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
		if (VerificationUtil.isValidTelNumber(telNumber)) {
			Uri uri = Uri.parse("smsto:" + telNumber);
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
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
		if (VerificationUtil.isValidTelNumber(telNumber)) {
			Uri uri = Uri.parse("tel:" + telNumber);
			Intent intent = new Intent(Intent.ACTION_CALL, uri);
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
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static void openAppSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
		intent.setData(Uri.fromParts("package", context.getPackageName(), null));
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			context.startActivity(intent);
		}
	}
}
