package com.sxu.commonbusiness.login.instance;

import android.app.Activity;
import android.content.Intent;

import com.sxu.commonbusiness.login.LoginConstants;
import com.sxu.commonbusiness.login.listener.AuthListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.json.JSONObject;

/*******************************************************************************
 * Description: QQ登录
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public class QQLoginInstance extends LoginInstance {

	private Activity activity;
	private Tencent tencent;
	private AuthListener listener;

	public QQLoginInstance(Activity activity, AuthListener listener) {
		this(activity, LoginConstants.APP_QQ_KEY, listener);
	}

	public QQLoginInstance(Activity activity, String appKey, AuthListener listener) {
		this.activity = activity;
		this.listener = listener;
		tencent = Tencent.createInstance(appKey, activity);
	}

	@Override
	public void onLogin() {
		tencent.login(activity, "all", this);
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		Tencent.onActivityResultData(requestCode, resultCode, intent, this);
	}

	@Override
	public void loginSucceed(Object response) {
		if (listener == null) {
			return;
		}

		try {
			String openId = ((JSONObject) response).getString(Constants.PARAM_OPEN_ID);
			listener.onAuthSucceed(openId);
		} catch (Exception e) {
			listener.onAuthFailed(e);
			e.printStackTrace(System.err);
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
