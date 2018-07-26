package com.sxu.baselibrary.commonutils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.sxu.baselibrary.R;

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

	private static Toast toast;
	private final static int SHOW_DURATION = 1200;

	public static void show(String info) {
		show(BaseContentProvider.context, info);
	}

	/**
	 * 注意：如果将activity theme中的fitsSystemWindows设置为true, toast会出现文件和背景错位的情况，
	 *  且自定义样式时设置setPadding无效
	 * @param context
	 * @param info
	 */
	public static void show(Context context, String info) {
//		if (context == null) {
//			return;
//		}
//		if (toast == null) {
//			toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
//			TextView textView = new TextView(context);
//			textView.setText(info);
//			textView.setTextSize(16);
//			textView.setTextColor(Color.RED);
//			textView.setGravity(Gravity.CENTER);
//			int verticalPadding = DisplayUtil.dpToPx(8);
//			int horizontalPadding = DisplayUtil.dpToPx(10);
//			textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
//			ViewBgUtil.setShapeBg(textView, GradientDrawable.RECTANGLE,
//					Color.parseColor("#333333"), DisplayUtil.dpToPx(6));
//			toast.setView(textView);
//			toast.setGravity(Gravity.CENTER, 0, DisplayUtil.dpToPx(70));
//			textView.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					toast.cancel();
//				}
//			}, SHOW_DURATION);
//		}
//
//		((TextView)toast.getView()).setText(info);
//		toast.show();

		toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		toast.show();
	}
}
