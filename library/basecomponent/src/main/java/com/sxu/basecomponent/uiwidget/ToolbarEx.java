package com.sxu.basecomponent.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxu.basecomponent.R;

/*******************************************************************************
 * Description: 封装ToolBar控件
 *
 * Author: Freeman
 *
 * Date: 2018/7/13
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ToolbarEx extends Toolbar {

	private TextView titleText;
	private TextView leftText;
	private TextView rightText;
	private ImageView rightIcon;
	private ImageView[] rightIcons;

	private int toolbarHeight;
	private int itemPadding;

	public ToolbarEx(Context context) {
		this(context, null);
	}

	public ToolbarEx(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ToolbarEx(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		toolbarHeight = getResources().getDimensionPixelOffset(R.dimen.toolbar_height);
		itemPadding = getResources().getDimensionPixelOffset(R.dimen.toolbar_padding);
		setTitle("");
		setReturnIcon(R.drawable.nav_back_grey_icon);
		setTitleTextAppearance(context, R.style.NavigationTitleAppearance);
		setMinimumHeight(toolbarHeight);
		setBackgroundColor(Color.WHITE);
		ViewCompat.setElevation(this, getResources().getDimension(R.dimen.toolbar_elevation));
	}

	/**
	 * 设置返回键的图片资源
	 * @param resId
	 */
	public void setReturnIcon(@DrawableRes int resId) {
		setNavigationIcon(resId);
		setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity) getContext()).finish();
			}
		});
	}

	/**
	 * 设置返回键的图片资源和点击事件
	 * @param resId
	 * @param listener
	 */
	public void setReturnIconAndEvent(@DrawableRes int resId, OnClickListener listener) {
		setNavigationIcon(resId);
		setNavigationOnClickListener(listener);
	}

	/**
	 * 初始化标题View
	 * @param isDefaultStyle
	 */
	private void initTitleText(boolean isDefaultStyle) {
		if (titleText == null) {
			titleText = new TextView(getContext());
			//titleText.setPadding(titlePadding, 0, titlePadding, 0);
			titleText.setGravity(Gravity.CENTER);
			if (isDefaultStyle) {
				titleText.setTextAppearance(getContext(), R.style.NavigationTitleAppearance);
			}
			Toolbar.LayoutParams params = generateDefaultLayoutParams();
			params.gravity = Gravity.LEFT ;
			addView(titleText, params);
		}
	}

	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	public ToolbarEx setTitle(String title) {
		initTitleText(true);
		titleText.setText(title);
		return this;
	}

	/**
	 * 调用默认的setTitle
	 * @param title
	 * @return
	 */
	public ToolbarEx setDefaultTitle(String title) {
		super.setTitle(title);
		return this;
	}

	public ToolbarEx setTitleGravity(int gravity) {
		initTitleText(true);
		((LayoutParams) titleText.getLayoutParams()).gravity = gravity;
		return this;
	}

	/**
	 * 设置标题样式
	 * @param styleId
	 * @return
	 */
	public ToolbarEx setTitleTextAppearance(@StyleRes int styleId) {
		initTitleText(false);
		titleText.setTextAppearance(getContext(), styleId);
		return this;
	}

	/**
	 * 获取标题View
	 * @return
	 */
	public TextView getTitleText() {
		initTitleText(true);
		return titleText;
	}

	/**
	 * 初始化左边TextView
	 * @param isDefaultStyle
	 */
	private void initLeftText(boolean isDefaultStyle) {
		if (leftText == null) {
			leftText = new TextView(getContext());
			leftText.setPadding(itemPadding, 0, itemPadding, 0);
			leftText.setGravity(Gravity.CENTER);
			if (isDefaultStyle) {
				leftText.setTextAppearance(getContext(), R.style.NavigationLeftTextAppearance);
			}
			Toolbar.LayoutParams params = generateDefaultLayoutParams();
			//params.height = ViewGroup.LayoutParams.MATCH_PARENT;
			params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			addView(leftText, params);
		}
	}

	/**
	 * 设置左边TextView的文案
	 * @param text
	 * @return
	 */
	public ToolbarEx setLeftText(String text) {
		setNavigationIcon(null);
		initLeftText(true);
		leftText.setText(text);
		return this;
	}

	/**
	 * 设置左边TextView的文案和点击事件
	 * @param text
	 * @param listener
	 * @return
	 */
	public ToolbarEx setLeftTextAndEvent(String text, OnClickListener listener) {
		initLeftText(true);
		leftText.setText(text);
		leftText.setOnClickListener(listener);

		return this;
	}

	/**
	 * 设置左边TextView的文字样式
	 * @param styleId
	 * @return
	 */
	public ToolbarEx setLeftTextAppearance(@StyleRes int styleId) {
		initLeftText(false);
		leftText.setTextAppearance(getContext(), styleId);

		return this;
	}

	/**
	 * 获取左边的TextView
	 * @return
	 */
	public TextView getLeftText() {
		return leftText;
	}

	/**
	 * 初始化右边的TextView
	 * @param isDefaultStyle
	 */
	private void initRightText(boolean isDefaultStyle) {
		if (rightText == null) {
			rightText = new TextView(getContext());
			rightText.setPadding(itemPadding, 0, itemPadding, 0);
			rightText.setGravity(Gravity.CENTER);
			if (isDefaultStyle) {
				rightText.setTextAppearance(getContext(), R.style.NavigationRightTextAppearance);
			}
			Toolbar.LayoutParams params = generateDefaultLayoutParams();
			params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
			addView(rightText, params);
		}
	}

	/**
	 * 设置右边TextView的文案
	 * @param text
	 * @return
	 */
	public ToolbarEx setRightText(String text) {
		initRightText(true);
		rightText.setText(text);
		return this;
	}

	/**
	 * 设置右边View的文案和点击事件
	 * @param text
	 * @param listener
	 * @return
	 */
	public ToolbarEx setRightTextAndEvent(String text, OnClickListener listener) {
		initRightText(true);
		rightText.setText(text);
		rightText.setOnClickListener(listener);
		return this;
	}

	/**
	 * 设置右边TextVIew的文字样式
	 * @param styleId
	 * @return
	 */
	public ToolbarEx setRightTextAppearance(@StyleRes int styleId) {
		initRightText(false);
		rightText.setTextAppearance(getContext(), styleId);

		return this;
	}

	/**
	 * 获取右边的TextView
	 * @return
	 */
	public TextView getRightText() {
		return rightText;
	}

	public ToolbarEx setRightIcon(@DrawableRes int resId, OnClickListener listener) {
		return setRightIcon(resId, 0, 0, listener);
	}

	public ToolbarEx setRightIcon(@DrawableRes int resId, int horizontalPadding, OnClickListener listener) {
		return setRightIcon(resId, horizontalPadding, 0, listener);
	}

	/**
	 * 设置右边Icon的资源
	 * @param resId
	 * @param horizontalPadding
	 * @param marginRight 最右边Icon的右边距
	 * @param listeners
	 * @return
	 */
	public ToolbarEx setRightIcon(@DrawableRes int resId, int horizontalPadding, int marginRight, OnClickListener listeners) {
		Toolbar.LayoutParams params = generateDefaultLayoutParams();
		params.rightMargin = marginRight;
		params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		rightIcon = new ImageView(getContext());
		rightIcon.setImageResource(resId);
		rightIcon.setPadding(horizontalPadding, 0, horizontalPadding, 0);
		rightIcon.setOnClickListener(listeners);
		addView(rightIcon, params);

		return this;
	}

	public ImageView getRightIcon() {
		return rightIcon;
	}

	public ToolbarEx setRightIcons(@DrawableRes int[] resIds, OnClickListener[] listeners) {
		return setRightIcons(resIds, 0, 0, listeners);
	}

	public ToolbarEx setRightIcons(@DrawableRes int[] resIds, int horizontalPadding, OnClickListener[] listeners) {
		return setRightIcons(resIds, horizontalPadding, 0, listeners);
	}

	/**
	 * 设置右边多个Icon资源
	 * @param resIds
	 * @param horizontalPadding 每个Icon的padding
	 * @param marginRight 最右边icon的右边距
	 * @param listeners
	 * @return
	 */
	public ToolbarEx setRightIcons(@DrawableRes int[] resIds, int horizontalPadding, int marginRight, OnClickListener[] listeners) {
		if (resIds == null || resIds.length == 0) {
			throw new IllegalArgumentException("resIds can't be null");
		}
		if (listeners == null || listeners.length == 0) {
			throw new IllegalArgumentException("listeners can't be null");
		}
		if (resIds.length != listeners.length) {
			throw new IllegalArgumentException("resIds's length should equals listeners's length");
		}

		Toolbar.LayoutParams params = generateDefaultLayoutParams();
		params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		LinearLayout rightIconLayout = new LinearLayout(getContext());
		rightIconLayout.setPadding(0, 0, marginRight, 0);
		rightIconLayout.setOrientation(LinearLayout.HORIZONTAL);
		rightIcons = new ImageView[resIds.length];
		LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		for (int i = 0; i < resIds.length; i++) {
			rightIcons[i] = new ImageView(getContext());
			rightIcons[i].setPadding(horizontalPadding, 0, horizontalPadding, 0);
			rightIcons[i].setImageResource(resIds[i]);
			rightIcons[i].setOnClickListener(listeners[i]);
			rightIconLayout.addView(rightIcons[i], iconParams);
		}
		addView(rightIconLayout, params);

		return this;
	}

	public ImageView[] getRightIcons() {
		return rightIcons;
	}

	/**
	 * 设置背景的透明度
	 * @param alpha
	 */
	public void setBackgroundAlpha(int alpha) {
		setBackgroundColor(Color.argb(alpha, 255, 255, 255));
	}

	public void setChildViewStyle() {
		for (int i = 0, size = getChildCount(); i < size; i++) {
			View itemView = getChildAt(i);
			if (itemView instanceof ImageView) {
				DrawableCompat.setTint(((ImageView) itemView).getDrawable(), Color.WHITE);
			} else if (itemView instanceof TextView) {
				((TextView) itemView).setTextColor(Color.WHITE);
			}
		}
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, toolbarHeight);
	}

	/**
	 * 判断颜色值是否为暗色调
	 * @param color
	 * @return
	 */
	public boolean isDarkColor(int color) {
		double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
		if (darkness < 0.5) {
			return false;
		} else {
			return true;
		}
	}
}
