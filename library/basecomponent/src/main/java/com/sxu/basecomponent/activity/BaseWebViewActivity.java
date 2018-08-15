package com.sxu.basecomponent.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.NetworkUtil;
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
public class BaseWebViewActivity extends BaseActivity {

	protected WebView webView;

	protected String title;
	protected String url;

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
		if (intent != null) {
			url = intent.getStringExtra("url");
			title = intent.getStringExtra("title");
			boolean needShare = getIntent().getBooleanExtra("needShare", false);
		}

		loadUrl(url);
	}

	protected void loadUrl(String url) {
		webView.loadUrl(url);
	}

	private void initWebView() {
		WebSettings settings = webView.getSettings();
		// 支持JS
		settings.setJavaScriptEnabled(true);
		// 自适应屏幕
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		// 支持缩放
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		// 隐藏缩放控件
		settings.setDisplayZoomControls(false);
		// 开启Dom存储功能
		settings.setDomStorageEnabled(true);
		// 开启文件访问
		settings.setAllowFileAccess(true);
		settings.setLoadsImagesAutomatically(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// 设置缓存策略
		if (NetworkUtil.isConnected(this)) {
			settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		} else {
			settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				// 页面开始加载时调用，如显示加载圈
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// 页面加载完成时调用，如隐藏加载圈
			}

			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				super.onReceivedError(view, request, error);
				// 页面出错时调用，可在此时加载失败时的页面
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String webTitle) {
				super.onReceivedTitle(view, webTitle);
				if (!TextUtils.isEmpty(webTitle)) {
					toolbar.setTitle(webTitle);
				} else {
					toolbar.setTitle(title);
				}
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				// 获取页面加载的进度
			}
		});
	}

	public static void enter(Context context, String title, String url) {
		Intent intent  = new Intent(context, BaseWebViewActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		webView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		webView.onPause();
	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		webView.removeAllViews();
		webView.destroy();
		((ViewGroup)webView.getParent()).removeAllViews();
		webView = null;
		super.onDestroy();
		System.exit(0);
	}
}
