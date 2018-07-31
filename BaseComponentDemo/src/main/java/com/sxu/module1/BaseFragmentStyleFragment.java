package com.sxu.module1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.sxu.basecomponent.fragment.BaseFragment;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/30
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class BaseFragmentStyleFragment extends BaseFragment {

	private int toolbarStyle;

	public static Fragment getInstance(int toolbarStyle) {
		Fragment fragment = new BaseFragmentStyleFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("toolbarStyle", toolbarStyle);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_base_activity_style_layout;
	}

	@Override
	protected void pretreatment() {
		Bundle bundle = getArguments();
		if (bundle == null) {
			return;
		}
		toolbarStyle = bundle.getInt("toolbarStyle");
		setToolbarStyle(toolbarStyle);
	}

	@Override
	protected void getViews() {
		TextView descText = (TextView) contentView.findViewById(R.id.desc_text);
		if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
			descText.setText("没有Toolbar的布局");
		} else if (toolbarStyle == TOOL_BAR_STYLE_NORMAL) {
			descText.setText("有Toolbar的线性布局");
		} else if (toolbarStyle == TOOL_BAR_STYLE_TRANSPARENT) {
			descText.setText("透明Toolbar的帧布局");
		} else if (toolbarStyle == TOOL_BAR_STYLE_TRANSLUCENT) {
			descText.setText("半透明Toolbar的帧布局");
		}
	}

	@Override
	protected void initFragment() {

	}
}
