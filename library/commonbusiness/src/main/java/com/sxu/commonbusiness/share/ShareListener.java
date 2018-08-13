package com.sxu.commonbusiness.share;

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
public abstract class ShareListener implements WbShareCallback, IUiListener {

	/**
	 * 微信分享回调
	 */
//	@Override
//	public void onReq(BaseReq baseReq) {
//		LogUtil.i("onReq");
//	}
//
//
//	@Override
//	public void onResp(BaseResp baseResp) {
//		LogUtil.i("onResp");
//		switch (baseResp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				shareSuccess();
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				shareCancel();
//				break;
//			default:
//				shareFailure(new Exception(baseResp.errStr));
//				break;
//		}
//	}

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
		shareFailure(new Exception(new StringBuilder("errorCode:").append(uiError.errorCode)
				.append(" errorMsg:").append(uiError.errorMessage)
				.append(" detailMsg:").append(uiError.errorDetail).toString()));
	}

	@Override
	public void onCancel() {
		shareCancel();
	}

	/**
	 * 由于不同的平台的分享回调不同，所以添加以下三个方法便于统一回调结果的通知
	 */
	public abstract void shareSuccess();

	public abstract void shareFailure(Exception e);

	public abstract void shareCancel();
}
