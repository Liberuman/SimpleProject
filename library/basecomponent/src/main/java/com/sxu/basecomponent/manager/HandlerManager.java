package com.sxu.basecomponent.manager;

import android.os.Handler;

/*******************************************************************************
 * Description: Handler管理器
 *
 * Author: Freeman
 *
 * Date: 2019/3/8
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class HandlerManager {

	private Handler handler = new Handler();

	private HandlerManager() {

	}

	public static HandlerManager getInstance() {
		return Singleton.INSTANCE;
	}

	public void executeTask(Runnable runnable) {
		handler.post(runnable);
	}

	public void executeTask(Runnable runnable, long delay) {
		handler.postDelayed(runnable, delay);
	}

	public void executeTaskAtTime(Runnable runnable, long time) {
		handler.postAtTime(runnable, time);
	}

	public void clearTask(Runnable runnable) {
		handler.removeCallbacks(runnable);
	}

	public void clear() {
		handler.removeCallbacksAndMessages(null);
	}

	public Handler getHandler() {
		return handler;
	}

	public Handler getNewHandler() {
		return new Handler();
	}

	private final static class Singleton {
		public final static HandlerManager INSTANCE = new HandlerManager();
	}
}
