package com.sxu.basecomponent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyleImpl;
import com.sxu.basecomponent.uiwidget.ToolbarEx;

/*******************************************************************************
 * Description: 通用Fragment基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/27
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class BaseCommonFragment extends Fragment implements ContainerLayoutStyle {

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

	/**
	 * 返回需要加载的布局ID
	 * @return
	 */
	protected abstract int getLayoutResId();

	/**
	 * 获取布局中的View，设置布局样式
	 */
	protected abstract void getViews();

	/**
	 * 关联Fragment中的View与Data
	 */
	protected abstract void initFragment();

	/**
	 * 预处理，设置toolbar风格
	 */
	protected void pretreatment() {

	}

	protected void setToolbarStyle(int style) {
		this.toolbarStyle = style;
	}

	@Override
	public void initLayout(int toolbarStyle) {
		layoutStyle.initLayout(toolbarStyle);
		toolbar = layoutStyle.getToolbar();
		containerLayout = layoutStyle.getContainerLayout();
	}
}
