package com.sxu.simpleproject;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.utils.BaseApplication;
import com.sxu.baselibrary.commonutils.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/8/1
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class MyApplication extends BaseApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		ARouter.openDebug();
		ARouter.openLog();
		ARouter.init(this);
	}
}
