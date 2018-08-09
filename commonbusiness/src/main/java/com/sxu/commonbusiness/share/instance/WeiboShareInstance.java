package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sxu.baselibrary.commonutils.BitmapUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.share.ShareConstants;

/*******************************************************************************
 * Description: 微博分享的实现
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class WeiboShareInstance extends ShareInstance {

	private Oauth2AccessToken accessToken;
	private SsoHandler ssoHandler;
	private WbShareHandler shareHandler;
	private Activity activity;
	private final int SHARE_IMAGE_MAX_SIZE = 32 * 1024 * 8; // 分享的图片不能大于32K

	public WeiboShareInstance(Activity activity) {
		this.activity = activity;
		// todo 检查图片加载库是否初始化
//		if (!ImageLoader.getInstance().isInited()) {
//			ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));
//		}
 		WbSdk.install(activity, new AuthInfo(activity, ShareConstants.APP_WEIBO_KEY, ShareConstants.REDIRECT_URL,
				ShareConstants.SCOPE));
		shareHandler = new WbShareHandler(activity);
		shareHandler.registerApp();
		accessToken = AccessTokenKeeper.readAccessToken(activity);
	}

	public void onShare(final String title, final String desc, final String iconUrl, final String url) {
		if (accessToken == null || !accessToken.isSessionValid()) {
			ssoHandler = new SsoHandler(activity);
			ssoHandler.authorize(new AuthListener());
			return;
		}

		if (!TextUtils.isEmpty(iconUrl)) {
//			ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
//				@Override
//				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//					super.onLoadingComplete(imageUri, view, loadedImage);
//					shareMediaByWeibo(activity, title, desc, url, BitmapUtil.zoomBitmap(loadedImage, SHARE_IMAGE_MAX_SIZE));
//				}
//
//				@Override
//				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//					super.onLoadingFailed(imageUri, view, failReason);
//					shareMediaByWeibo(activity, title, desc, url, null);
//				}
//
//				@Override
//				public void onLoadingCancelled(String imageUri, View view) {
//					super.onLoadingCancelled(imageUri, view);
//					shareMediaByWeibo(activity, title, desc, url, null);
//				}
//			});
		} else {
			shareMediaByWeibo(activity, title, desc, url, null);
		}
	}

	private void shareMediaByWeibo(Activity activity, String title, String desc, String url, Bitmap bitmap) {
		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
		if (!TextUtils.isEmpty(title)) {
			TextObject textObject = new TextObject();
			textObject.text = title;
			multiMessage.textObject = textObject;
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			ImageObject imageObject = new ImageObject();
			imageObject.setImageObject(bitmap);
			multiMessage.imageObject = imageObject;
		}

		WebpageObject mediaObject = new WebpageObject();
		mediaObject.identify = "123";
		mediaObject.title = ""; // 拼接在textObject.text后面
		mediaObject.description = desc; // 有什么用？
		Bitmap  bitmap2 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.cb_share_weibo_icon);
		// 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
		mediaObject.setThumbImage(bitmap2); // 必须设置 否则分享失败
		mediaObject.actionUrl = "";
		mediaObject.defaultText = ""; // textObject.text和mediaObject.title都为空时显示
		multiMessage.mediaObject = mediaObject;
		shareHandler.shareMessage(multiMessage, false);
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		if (ssoHandler != null) {
			LogUtil.i("ssoHandle is not null");
			ssoHandler.authorizeCallBack(requestCode, resultCode, intent);
		} else {
			LogUtil.i("ssoHandle is null");
			shareHandler.doResultIntent(intent, this);
		}
	}

	@Override
	public void shareSuccess() {
		ToastUtil.show("分享成功");
		activity.finish();
	}

	@Override
	public void shareFailure(Exception e) {
		ToastUtil.show("分享失败");
		activity.finish();
	}

	@Override
	public void shareCancel() {
		ToastUtil.show("取消分享");
		activity.finish();
	}

	/***
	 * 实现WeiboAuthListener接口，返回授权结果
	 * 通过access_token和expires_in获取accesstoken
	 * @author Administrator
	 *
	 */
	class AuthListener implements WbAuthListener {

		@Override
		public void cancel() {
			LogUtil.i("微博授权取消");
			activity.finish();
		}

		@Override
		public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
			accessToken = oauth2AccessToken;
			LogUtil.i("微博授权完成" + accessToken);
			if (accessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(activity, accessToken);
				onShare("title", "desc", "", "http://m.Freeman.com");
			} else {
				LogUtil.i("微博开发账号签名错误");
			}
		}

		@Override
		public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
			LogUtil.i("微博授权失败" + wbConnectErrorMessage.getErrorMessage());
			activity.finish();
		}
	}
}
