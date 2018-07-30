package com.sxu.basecomponent.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyleImpl;
import com.sxu.basecomponent.uiwidget.ToolbarEx;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

/*******************************************************************************
 * Description: 通用Fragment基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/27
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class CommonFragment extends Fragment implements ContainerLayoutStyle {

	protected ToolbarEx toolbar;
	protected View contentView;
	protected ViewGroup containerLayout;

	protected int toolbarStyle;

	protected Context context;
	private ContainerLayoutStyleImpl layoutStyle;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.context = context;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		pretreatment();
		layoutStyle = new ContainerLayoutStyleImpl(context);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected abstract int getLayoutResId();

	protected abstract void getViews();

	protected abstract void initFragment();

	/**
	 * 预处理，设置toolbar风格
	 */
	protected void pretreatment() {

	}

	protected void setToolbarStyle(int style) {
		this.toolbarStyle = style;
	}

	public void initLayout(int toolbarStyle) {
		layoutStyle.initLayout(toolbarStyle);
		toolbar = layoutStyle.getToolbar();
		containerLayout = layoutStyle.getContainerLayout();
	}
}
