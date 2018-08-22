package com.sxu.commonbusiness.push;

import android.content.Context;

import com.google.gson.JsonObject;

/*******************************************************************************
 * Description: 推送回调接口
 *
 * Author: Freeman
 *
 * Date: 2018/7/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public interface PushListener {

	/**
	 * 接收到透传消息时的回调
	 * @param context
	 * @param object
	 */
	void onReceiveMessage(Context context, int pushChannel, Object object);

	/**
	 * 接收到通知时的回调
	 * @param context
	 * @param object
	 */
	void onReceiveNotification(Context context, int pushChannel, Object object);

	/**
	 * 点击通知时的回调
	 * @param context
	 * @param object
	 */
	void onNotificationClicked(Context context, int pushChannel, Object object);
}
