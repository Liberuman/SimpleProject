package com.sxu.baselibrary.uiwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*******************************************************************************
 * Description: 顶部可下拉回弹的ListView, 锤子手机默认可回弹
 *
 * Author: Freeman
 *
 * Date: 2018/3/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ResilienceListView extends ListView {

	private int mOverScrollY;

	public ResilienceListView(Context context) {
		super(context);
	}

	public ResilienceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ResilienceListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOverScrollY(int overScrollY) {
		this.mOverScrollY = overScrollY;
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
	                               int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mOverScrollY, isTouchEvent);
	}
}
