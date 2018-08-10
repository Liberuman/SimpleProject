package com.sxu.baselibrary.commonutils;

import android.graphics.Bitmap;
import android.view.View;

/*******************************************************************************
 * Description: 获取View的相关参数
 *
 * Author: Freeman
 *
 * Date: 2018/8/9
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ViewUtil {

	/**
	 * 获取View在屏幕上的上边距
	 * @param view
	 * @return
	 */
	public static int getTop(View view) {
		int[] position = new int[2];
		view.getLocationOnScreen(position);
		return position[1];
	}

	/**
	 * 获取View在屏幕上的左边距
	 * @param view
	 * @return
	 */
	public static int getLeft(View view) {
		int[] position = new int[2];
		view.getLocationOnScreen(position);
		return position[0];
	}

	/**
	 * 获取View在屏幕上的右边距
	 * @param view
	 * @return
	 */
	public static int getRight(View view) {
		int[] position = new int[2];
		view.getLocationOnScreen(position);
		return view.getWidth() + position[0];
	}

	/**
	 * 获取View在屏幕上的下边距
	 * @param view
	 * @return
	 */
	public static int getBottom(View view) {
		int[] position = new int[2];
		view.getLocationOnScreen(position);
		return view.getHeight() + position[1];
	}

	/**
	 * 获取View的截图
	 * @param view
	 * @return
	 */
	public static Bitmap getCapture(View view) {
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	/**
	 * 点击点是否在指定View内
	 * @param view
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean pointInView(View view, int x, int y) {
		int[] position = new int[2];
		view.getLocationOnScreen(position);
		int right = view.getWidth() + position[0];
		int bottom = view.getHeight() + position[1];
		return x >= position[0] && x <= right && y >= position[1] && y < bottom;
	}
}
