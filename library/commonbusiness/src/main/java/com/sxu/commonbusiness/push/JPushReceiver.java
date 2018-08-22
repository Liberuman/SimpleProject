package com.sxu.commonbusiness.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/*******************************************************************************
 * Description: 极光推送（PS: 不定义此Receiver时，点击通知默认会打开APP，且接收不到自定义消息）
 *
 * Author: Freeman
 *
 * Date: 2018/3/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class JPushReceiver extends BroadcastReceiver {

	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				PushManager.getInstance().getPushListener().onReceiveMessage(context,
						PushManager.PUSH_TYPE_JPUSH, bundle.getString(JPushInterface.EXTRA_EXTRA));
			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				PushManager.getInstance().getPushListener().onReceiveNotification(context,
						PushManager.PUSH_TYPE_JPUSH, bundle.getString(JPushInterface.EXTRA_EXTRA));
			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
				PushManager.getInstance().getPushListener().onReceiveNotification(context,
						PushManager.PUSH_TYPE_JPUSH, bundle.getString(JPushInterface.EXTRA_EXTRA));
			} else {
				/**
				 * Nothing
				 */
			}
		} catch (Exception e){
			e.printStackTrace(System.out);
		}

	}
}

