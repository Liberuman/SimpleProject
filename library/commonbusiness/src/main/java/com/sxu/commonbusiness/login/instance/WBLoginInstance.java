package com.sxu.commonbusiness.login.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sxu.commonbusiness.login.listener.LoginListener;
import com.sxu.commonbusiness.share.ShareConstants;

/*******************************************************************************
 * Description: 微博登录
 *
 * Author: Freeman
 *
 * Date: 2018/9/1
 *******************************************************************************/
public class WBLoginInstance extends LoginInstance {

	private Context context;
	private LoginListener listener;

	private SsoHandler ssoHandler;

	public WBLoginInstance(Activity activity, LoginListener listener) {
		this(activity, ShareConstants.APP_WEIBO_KEY, ShareConstants.REDIRECT_URL, ShareConstants.SCOPE, listener);
	}

	public WBLoginInstance(Activity activity, String appKey, String redirectUrl, String scope, LoginListener listener) {

	}

	@Override
	public void onLogin() {

	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		ssoHandler.authorizeCallBack(requestCode, resultCode, intent);
	}

	@Override
	public void loginSucceed(Object response) {

	}

	@Override
	public void loginFailed(Exception e) {

	}

	@Override
	public void loginCanceled() {

	}
}
