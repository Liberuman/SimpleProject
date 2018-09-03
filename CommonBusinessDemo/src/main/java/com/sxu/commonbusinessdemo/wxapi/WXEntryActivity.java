package com.sxu.commonbusinessdemo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.login.instance.WXLoginInstance;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonbusiness.share.instance.WeChatShareInstance;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxApi;

    private final int WX_SDK_LOGIN = 1;
    private final int WX_SDK_SHARE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxApi =  WXAPIFactory.createWXAPI(this, ShareConstants.APP_WECHAT_KEY, false);
        wxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    // 微信SDK的回调处理过程，包括分享，登录
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == WX_SDK_LOGIN) {
            WXLoginInstance.onResp(this, resp);
        } else if (resp.getType() == WX_SDK_SHARE) {
            WeChatShareInstance.onResp(this, resp);
        }
    }
}