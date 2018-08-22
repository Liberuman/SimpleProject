package com.sxu.commonbusiness.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.sxu.commonbusiness.share.ShareManager;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/*******************************************************************************
 * Description: 小米推送
 *
 *  透传与通知的区别：
 *  透传不会在通知栏显示，只是透明地传输数据（可自定义数据格式），但定制性更强；
 *  通知即通常的推送，会自动显示在通知栏，当然也可定义其行为，如通过Map结构传输额外的数据，通知的点击行为等；
 *
 * Author: Freeman
 *
 * Date: 2018/3/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class MiPushReceiver extends PushMessageReceiver {

	// 接收服务器向客户端发送的透传消息
	@Override
	public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
		PushManager.getInstance().getPushListener().onReceiveMessage(context, PushManager.PUSH_TYPE_XIAOMI, message);
	}

	// 服务器向客户端发送的通知消息的点击事件
	@Override
	public void onNotificationMessageClicked(Context context, MiPushMessage message) {
		PushManager.getInstance().getPushListener().onNotificationClicked(context, PushManager.PUSH_TYPE_XIAOMI, message);
	}

	// 接收服务器向客户端发送的通知消息, 这个回调方法是在通知消息到达客户端时触发。
	// 另外应用在前台时不弹出通知, 但仍会触发这个回调函数
	@Override
	public void onNotificationMessageArrived(Context context, MiPushMessage message) {
		PushManager.getInstance().getPushListener().onReceiveNotification(context, PushManager.PUSH_TYPE_XIAOMI, message);
	}
}
