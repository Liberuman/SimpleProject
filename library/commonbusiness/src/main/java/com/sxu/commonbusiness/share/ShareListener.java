package com.sxu.commonbusiness.share;

/*******************************************************************************
 * Description: 监听分享的结果
 *
 * Author: Freeman
 *
 * Date: 2018/9/3
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface ShareListener {

	/**
	 * 分享成功
	 */
	void onShareSucceed();

	/**
	 * 分享失败
	 * @param e
	 */
	void onShareFailed(Exception e);
}
