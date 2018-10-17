package com.sxu.basecomponent.mvp;

/*******************************************************************************
 * Description: MVP中View的基类
 *
 * Author: Freeman
 *
 * Date: 2018/10/11
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface BaseView {

	/**
	 * 网络请求完成后通知页面加载相应布局
	 * @param msg
	 */
	void requestComplete(int msg);
}
