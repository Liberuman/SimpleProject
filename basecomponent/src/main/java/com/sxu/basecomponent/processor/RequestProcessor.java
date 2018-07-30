package com.sxu.basecomponent.processor;

import android.view.View;

/*******************************************************************************
 * Description: 需要网络请求的页面处理过程的接口
 *
 * Author: Freeman
 *
 * Date: 2018/7/30
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public interface RequestProcessor {

	// 数据请求成功
	int MSG_LOAD_FINISH = 0x100;
	// 网络请求失败
	int MSG_LOAD_FAILURE  = 0x101;
	// 数据为空
	int MSG_LOAD_EMPTY = 0x102;
	// 没有更多数据
	int MSG_NO_MORE = 0x104;
	// 未登录
	int MSG_LOAD_NO_LOGIN = 0x105;


	/**
	 * 加载加载中布局
	 * @return
	 */
	View loadLoadingLayout();

	/**
	 * 加载数据为空时的布局
	 * @return
	 */
	View loadEmptyLayout();

	/**
	 * 加载网络请求为空时的布局
	 * @return
	 */
	View loadFailureLayout();

	/**
	 * 加载需要未登录时的布局
	 * @return
	 */
	View loadLoginLayout();
}
