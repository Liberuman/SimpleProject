package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sxu.baselibrary.commonutils.BitmapUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.imageloader.FrescoInstance;
import com.sxu.imageloader.ImageLoaderListener;
import com.sxu.imageloader.ImageLoaderManager;
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

	public WeChatShareInstance(Activity activity, int flowId) {
		this.flowId = flowId;
		this.activity = activity;
		ImageLoaderManager.getInstance().init(activity.getApplicationContext(), new FrescoInstance());
		wxApi = WXAPIFactory.createWXAPI(activity, ShareConstants.APP_WECHAT_KEY, true);
		boolean result = wxApi.registerApp(ShareConstants.APP_WECHAT_KEY);
	}

	public void onShare(final String title, String desc, final String iconUrl, final String url) {
		if (!wxApi.isWXAppInstalled()) {
			shareFailure(new Exception("您还没有安装微信客户端哦~"));
			return;
		}

		final String content = desc;
		if (!TextUtils.isEmpty(iconUrl)) {
			ImageLoaderManager.getInstance().downloadImage(activity, iconUrl, new ImageLoaderListener() {
				@Override
				public void onStart() {

				}

				@Override
				public void onProcess(int i, int i1) {

				}

				@Override
				public void onCompleted(Bitmap bitmap) {
					startShare(title, content, url, bitmap);
				}

				@Override
				public void onFailure(Exception e) {
					startShare(title, content, url,
							BitmapUtil.drawableToBitmap(ActivityCompat.getDrawable(activity, R.drawable.ic_launcher)));
				}
			});
		} else {
			startShare(title, content, url,
					BitmapUtil.drawableToBitmap(ActivityCompat.getDrawable(activity, R.drawable.ic_launcher)));
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
		String transaction;
		WXMediaMessage msg;
		if (!TextUtils.isEmpty(url)) {
			transaction = "webpage";
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = url;
			msg = new WXMediaMessage(webpage);
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

		msg.title = title;
		msg.description = desc;
		msg.thumbData = BitmapUtil.bitmapToByteArray(bitmap, true);

		SendMessageToWX.Req request = new SendMessageToWX.Req();
		request.transaction = buildTransaction(transaction);
		request.message = msg;
		request.scene = (flowId == ShareConstants.SHARE_BY_WECHAT_MOMENT) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(request);
		activity.finish();
	}

	/**
	 * 小程序分享
	 *
	 * @param title
	 * @param desc
	 * @param url
	 * @param bitmap
	 *
	 * 注意：
	 * 小程序分享分为两种情况：
	 * 1. 小程序页面：url为小程序页面路径；
	 * 2. 网页：在小程序中打开可能还需要在URL前面添加路径；
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
		activity.finish();
	}

	private String buildTransaction(final String type) {
		return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	/**
	 * 微信分享的回调需要在包名.WXEntryActivity中统一处理，在其他页面不能监听回调。
	 */
	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {

	}

	@Override
	public void shareSuccess() {

	}

	@Override
	public void shareFailure(Exception e) {

	}

	@Override
	public void shareCancel() {

	}
}

