package com.sxu.baselibrary.commonutils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;

/*******************************************************************************
 * Description: 获取屏幕信息
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class DisplayUtil {

	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * @return
	 */
	public static int getScreenHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取屏幕物理像素密度
	 * @return
	 */
	public static float getScreenDensity() {
		return Resources.getSystem().getDisplayMetrics().density;
	}

	/**
	 * dp转px
	 * @param dp
	 * @return
	 */
	public static int dpToPx(int dp) {
		return Math.round(Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
	}

	/**
	 * sp转px
	 * @param sp
	 * @return
	 */
	public static int spToPx(int sp) {
		return Math.round(Resources.getSystem().getDisplayMetrics().scaledDensity * sp + 0.5f);
	}

	/**
	 * px转dp
	 * @param px
	 * @return
	 */
	public static int pxToDp(int px) {
		return Math.round(px / Resources.getSystem().getDisplayMetrics().density + 0.5f);
	}

	/**
	 * px转sp
	 * @param px
	 * @return
	 */
	public static int pxToSp(int px) {
		return Math.round(px / Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
	}

	/**
	 * 获取状态栏的高度
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {
		Rect rect = new Rect();
		((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	/**
	 * 获取屏幕参数
	 * @return
	 */
	public static String getScreenParams() {
		return getScreenWidth() + "*" + getScreenHeight();
	}
}
