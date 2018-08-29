package com.sxu.basecomponent.utils;

import android.content.Context;
import android.text.TextUtils;

import com.meituan.android.walle.WalleChannelReader;

/*******************************************************************************
 * Description: 获取渠道名
 *
 * Author: Freeman
 *
 * Date: 2018/8/29
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ChannelUtil {

	public static String getChannel(Context context) {
		String chanel = WalleChannelReader.getChannel(context.getApplicationContext());
		if (TextUtils.isEmpty(chanel)) {
			chanel = "official";
		}

		return chanel;
	}
}
