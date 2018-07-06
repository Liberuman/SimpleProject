package com.sxu.baselibrary.datasource.http.impl.interceptor;

import android.content.Context;

import com.sxu.baselibrary.commonutils.AppUtil;
import com.sxu.baselibrary.commonutils.DeviceUtil;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.NetworkUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* *****************************************************************************
 * Description: 公共参数拦截器
 *
 * Author: Freeman
 *
 * Date: 2018/01/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class BaseInterceptor implements Interceptor {

	private Context mContext;

	public BaseInterceptor(Context context) {
		this.mContext = context;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		HttpUrl.Builder urlBuilder = chain.request().url().newBuilder()
				.addQueryParameter("app_version", AppUtil.getVersionName(mContext))
				.addQueryParameter("android_version", DeviceUtil.getOSVersion())
				.addQueryParameter("channel", "official")
				.addQueryParameter("res", DisplayUtil.getScreenParams())
				.addQueryParameter("did", DeviceUtil.getDeviceId(mContext))
				.addQueryParameter("mac", DeviceUtil.getMacAddress(mContext))
				.addQueryParameter("token", "");
		Request.Builder requestBuilder = chain.request().newBuilder();
		if (NetworkUtil.isConnected(mContext)) {
			urlBuilder.addQueryParameter("network", NetworkUtil.getNetworkType(mContext))
					.addQueryParameter("carrier", NetworkUtil.getCarrier(mContext));
		} else {
			requestBuilder.removeHeader("Pragma")
					.header("Cache-Control", "only-if-cached, max-stale=" + 30 * 24 * 60 * 60);
		}
		Request newRequest = requestBuilder
				.url(urlBuilder.build())
				.build();


		return chain.proceed(newRequest);
	}
}
