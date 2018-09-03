package com.sxu.commonbusiness.login.instance;

import android.app.Activity;
import android.content.Intent;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sxu.commonbusiness.login.LoginConstants;
import com.sxu.commonbusiness.login.listener.AuthListener;

/*******************************************************************************
 * Description: 微博登录
 *
 * Author: Freeman
 *
 * Date: 2018/9/1
 *******************************************************************************/
public class WBLoginInstance extends LoginInstance {

	private AuthListener listener;
	private SsoHandler ssoHandler;

	public WBLoginInstance(Activity activity, AuthListener listener) {
		this(activity, LoginConstants.APP_WEIBO_KEY, LoginConstants.REDIRECT_URL, LoginConstants.SCOPE, listener);
	}

	public WBLoginInstance(Activity activity, String appKey, String redirectUrl, String scope, AuthListener listener) {
		this.listener = listener;
		AuthInfo authInfo = new AuthInfo(activity, appKey, redirectUrl, scope);
		WbSdk.install(activity, authInfo);
		ssoHandler = new SsoHandler(activity);
	}

	@Override
	public void onLogin() {
		ssoHandler.authorize(this);
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		ssoHandler.authorizeCallBack(requestCode, resultCode, intent);
	}

	@Override
	public void loginSucceed(Object response) {
		if (listener != null) {
			if (response == null) {
				listener.onAuthFailed(new NullPointerException("Auth information is null"));
			}

			listener.onAuthSucceed(((Oauth2AccessToken) response).getUid());
		}
	}

	@Override
	public void loginFailed(Exception e) {
		if (listener != null) {
			listener.onAuthFailed(e);
		}
	}

	@Override
	public void loginCanceled() {
		if (listener != null) {
			listener.onAuthFailed(new Exception("Auth is cancelled!"));
		}
	}
}
