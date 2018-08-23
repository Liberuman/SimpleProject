package com.sxu.commonbusinessdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sxu.commonbusiness.push.PushListener;
import com.sxu.commonbusiness.push.PushManager;

public class PushActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_layout);

		PushManager.getInstance().initPush(getApplicationContext(), "小米推送的appId",
				"小米推送的appKey");

		PushManager.getInstance().setPushListener(new PushListener() {
			@Override
			public void onReceiveMessage(Context context, int pushChannel, Object object) {
				/**
				 * 接收到自定义消息时的回调
				 */
			}

			@Override
			public void onReceiveNotification(Context context, int pushChannel, Object object) {
				/**
				 * 接收到通知时的回调
				 */
			}

			@Override
			public void onNotificationClicked(Context context, int pushChannel, Object object) {
				/**
				 * 点击推送通知时的回调
				 */
			}
		});
	}
}
