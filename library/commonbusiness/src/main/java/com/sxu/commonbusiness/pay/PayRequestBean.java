package com.sxu.commonbusiness.pay;

/*******************************************************************************
 * Description: 微信支付数据对象
 *
 * Author: Freeman
 *
 * Date: 2018/07/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PayRequestBean {
	public String appId;
	public String partnerId;
	public String prepayId;
	public String nonceStr;
	public String timeStamp;
	public String packageValue;
	public String sign;
	public String extraValue;

	public PayRequestBean() {

	}

	public PayRequestBean(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String packageValue, String sign) {
		this(appId, partnerId, prepayId, nonceStr, timeStamp, packageValue, sign, null);
	}

	public PayRequestBean(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp,
	                      String packageValue, String sign, String extraValue) {
		this.appId = appId;
		this.partnerId = partnerId;
		this.prepayId = prepayId;
		this.nonceStr = nonceStr;
		this.timeStamp = timeStamp;
		this.packageValue = packageValue;
		this.sign = sign;
		this.extraValue = extraValue;
	}
}
