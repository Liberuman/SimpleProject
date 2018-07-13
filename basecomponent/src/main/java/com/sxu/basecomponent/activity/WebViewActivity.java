package com.sxu.basecomponent.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.ToastUtil;


/*******************************************************************************
 * FileName: WebViewActivity
 *
 * Description: 通用的网页加载页面
 *
 * Author: Freeman
 *
 * Version: v1.0
 *
 * Date: 2018/1/24
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class WebViewActivity extends BaseActivity {

	protected WebView webView;

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_webview_layout;
	}

	@Override
	protected void getViews() {
		webView = (WebView) findViewById(R.id.web_view);
	}

	@Override
	protected void initActivity() {
		initWebView();
		Intent intent = getIntent();
		String url = "";
		String title = "";
		if (intent != null) {
			url = intent.getStringExtra("url");
			title = intent.getStringExtra("title");
			boolean needShare = getIntent().getBooleanExtra("needShare", false);
			if (needShare) {
				navigationBar.setRightIconResAndEvent(R.drawable.nav_share_grey_icon, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						shareInfo();
					}
				});
			}
		}

		loadUrl(url);
		navigationBar.setTitle(!TextUtils.isEmpty(title) ? title : "加载中");
	}

	protected void loadUrl(String url) {
		webView.loadUrl(url);
	}

	protected void shareInfo() {
		ToastUtil.show("分享");
	}

	private void initWebView() {
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true);
		settings.setDomStorageEnabled(true);
		settings.setLoadsImagesAutomatically(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
	}

	public static void enter(Context context, String title, String url) {
		Intent intent  = new Intent(context, WebViewActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	public static void enter(Context context, boolean needShare, String title, String url) {
		Intent intent  = new Intent(context, WebViewActivity.class);
		intent.putExtra("needShare", needShare);
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	public static void enter(Context context, String title, String desc, String url, String iconUrl) {
		Intent intent  = new Intent(context, WebViewActivity.class);
		intent.putExtra("needShare", true);
		intent.putExtra("title", title);
		intent.putExtra("desc", desc);
		intent.putExtra("url", url);
		intent.putExtra("iconUrl", iconUrl);
		context.startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}
}
