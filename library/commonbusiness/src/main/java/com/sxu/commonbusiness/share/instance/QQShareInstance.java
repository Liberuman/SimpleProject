package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonbusiness.share.ShareListener;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/*******************************************************************************
 * Description: QQ分享的实现
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class QQShareInstance extends ShareInstance {

	private int flowId;
	private Activity activity;
	private ShareListener listener;
	private Tencent tencent;

	public QQShareInstance(Activity activity, int flowId, ShareListener listener) {
		this.activity = activity;
		this.flowId = flowId;
		this.listener = listener;
		tencent = Tencent.createInstance(ShareConstants.APP_QQ_KEY, activity);
	}

	@Override
	public void onShare(String title, String desc, String iconUrl, String url) {
		if (tencent != null) {
			Bundle params = new Bundle();
			if (flowId == ShareConstants.SHARE_BY_QQ) {
				params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
				params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
				params.putString(QQShare.SHARE_TO_QQ_SUMMARY, desc);
				params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
				params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, iconUrl);
				tencent.shareToQQ(activity, params, this);
			} else {
				final ArrayList<String> imageList = new ArrayList<>();
				imageList.add(iconUrl);
				params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
						QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
				params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
				params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, desc);
				params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
				params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageList);
				tencent.shareToQzone(activity, params, this);
			}
		}
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		tencent.onActivityResultData(requestCode, resultCode, intent, this);
	}

	@Override
	public void shareSuccess() {
		if (listener != null) {
			listener.onShareSucceed();
		}
		activity.finish();
	}

	@Override
	public void shareFailure(Exception e) {
		if (listener != null) {
			listener.onShareFailed(e);
		}
		activity.finish();
	}

	@Override
	public void shareCancel() {
		if (listener != null) {
			listener.onShareFailed(new Exception("Share is canceled"));
		}
		activity.finish();
	}
}
