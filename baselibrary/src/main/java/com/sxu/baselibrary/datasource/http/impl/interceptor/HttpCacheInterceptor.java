package com.sxu.baselibrary.datasource.http.impl.interceptor;

import android.content.Context;

import com.sxu.baselibrary.commonutils.NetworkUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* *****************************************************************************
 * Description: 缓存预处理拦截器，对HTTP Response缓存之前的预处理
 *
 * Author: Freeman
 *
 * Date: 2018/01/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class HttpCacheInterceptor implements Interceptor {

	private Context context;

	public HttpCacheInterceptor(Context context) {
		this.context = context;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {

		Response response = chain.proceed(chain.request());
		if (NetworkUtil.isConnected(context)) {
			HttpUrl newUrl = chain.request().url().newBuilder()
					.removeAllQueryParameters("network")
					.removeAllQueryParameters("carrier")
					.build();
			Request newRequest = chain.request().newBuilder()
					.url(newUrl)
					.build();
			return response.newBuilder()
					.request(newRequest)
					.removeHeader("Pragma")
					.header("Cache-Control", "public, max-age=" + 1)
					.build();
		}

		return response;
	}
}
