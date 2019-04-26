package com.sxu.baselibrary.uiwidget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

/*******************************************************************************
 * FileName: PageAdapterEx
 *
 * Description: 增加getTabView，使我们可以自定义TabIndicator布局
 *
 * Author: Freeman
 *
 * Version: v1.0
 *
 * Date: 16/8/4
 *******************************************************************************/
public abstract class BasePagerAdapterExt extends FragmentPagerAdapter {

	public BasePagerAdapterExt(FragmentManager fm) {
		super(fm);
	}

	/**
	 * 自定义每一项的View
	 * @param position
	 * @return
	 */
	public abstract View getTabView(int position);
}