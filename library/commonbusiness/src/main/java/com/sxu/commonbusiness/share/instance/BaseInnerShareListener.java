package com.sxu.commonbusiness.share.instance;

import com.sina.weibo.sdk.share.WbShareCallback;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/*******************************************************************************
 * Description: 监听分享结果
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
abstract class BaseInnerShareListener implements WbShareCallback, IUiListener {

	/**
	 * 微博分享回调
	 */
	@Override
	public void onWbShareCancel() {
		shareCancel();
	}

	@Override
	public void onWbShareFail() {
		shareFailure(new Exception("shared filed"));
	}

	@Override
	public void onWbShareSuccess() {
		shareSuccess();
	}

	/**
	 * QQ分享回调
	 * @param o
	 */
	@Override
	public void onComplete(Object o) {
		shareSuccess();
	}

	@Override
	public void onError(UiError uiError) {
		shareFailure(new Exception("errorCode:" + uiError.errorCode + " errorMsg:" + uiError.errorMessage
				+ " detailMsg:" + uiError.errorDetail));


	}

	@Override
	public void onCancel() {
		shareCancel();
	}

	/**
	 * 由于不同的平台的分享回调不同，所以添加以下三个方法便于统一回调结果的通知
	 */
	public abstract void shareSuccess();

	/**
	 * 分享失败
	 * @param e
	 */
	public abstract void shareFailure(Exception e);

	/**
	 * 取消分享
	 */
	public abstract void shareCancel();
}
