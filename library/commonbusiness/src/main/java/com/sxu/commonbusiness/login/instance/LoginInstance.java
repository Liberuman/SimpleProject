package com.sxu.commonbusiness.login.instance;

import android.content.Intent;

import com.sxu.commonbusiness.login.LoginHandler;
import com.sxu.commonbusiness.login.listener.LoginListener;

/*******************************************************************************
 * Description: 登录统一接口
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public abstract class LoginInstance extends LoginListener implements LoginHandler {

	public abstract void onLogin();

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {

	}

	@Override
	public void loginSucceed(Object response) {

	}

	@Override
	public void loginFailed(Exception e) {

	}

	@Override
	public void loginCanceled() {

	}
}
