package com.sxu.commonbusiness.share.instance;


import com.sxu.commonbusiness.share.ShareHandler;
import com.sxu.commonbusiness.share.ShareListener;


/*******************************************************************************
 * Description: 分享的接口
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class ShareInstance extends ShareListener implements ShareHandler {

	/**
	 * 分享多媒体，包括文字，图片，视频，URL等
	 * @param title
	 * @param desc
	 * @param iconUrl
	 * @param url
	 */
	public abstract void onShare(String title, String desc, String iconUrl, String url);
}
