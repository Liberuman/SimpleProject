package com.sxu.simpleproject.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareConstants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*******************************************************************************
 * Description: 微信分享，登录的回调页面
 *
 * Author: Freeman
 *
 * Date: 2018/8/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

	private IWXAPI wxApi;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wxApi = WXAPIFactory.createWXAPI(this, ShareConstants.APP_WECHAT_KEY);
		wxApi.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		wxApi.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp baseResp) {
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				/**
				 * 成功的逻辑
				 */
				break;
			case BaseResp.ErrCode.ERR_SENT_FAILED:
				/**
				 * 失败的逻辑
				 */
				break;
			default:
				finish();
				break;
		}
	}
}
