package com.sxu.baselibrary.datasource.http.impl.interceptor;

import android.text.TextUtils;

import com.sxu.baselibrary.datasource.preference.PreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* *****************************************************************************
 * Description: Token拦截器，负责Token的管理
 *
 * Author: Freeman
 *
 * Date: 2018/01/18
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class AuthorizationInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request newRequest = chain.request();
		String authKey = "KEY_AUTH";
		String authorization = PreferencesManager.getString(authKey);
		if (!TextUtils.isEmpty(authorization)) {
			newRequest = newRequest.newBuilder()
					.addHeader("Authorization", authorization)
					.build();
		}

		Response response = chain.proceed(newRequest);
		if (response != null) {
			authorization = response.header("WWW-Authenticate");
			if (!TextUtils.isEmpty(authorization)) {
				PreferencesManager.putString(authKey, authorization);
			}
		}

		return response;
	}
}
