package com.sxu.basecomponent.utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/8/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class BaseApplication extends MultiDexApplication {

	// 是否已登录
	public static boolean isLogin;
	public static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
	}
}
