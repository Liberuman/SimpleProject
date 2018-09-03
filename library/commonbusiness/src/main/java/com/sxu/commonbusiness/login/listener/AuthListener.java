package com.sxu.commonbusiness.login.listener;

/*******************************************************************************
 * Description: 登录授权回调接口
 *
 * Author: Freeman
 *
 * Date: 2018/9/3
 *******************************************************************************/
public interface AuthListener {

	void onAuthSucceed(String openId);

	void onAuthFailed(Exception e);
}
