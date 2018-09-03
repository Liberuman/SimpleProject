package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
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
import com.sxu.baselibrary.commonutils.CollectionUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonbusiness.share.ShareListener;
import com.sxu.imageloader.FrescoInstance;
import com.sxu.imageloader.ImageLoaderListener;
import com.sxu.imageloader.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;

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
	private ShareListener listener;

	public WeiboShareInstance(Activity activity, ShareListener listener) {
		this.activity = activity;
		this.listener = listener;
		ImageLoaderManager.getInstance().init(activity.getApplicationContext(), new FrescoInstance());
		AuthInfo authInfo = new AuthInfo(activity, ShareConstants.APP_WEIBO_KEY, ShareConstants.REDIRECT_URL,
				ShareConstants.SCOPE);
 		WbSdk.install(activity, authInfo);

		shareHandler = new WbShareHandler(activity);
		shareHandler.registerApp();
		accessToken = AccessTokenKeeper.readAccessToken(activity);
	}

	public void onShare(final String title, final String desc, final String iconUrl, final String url) {
		if (accessToken == null || !accessToken.isSessionValid()) {
			ssoHandler = new SsoHandler(activity);
			ssoHandler.authorize(new AuthListener(title, desc, iconUrl, url));
			return;
		}

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
					shareMediaByWeibo(activity, desc, url, bitmap);
				}

				@Override
				public void onFailure(Exception e) {
					shareMediaByWeibo(activity, desc, url,
							BitmapUtil.drawableToBitmap(activity.getResources().getDrawable(R.drawable.ic_launcher)));
				}
			});
		} else {
			shareMediaByWeibo(activity, desc, url,
					BitmapUtil.drawableToBitmap(activity.getResources().getDrawable(R.drawable.ic_launcher)));
		}
	}

	/**
	 * 普通分享，支持文字，链接，图片  注意：微博分享设置title无效，链接应追加在desc后面
	 * @param activity
	 * @param desc
	 * @param url
	 * @param bitmap
	 */
	private void shareMediaByWeibo(Activity activity, String desc, String url, Bitmap bitmap) {
		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
		if (!TextUtils.isEmpty(desc)) {
			TextObject textObject = new TextObject();
			textObject.text = new StringBuilder(desc).append(" ").append(url).toString();
			multiMessage.textObject = textObject;
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			ImageObject imageObject = new ImageObject();
			imageObject.setImageObject(bitmap);
			multiMessage.imageObject = imageObject;
		}

		shareHandler.shareMessage(multiMessage, false);
	}

	/**
	 * 多图分享
	 * @param activity
	 * @param desc
	 * @param url
	 * @param imageList
	 */
	private void shareMediaByWeibo(Activity activity, String desc, String url, ArrayList<Uri> imageList) {
		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
		if (!TextUtils.isEmpty(desc)) {
			TextObject textObject = new TextObject();
			textObject.text = new StringBuilder(desc).append(" ").append(url).toString();
			multiMessage.textObject = textObject;
		}
		if (!CollectionUtil.isEmpty(imageList)) {
			MultiImageObject multiImageObj = new MultiImageObject();
			multiImageObj.setImageList(imageList);
			multiMessage.multiImageObject = multiImageObj;
		}

		shareHandler.shareMessage(multiMessage, false);
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, intent);
		}
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

	/***
	 * 授权监听
	 */
	class AuthListener implements WbAuthListener {

		private String desc;
		private String url;
		private String iconUrl;

		public AuthListener(String title, String desc, String url, String iconUrl) {
			this.desc = desc;
			this.url = url;
			this.iconUrl = iconUrl;
		}

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
				AccessTokenKeeper.writeAccessToken(activity.getApplicationContext(), accessToken);
				onShare(null, desc, url, iconUrl);
			} else {
				LogUtil.i("微博开发账号签名错误");
				activity.finish();
			}
		}

		@Override
		public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
			LogUtil.i("微博授权失败" + wbConnectErrorMessage.getErrorCode());
			activity.finish();
		}
	}
}
