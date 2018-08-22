package com.sxu.commonbusiness.push;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.sxu.baselibrary.commonutils.RomUtil;
import com.sxu.baselibrary.datasource.http.impl.HttpManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

/*******************************************************************************
 * Description: 推送管理器
 *
 * Author: Freeman
 *
 * Date: 2018/7/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class PushManager {

	/**
	 * 小米推送
	 */
	public final static int PUSH_TYPE_XIAOMI = 1;
	/**
	 * 华为推送
	 */
	public final static int PUSH_TYPE_HUAWEI = 2;
	/**
	 * 极光推送
	 */
	public final static int PUSH_TYPE_JPUSH = 3;

	private PushListener mListener;

	private PushManager() {

	}

	public static PushManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 初始化推送 appId和appKey用于小米推送
	 * @param context
	 * @param appId
	 * @param appKey
	 */
	public void initPush(Context context, String appId, String appKey) {
		initPush(context, appId, appKey, null);
	}

	public void initPush(Context context, String appId, String appKey, PushListener listener) {
		if (RomUtil.isMiui()) {
			MiPushClient.registerPush(context.getApplicationContext(), appId, appKey);
		} else if (RomUtil.isEmui()) {
			initHWPush(context.getApplicationContext());
		} else {
			JPushInterface.init(context.getApplicationContext());
		}
		mListener = listener;
	}

	private void initHWPush(Context context) {
		HuaweiApiClient client = new HuaweiApiClient.Builder(context.getApplicationContext())
				.addApi(HuaweiPush.PUSH_API)
				.addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected() {
					}

					@Override
					public void onConnectionSuspended(int i) {

					}
				})
				.addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {
					@Override
					public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
					}
				})
				.build();
		client.connect();
		HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(client, true);
	}

	public void setPushListener(PushListener listener) {
		mListener = listener;
	}

	public PushListener getPushListener() {
		if (mListener == null) {
			throw new NullPointerException("mListener can't be null");
		}
		return mListener;
	}

	public static class SingletonHolder {
		private final static PushManager instance = new PushManager();
	}
}
