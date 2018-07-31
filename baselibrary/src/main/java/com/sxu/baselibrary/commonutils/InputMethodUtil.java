package com.sxu.baselibrary.commonutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

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
	 * 键盘的最小高度
	 */
	private static final int MINI_KEYBOARD_HEIGHT = 180;


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

	/**
	 * 键盘弹出时调整EditText的位置
	 * @param view 需要调整位置的View， 一般为根布局
	 * @param focusView 当前获取焦点的EditText
	 */
	public static void adjustViewPosition(final View view, final View focusView) {
		adjustViewPosition(view, focusView, 0);
	}

	public static void adjustViewPosition(final View view, final View focusView, final int offsetY) {
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (((Activity)view.getContext()).getCurrentFocus() != focusView) {
					return;
				}
				Rect outRect = new Rect();
				view.getWindowVisibleDisplayFrame(outRect);
				int screenHeight = view.getRootView().getHeight();
				int softKeyboardHeight = screenHeight - outRect.bottom;
				if (softKeyboardHeight > MINI_KEYBOARD_HEIGHT) {
					final int scrollY = offsetY != 0 ? offsetY : softKeyboardHeight;
					view.post(new Runnable() {
						@Override
						public void run() {
							if (view instanceof ScrollView) {
								((ScrollView) view).smoothScrollBy(0, scrollY);
							} else {
								view.scrollBy(0, scrollY);
							}
						}
					});
				} else {
					view.scrollTo(0, 0);
				}
			}
		});
	}
}
