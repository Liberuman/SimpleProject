package com.sxu.commonbusiness.share;

import android.content.Intent;

/*******************************************************************************
 * Description: 处理分享的结果
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface ShareHandler {

	/**
	 * 分享结果回调 需在onActivityResult中调用，已回传分享结果
	 * @param requestCode
	 * @param resultCode
	 * @param intent
	 */
	void handleResult(int requestCode, int resultCode, Intent intent);
}
