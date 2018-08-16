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

import java.util.Map;

/*******************************************************************************
 * Description: 支付管理器
 *
 * Author: Freeman
 *
 * Date: 2018/07/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PayManager {

	private volatile static PayManager instance;
	private IWXAPI mWxApi;
	private Handler mHandler;
	private AliPayListener mListener;

	private final static int MSG_WHAT_ALI_PAY = 1001;
	private final static String PAY_RESULT_SUCCESS = "9000";

	private PayManager(Context context) {
		mWxApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), null);
		mHandler = new Handler(context.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				handlePayMessage(msg);
			}
		};
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
				Map<String, String> result = aliPay.payV2(requestStr, true);
				Message msg = Message.obtain();
				msg.what = MSG_WHAT_ALI_PAY;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	public boolean handlePayMessage(Message msg) {
		if (msg.what == MSG_WHAT_ALI_PAY) {
			if (mListener != null) {
				PayResultBean payResultBean = new PayResultBean((Map<String, String>)msg.obj);
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
