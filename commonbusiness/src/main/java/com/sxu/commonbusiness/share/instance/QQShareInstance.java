package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareConstants;
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
	private Tencent tencent;

	public QQShareInstance(Activity activity, int flowId) {
		this.activity = activity;
		this.flowId = flowId;
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
		ToastUtil.show(activity, "handleResult");
		tencent.onActivityResultData(requestCode, resultCode, intent, this);
	}

	@Override
	public void shareSuccess() {
		LogUtil.i("shareSuccess");
		ToastUtil.show(activity, "分享成功");
		activity.finish();
	}

	@Override
	public void shareFailure(Exception e) {
		LogUtil.i("shareFailure");
		ToastUtil.show(activity, "分享失败");
		activity.finish();
	}

	@Override
	public void shareCancel() {
		LogUtil.i("shareCancel");
		ToastUtil.show(activity, "取消分享");
		activity.finish();
	}
}
