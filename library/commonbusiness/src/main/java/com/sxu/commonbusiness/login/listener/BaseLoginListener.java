package com.sxu.commonbusiness.login.listener;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/*******************************************************************************
 * Description: 为各个登录方式提供统一的登录监听
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public abstract class BaseLoginListener implements WbAuthListener, IUiListener {

	/**
	 * QQ登录回调
	 */
	@Override
	public final void onComplete(Object o) {
		loginSucceed(o);
	}

	@Override
	public final void onError(UiError uiError) {
		loginFailed(new Exception("errorCode:" + uiError.errorCode + " errorMsg:" + uiError.errorMessage
				+ " detailMsg:" + uiError.errorDetail));
	}

	@Override
	public final void onCancel() {
		loginCanceled();
	}

	/**
	 * 微博登录回调
	 */
	@Override
	public final void onSuccess(Oauth2AccessToken oauth2AccessToken) {
		loginSucceed(oauth2AccessToken);
	}

	@Override
	public final void cancel() {
		loginCanceled();
	}

	@Override
	public final void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
		loginFailed(new Exception(wbConnectErrorMessage.getErrorMessage()));
	}

	/**
	 * 登录成功(由于不同的平台的分享回调不同，所以添加以下三个方法便于统一回调结果的通知)
	 * @param response
	 */
	public abstract void loginSucceed(Object response);

	/**
	 * 登录失败
	 * @param e
	 */
	public abstract void loginFailed(Exception e);

	/**
	 * 取消登录
	 */
	public abstract void loginCanceled();
}