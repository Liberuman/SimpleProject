package com.sxu.simpleproject;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/8/1
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class MyApplication extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		ARouter.openDebug();
		ARouter.openLog();
		ARouter.init(this);
	}
}
