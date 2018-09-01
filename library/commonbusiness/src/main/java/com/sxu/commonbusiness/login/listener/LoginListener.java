package com.sxu.commonbusiness.login.listener;

import android.os.Bundle;

import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/*******************************************************************************
 * Description: 为各个登录方式提供统一的登录监听
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public abstract class LoginListener implements IUiListener {

	/**
	 * QQ登录回调
	 * @param o
	 */
	@Override
	public final void onComplete(Object o) {
		loginSucceed(o);
	}

	@Override
	public final void onError(UiError uiError) {
		loginFailed(new Exception(new StringBuilder("errorCode:").append(uiError.errorCode)
				.append(" errorMsg:").append(uiError.errorMessage)
				.append(" detailMsg:").append(uiError.errorDetail).toString()));
	}

	@Override
	public final void onCancel() {
		loginCanceled();
	}

	/**
	 * 由于不同的平台的分享回调不同，所以添加以下三个方法便于统一回调结果的通知
	 */
	public abstract void loginSucceed(Object response);

	public abstract void loginFailed(Exception e);

	public abstract void loginCanceled();
}
