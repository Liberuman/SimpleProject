package com.sxu.commonbusiness.login.instance;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.login.bean.QQUserInfoBean;
import com.sxu.commonbusiness.login.listener.LoginListener;
import com.sxu.commonbusiness.share.ShareConstants;
import com.tencent.connect.UserInfo;
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

	private String openId;
	private String token;
	private String expires;
	private boolean isUserInfoListener = false;

	private Activity activity;
	private Tencent tencent;
	private LoginListener listener;

	public QQLoginInstance(Activity activity, LoginListener listener) {
		this(activity, ShareConstants.APP_QQ_KEY, listener);
	}

	public QQLoginInstance(Activity activity, String appKey, LoginListener listener) {
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
		if (response == null) {
			if (listener != null) {
				listener.loginFailed(new NullPointerException("User information is null"));
			}
			return;
		}

		if (isUserInfoListener && listener != null) {
			QQUserInfoBean userInfo = new Gson().fromJson(response.toString(), QQUserInfoBean.class);
			if (userInfo != null) {
				userInfo.openid = openId;
				userInfo.token = token;
				userInfo.expires = expires;
			}
			listener.onComplete(userInfo);
		} else {
			try {
				JSONObject result = (JSONObject) response;
				openId = result.getString(Constants.PARAM_OPEN_ID);
				token = result.getString(Constants.PARAM_ACCESS_TOKEN);
				expires = result.getString(Constants.PARAM_EXPIRES_IN);
				tencent.setOpenId(openId);
				tencent.setAccessToken(token, expires);
				UserInfo userInfo = new UserInfo(activity, tencent.getQQToken());
				userInfo.getUserInfo(this);
				isUserInfoListener = true;
			} catch (Exception e) {
				if (listener != null) {
					listener.loginFailed(new Exception(e.getMessage()));
				}
				e.printStackTrace(System.err);
			}
		}
	}

	@Override
	public void loginFailed(Exception e) {
		if (listener != null) {
			listener.loginFailed(e);
		} else {
			ToastUtil.show(e.getMessage());
		}
	}

	@Override
	public void loginCanceled() {
		if (listener != null) {
			listener.loginCanceled();
		}
	}
}
