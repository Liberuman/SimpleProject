package com.sxu.baselibrary.commonutils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/*******************************************************************************
 * Description: 键盘弹出时调整EditText的位置
 *
 * Author: Freeman
 *
 * Date: 2018/3/5
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class AdjustEditTextPositionUtil {

	/**
	 * 键盘的最小高度
	 */
	private static final int MINI_KEYBOARD_HEIGHT = 180;

	public static void adjustPosition(final View view, final int scrollY) {
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Rect outRect = new Rect();
				view.getWindowVisibleDisplayFrame(outRect);
				int screenHeight = view.getRootView().getHeight();
				int softKeyboardHeight = screenHeight - outRect.bottom;
				if (softKeyboardHeight > MINI_KEYBOARD_HEIGHT) { // 键盘弹出时
					view.scrollTo(0, scrollY);
				} else {
					view.scrollTo(0, 0);
				}
			}
		});
	}
}
