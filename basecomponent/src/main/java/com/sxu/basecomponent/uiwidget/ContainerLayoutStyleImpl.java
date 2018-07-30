package com.sxu.basecomponent.uiwidget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

/*******************************************************************************
 * Description: 默认实现的Activity/Fragment的基本样式
 *
 * Author: Freeman
 *
 * Date: 2018/7/30
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ContainerLayoutStyleImpl implements ContainerLayoutStyle {

	private ToolbarEx toolbar;
	private ViewGroup containerLayout;

	private Context context;

	public ContainerLayoutStyleImpl(Context context) {
		this.context = context;
	}

	public ToolbarEx getToolbar() {
		return toolbar;
	}

	@Override
	public void initLayout(int toolbarStyle) {
		switch (toolbarStyle) {
			case TOOL_BAR_STYLE_NONE:
				containerLayout = new FrameLayout(context);
				break;
			case TOOL_BAR_STYLE_NORMAL:
				containerLayout = createNormalToolbarLayout();
				break;
			case TOOL_BAR_STYLE_TRANSPARENT:
				containerLayout = createTransparentToolbarLayout(true);
				break;
			case TOOL_BAR_STYLE_TRANSLUCENT:
				containerLayout = createTransparentToolbarLayout(false);
				break;
		}
	}

	public ViewGroup getContainerLayout() {
		return containerLayout;
	}

	private ViewGroup createNormalToolbarLayout() {
		LinearLayout containerLayout = new LinearLayout(context);
		containerLayout.setOrientation(LinearLayout.VERTICAL);
		toolbar = new ToolbarEx(context);
		containerLayout.addView(toolbar);
		return containerLayout;
	}

	private ViewGroup createTransparentToolbarLayout(boolean isTransparent) {
		FrameLayout containerLayout = new FrameLayout(context);
		toolbar = new ToolbarEx(context);
		if (isTransparent) {
			toolbar.setBackgroundColor(Color.TRANSPARENT);
		} else {
			ViewBgUtil.setShapeBg(toolbar, GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
					ContextCompat.getColor(context, R.color.black_50), Color.TRANSPARENT}, 0);
		}
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				context.getResources().getDimensionPixelOffset(R.dimen.toolbar_height));
		containerLayout.addView(toolbar, params);

		return containerLayout;
	}
}
