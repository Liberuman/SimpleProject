package com.sxu.commonbusiness.login.instance;

import android.content.Context;

import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareConstants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*******************************************************************************
 * Description: 微信登录
 *
 * Author: Freeman
 *
 * Date: 2018/9/1
 *******************************************************************************/
public class WXLoginInstance extends LoginInstance {

	private IWXAPI wxApi;
	private Context context;

	public WXLoginInstance(Context context) {
		this(context, ShareConstants.APP_WECHAT_KEY);
	}

	private WXLoginInstance(Context context, String appKey) {
		this.context = context;
		wxApi = WXAPIFactory.createWXAPI(context, appKey);
		wxApi.registerApp(appKey);
	}

	@Override
	public void onLogin() {
		if (wxApi.isWXAppInstalled()) {
			SendAuth.Req request = new SendAuth.Req();
			request.scope = "snsapi_userinfo";
			request.state = context.getPackageName();
			wxApi.sendReq(request);
		} else {
			ToastUtil.show("请先安装微信~");
		}
 	}
}
