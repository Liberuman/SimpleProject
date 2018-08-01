package com.sxu.simpleproject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

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

public class InitService extends IntentService {

	public InitService() {
		super("InitService");
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {

		// 初始化第三方组件
	}
}
