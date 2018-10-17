package com.sxu.basecomponent.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.mvp.BaseView;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyleImpl;
import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.basecomponent.uiwidget.ToolbarEx;
import com.sxu.basecomponent.utils.Event;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.InputMethodUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

import org.greenrobot.eventbus.EventBus;

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

public abstract class CommonActivity extends SwipeBackActivity implements ContainerLayoutStyle {

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
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//		}

		// 设置Activity的切换动画
		overridePendingTransition(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300);
		if (savedInstanceState != null) {
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
