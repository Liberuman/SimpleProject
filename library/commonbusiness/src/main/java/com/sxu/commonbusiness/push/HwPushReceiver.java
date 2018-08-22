package com.sxu.commonbusiness.push;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;

/*******************************************************************************
 * Description: 华为推送
 *
 * Author: Freeman
 *
 * Date: 2018/3/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class HwPushReceiver extends PushReceiver {

	@Override
	public void onToken(Context context, String token, Bundle extras) {

	}

	@Override
	public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
		PushManager.getInstance().getPushListener().onNotificationClicked(context, PushManager.PUSH_TYPE_HUAWEI, msg);
		return false;
	}

	public void onEvent(Context context, Event event, Bundle extras) {
		super.onEvent(context, event, extras);
		if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
			PushManager.getInstance().getPushListener().onNotificationClicked(context, PushManager.PUSH_TYPE_HUAWEI, extras);
		}
	}

	@Override
	public void onPushState(Context context, boolean pushState) {
		try {
			String content = "The current push status： " + (pushState ? "Connected" : "Disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
