package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.sxu.baselibrary.commonutils.BitmapUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareConstants;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*******************************************************************************
 * Description: 微信分享的实现
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class WeChatShareInstance extends ShareInstance {

	private int flowId;
	private Activity activity;
	private IWXAPI wxApi;

	private final int TEXT_MAX_LEN = 140; // 分享文字的最大长度
	private final int SHARE_IMAGE_MAX_SIZE = 32 * 1024 * 8; // 分享的图片不能大于32K

	public WeChatShareInstance(Activity activity, int flowId) {
		this.flowId = flowId;
		this.activity = activity;
		// todo 检查图片库是否初始化
		wxApi = WXAPIFactory.createWXAPI(activity, ShareConstants.APP_WECHAT_KEY, true);
		boolean result = wxApi.registerApp(ShareConstants.APP_WECHAT_KEY);
	}

	public void onShare(final String title, String desc, final String iconUrl, final String url) {
		if (!wxApi.isWXAppInstalled()) {
			shareFailure(new Exception("您还没有安装微信客户端哦~"));
			return;
		}

		if (desc != null && desc.length() > TEXT_MAX_LEN) {
			desc = desc.substring(0, TEXT_MAX_LEN);
		}
		final String content = desc;
		if (!TextUtils.isEmpty(iconUrl)) {
//			ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
//				@Override
//				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//					super.onLoadingComplete(imageUri, view, loadedImage);
//					startShare(title, content, url, BitmapUtil.zoomBitmap(loadedImage, SHARE_IMAGE_MAX_SIZE));
//				}
//
//				@Override
//				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//					super.onLoadingFailed(imageUri, view, failReason);
//					startShare(title, content, url, null);
//				}
//			});
		} else {
			startShare(title, content, url, null);
		}
	}

	private void startShare(String title, String desc, String url, Bitmap bitmap) {
		if (flowId == ShareConstants.SHARE_BY_MINI_PROGRAM) {
			shareByMiniProgram(title, desc, url, bitmap);
		} else {
			shareByWeChat(title, desc, url, bitmap);
		}
	}

	private void shareByWeChat(String title, String desc, String url, Bitmap bitmap) {
		String transaction = null;
		StringBuilder builder = new StringBuilder();
		WXMediaMessage msg = null;
		if (!TextUtils.isEmpty(title)) {
			builder.append(title);
		}
		if (!TextUtils.isEmpty(url)) {
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = url;
			transaction = "webpage";
			msg = new WXMediaMessage(webpage);
			builder.append(" ").append(url);
		} else {
			msg = new WXMediaMessage();
			if (bitmap != null && !bitmap.isRecycled()) {
				transaction = "image";
				msg.mediaObject = new WXImageObject(bitmap);
			} else {
				transaction = "text";
				msg.mediaObject = new WXTextObject(title);
			}
		}

		msg.title = builder.toString();
		msg.description = desc;
		msg.thumbData = BitmapUtil.bitmapToByteArray(bitmap, true);

		SendMessageToWX.Req request = new SendMessageToWX.Req();
		request.transaction = buildTransaction(transaction);
		request.message = msg;
		request.scene = (flowId == ShareConstants.SHARE_BY_WECHAT_MOMENT) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(request);
	}

	/**
	 * 小程序分享
	 *
	 * @param title
	 * @param desc
	 * @param url
	 * @param bitmap
	 */
	private void shareByMiniProgram(String title, String desc, String url, Bitmap bitmap) {
		WXMiniProgramObject object = new WXMiniProgramObject();
		object.webpageUrl = ShareConstants.APP_MINI_PROGRAM_WEB_PAGE;
		object.userName = ShareConstants.APP_MINI_PROGRAM_NAME;
		StringBuilder builder = new StringBuilder();
		object.path = url;
		WXMediaMessage msg = new WXMediaMessage(object);
		msg.title = title;
		msg.description = desc;
		msg.thumbData = BitmapUtil.bitmapToByteArray(bitmap, true);
		SendMessageToWX.Req request = new SendMessageToWX.Req();
		request.transaction = buildTransaction("webpage");
		request.message = msg;
		request.scene = SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(request);
	}

	private String buildTransaction(final String type) {
		return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		LogUtil.i("handeResult");
		//wxApi.handleIntent(intent, this);
	}

	@Override
	public void shareSuccess() {
		LogUtil.i("shareSuccess");
		ToastUtil.show("分享成功");
	}

	@Override
	public void shareFailure(Exception e) {
		LogUtil.i("shareFailure");
		ToastUtil.show("分享失败");
	}

	@Override
	public void shareCancel() {
		LogUtil.i("shareCancel");
		ToastUtil.show("取消分享");
	}
}
