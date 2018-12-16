package com.sxu.simpleproject;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.utils.BaseApplication;
import com.sxu.basecomponent.utils.ChannelUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.imageloader.FrescoInstance;
import com.sxu.imageloader.ImageLoaderManager;

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
		ImageLoaderManager.getInstance().init(this, new FrescoInstance());
		LogUtil.i("channel=====" + ChannelUtil.getChannel(this));
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.fontScale != 1) {
			getResources();
		}
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 放大系统字体将导致布局错乱，所以一般可将其屏蔽
	 * @return
	 */
	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		if (res.getConfiguration().fontScale != 1) {
			Configuration newConfig = new Configuration();
			newConfig.setToDefaults();
			res.updateConfiguration(newConfig, res.getDisplayMetrics());
		}

		return res;
	}
}
