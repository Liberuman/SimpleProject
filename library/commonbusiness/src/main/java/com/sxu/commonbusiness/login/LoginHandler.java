package com.sxu.commonbusiness.login;

import android.content.Intent;

/*******************************************************************************
 * Description: 处理分享的结果, 需在onActivityResult中调用
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface LoginHandler {

	/**
	 * 登录结果回调
	 * @param requestCode
	 * @param resultCode
	 * @param intent
	 */
	void handleResult(int requestCode, int resultCode, Intent intent);
}
