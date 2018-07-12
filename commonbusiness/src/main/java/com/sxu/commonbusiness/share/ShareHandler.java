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

	void handleResult(int requestCode, int resultCode, Intent intent);
}
