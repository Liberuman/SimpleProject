package com.sxu.baselibrary.commonutils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/*******************************************************************************
 * Description: 自定义Toast
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ToastUtil {

	// 默认样式
	public final static int TOAST_STYLE_DEFAULT = 0;
	// 成功样式
	public final static int TOAST_STYLE_CORRECT = 1;
	// 警告样式
	public final static int TOAST_STYLE_WARNING = 2;
	// 错误样式
	public final static int TOAST_STYLE_ERROR = 3;

	private static Toast toast;
	private static int defaultColor = Color.parseColor("#424242");
	private static int correctColor = Color.parseColor("#4caf50");
	private static int warningColor = Color.parseColor("#ffc107");
	private static int errorColor = Color.parseColor("#f44336");

	// 记录上次的显示样式，避免每次设置样式
	private static int lastStyle = TOAST_STYLE_DEFAULT;

	public static void show(String info) {
		show(BaseContentProvider.context, info, TOAST_STYLE_DEFAULT);
	}

	public static void show(String info, int toastStyle) {
		show(BaseContentProvider.context, info, toastStyle);
	}

	/**
	 * 注意：如果将activity theme中的fitsSystemWindows设置为true, toast会出现文件和背景错位的情况，
	 *  且自定义样式时设置setPadding无效
	 * @param context
	 * @param info
	 */
	private static void show(Context context, String info, int toastStyle) {
		if (context == null) {
			return;
		}

		if (toast == null) {
			toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
			TextView textView = new TextView(context);
			textView.setText(info);
			textView.setTextSize(16);
			textView.setTextColor(Color.WHITE);
			textView.setGravity(Gravity.CENTER);
			int verticalPadding = DisplayUtil.dpToPx(8);
			int horizontalPadding = DisplayUtil.dpToPx(12);
			textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
			ViewBgUtil.setShapeBg(textView, defaultColor, DisplayUtil.dpToPx(6));
			toast.setView(textView);
			toast.setGravity(Gravity.CENTER, 0, DisplayUtil.dpToPx(70));
		}

		if (lastStyle != toastStyle) {
			int backgroundColor = defaultColor;
			switch (toastStyle) {
				case TOAST_STYLE_CORRECT:
					backgroundColor = correctColor;
					break;
				case TOAST_STYLE_WARNING:
					backgroundColor = warningColor;
					break;
				case TOAST_STYLE_ERROR:
					backgroundColor = errorColor;
					break;
				default:
					break;
			}
			ViewBgUtil.setShapeBg(toast.getView(), backgroundColor, DisplayUtil.dpToPx(6));
		}
		((TextView)toast.getView()).setText(info);
		toast.show();
		lastStyle = toastStyle;
	}
}
