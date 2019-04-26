package com.sxu.basecomponent.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyleImpl;
import com.sxu.basecomponent.uiwidget.ToolbarEx;
import com.sxu.basecomponent.utils.Event;
import com.sxu.baselibrary.commonutils.CollectionUtil;
import com.sxu.baselibrary.commonutils.InputMethodUtil;

import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/*******************************************************************************
 * Description: Activity最基础的类, 为BaseActivity和BaseProgressActivity提供公共逻辑
 *
 * Author: Freeman
 *
 * Date: 2018/7/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class BaseCommonActivity extends SwipeBackActivity implements ContainerLayoutStyle {

	protected ToolbarEx toolbar;
	protected ViewGroup containerLayout;

	protected Activity context;
	protected FragmentManager fragmentManager;
	protected ContainerLayoutStyleImpl layoutStyle;

	protected int toolbarStyle = TOOL_BAR_STYLE_NORMAL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		fragmentManager = getSupportFragmentManager();
		// 设置沉浸式状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		// 设置Activity的切换动画
		overridePendingTransition(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300);
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
			recreateActivity(savedInstanceState);
		}

		layoutStyle = new ContainerLayoutStyleImpl(this);
		pretreatment();
		initLayout(toolbarStyle);
		if (containerLayout != null) {
			setContentView(containerLayout);
		}

		// 注册EventBus
		if (needEventBus()) {
			Event.register(this);
		}
	}

	@Override
	public void initLayout(int toolbarStyle) {
		layoutStyle.initLayout(toolbarStyle);
		toolbar = layoutStyle.getToolbar();
		containerLayout = layoutStyle.getContainerLayout();
	}

	/**
	 * 预处理，加载布局之前添加相关逻辑，如设置window样式，重置切换动画，设置toolbar风格
	 */
	protected void pretreatment() {

	}

	/**
	 * 设置toolbar的样式
	 * @param style
	 */
	protected void setToolbarStyle(int style) {
		this.toolbarStyle = style;
	}

	/**
	 * 获取Activity加载的布局ID
	 * @return
	 */
	protected abstract int getLayoutResId();

	/**
	 * 获取布局中的所有控件
	 */
	protected abstract void getViews();

	/**
	 * Activity的逻辑处理过程
	 */
	protected abstract void initActivity();

	protected void recreateActivity(Bundle savedInstanceState) {

	}

	protected boolean needEventBus() {
		return false;
	}

	/**
	 * 当Activity重建时恢复Fragment
	 * @param savedInstanceState
	 */
	public void restoreFragment(Bundle savedInstanceState) {
		FragmentManager fm = getSupportFragmentManager();
		List<Fragment> fragmentList = fm != null ? fm.getFragments() : null;
		if (CollectionUtil.isEmpty(fragmentList)) {
			return;
		}

		for (Fragment fragment : fragmentList) {
			fragment.onAttach((Context)context);
			restoreChildFragment(fragment);
		}
	}

	/**
	 * 恢复嵌套的Fragment
	 */
	public void restoreChildFragment(Fragment currentFragment) {
		FragmentManager fm = currentFragment.getFragmentManager();
		List<Fragment> fragmentList = fm != null ? fm.getFragments() : null;
		if (CollectionUtil.isEmpty(fragmentList)) {
			return;
		}

		for (Fragment fragment : fragmentList) {
			fragment.onAttach((Context)context);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (needEventBus()) {
			Event.unregister(this);
		}
	}

	@Override
	public void finish() {
		super.finish();
		InputMethodUtil.hideKeyboard(this);
		overridePendingTransition(R.anim.anim_translate_left_in_300, R.anim.anim_translate_right_out_300);
	}
}
