package com.sxu.basecomponent.uiwidget;

import android.view.ViewGroup;

/*******************************************************************************
 * Description: Activity/Fragment的页面的基本样式
 *
 * Author: Freeman
 *
 * Date: 2018/7/30
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public interface ContainerLayoutStyle {

	/**
	 * 没有Toolbar
	 */
	int TOOL_BAR_STYLE_NONE = 0;
	/**
	 * 白色背景的Toolbar
	 */
	int TOOL_BAR_STYLE_NORMAL = 1;
	/**
	 * 透明的Toolbar
	 */
	int TOOL_BAR_STYLE_TRANSPARENT = 2;
	/**
	 * 半透明的Toolbar
	 */
	int TOOL_BAR_STYLE_TRANSLUCENT = 3;

	/**
	 * 根据Toolbar的样式设置相应的布局
	 * @param toolbarStyle
	 */
	void initLayout(int toolbarStyle);
}
