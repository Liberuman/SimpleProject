package com.sxu.baselibrary.commonutils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;

/*******************************************************************************
 * Description: 获取资源
 *
 * Author: Freeman
 *
 * Date: 2018/8/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ResourceUtil {

	/**
	 * 获取Drawable资源
	 * @param resId
	 * @return
	 */
	public Drawable getDrawable(@DrawableRes int resId) {
		return ContextCompat.getDrawable(BaseContentProvider.context, resId);
	}

	/**
	 * 获取Color资源
	 * @param colorId
	 * @return
	 */
	public int getColor(@ColorRes int colorId) {
		return ContextCompat.getColor(BaseContentProvider.context, colorId);
	}

	/**
	 * 获取字符串
	 * @param resId
	 * @return
	 */
	public String getString(@StringRes int resId) {
		return BaseContentProvider.context.getResources().getString(resId);
	}

	/**
	 * 获取整型值
	 * @param resId
	 * @return
	 */
	public int getInteger(@IntegerRes int resId) {
		return BaseContentProvider.context.getResources().getInteger(resId);
	}
}
