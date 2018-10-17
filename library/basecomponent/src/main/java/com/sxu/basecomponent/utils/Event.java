package com.sxu.basecomponent.utils;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/*******************************************************************************
 * Description: 定义EventBus中使用的Event实体类，以Event为后缀
 *
 * Author: Freeman
 *
 * Date: 2018/8/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class Event {

	public static void register(Object object) {
		if (!EventBus.getDefault().isRegistered(object)) {
			EventBus.getDefault().register(object);
		}
	}

	public static void unregister(Object object) {
		if (EventBus.getDefault().isRegistered(object)) {
			EventBus.getDefault().unregister(object);
		}
	}
}
