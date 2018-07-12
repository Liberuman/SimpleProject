package com.sxu.commonbusiness.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*******************************************************************************
 * Description: 支付管理器
 *
 * Author: Freeman
 *
 * Date: 2018/07/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PayManager implements Handler.Callback {

	private volatile static PayManager instance;
	private IWXAPI mWxApi;
	private Handler mHandler;
	private AliPayListener mListener;

	private final static int MSG_WHAT_ALI_PAY = 1001;
	private final static String PAY_RESULT_SUCCESS = "9000";

	private PayManager(Context context) {
		mWxApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), null);
		mHandler = new Handler(context.getMainLooper());
	}

	public static PayManager getInstance(Context context) {
		if (instance == null) {
			synchronized (PayManager.class) {
				if (instance == null) {
					instance = new PayManager(context);
				}
			}
		}

		return instance;
	}

	public boolean payByWeChat(PayRequestBean requestInfo, String orderId) {
		if (requestInfo != null && !TextUtils.isEmpty(orderId)) {
			PayReq req = new PayReq();
			req.appId = requestInfo.appId;
			req.nonceStr = requestInfo.nonceStr;
			req.packageValue = requestInfo.packageValue;
			req.partnerId = requestInfo.partnerId;
			req.prepayId = requestInfo.prepayId;
			req.sign = requestInfo.sign;
			req.timeStamp = requestInfo.timeStamp;
			req.extData = orderId;
			mWxApi.registerApp(requestInfo.appId);
			return mWxApi.sendReq(req);
		}

		return false;
	}

	public void payByAliPay(final Activity context, final String requestStr, AliPayListener listener) {
		this.mListener = listener;
		new Thread(new Runnable() {
			@Override
			public void run() {
				PayTask aliPay = new PayTask(context);
				String result = aliPay.pay(requestStr, true);
				Message msg = Message.obtain();
				msg.what = MSG_WHAT_ALI_PAY;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == MSG_WHAT_ALI_PAY) {
			if (mListener != null) {
				PayResultBean payResultBean = new PayResultBean((String)msg.obj);
				String resultStatus = payResultBean.getResultStatus();
				if (TextUtils.equals(resultStatus, PAY_RESULT_SUCCESS)) {
					mListener.onSuccess();
				} else {
					mListener.onFailure(new Exception(resultStatus));
				}
			}
		}

		return false;
	}

	public interface AliPayListener {
		void onSuccess();
		void onFailure(Exception e);
	}
}
