package com.sxu.commonbusiness.login.listener;

/*******************************************************************************
 * Description: 登录授权回调接口
 *
 * Author: Freeman
 *
 * Date: 2018/9/3
 *******************************************************************************/
public interface AuthListener {

	/**
	 * 授权成功
	 * @param openId
	 */
	void onAuthSucceed(String openId);

	/**
	 * 授权失败
	 * @param e
	 */
	void onAuthFailed(Exception e);
}
