package com.sxu.baselibrary.commonutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/*******************************************************************************
 * Description: 键盘的相关操作
 *
 * Author: Freeman
 *
 * Date: 2018/3/23
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class InputMethodUtil {

	/**
	 * 切换菜单，显示时调用则隐藏，隐藏时调用则显示
	 * @param context
	 */
	public static void toggleKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, 0);
	}

	/**
	 * 隐藏键盘
	 * @param context
	 */
	public static void hideKeyboard(Activity context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = context.getCurrentFocus();
		if (view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 设置某个view的focusable和touchFocusable为true, 然后调用此方法
	 * @param context
	 */
	public static void showKeyboard(Activity context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = context.getCurrentFocus();
		if (view != null) {
			imm.showSoftInput(view, 0);
		}
	}

	/**
	 * 显示键盘
	 * @param context
	 * @param view
	 */
	public static void showKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}
}
