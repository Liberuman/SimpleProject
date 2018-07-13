package com.sxu.basecomponent.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
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

	private TextView leftText;
	private TextView rightText;
	private ImageView rightIcon;
	private ImageView[] rightIcons;

	public ToolbarEx(Context context) {
		this(context, null);
	}

	public ToolbarEx(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public ToolbarEx(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	public void setReturnIcon(@DrawableRes int resId) {
		setNavigationIcon(resId);
		setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();
			}
		});
	}

	public void setReturnIconAndEvent(@DrawableRes int resId, OnClickListener listener) {
		setNavigationIcon(resId);
		setNavigationOnClickListener(listener);
	}

	private void initLeftText(boolean isDefaultStyle) {
		if (leftText == null) {
			leftText = new TextView(getContext());
			if (isDefaultStyle) {
				leftText.setTextAppearance(getContext(), R.style.NavigationLeftTextAppearance);
			}
			Toolbar.LayoutParams params = generateDefaultLayoutParams();
			params.height = ViewGroup.LayoutParams.MATCH_PARENT;
			params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			addView(leftText, params);
		}
	}

	public ToolbarEx setLeftText(String text) {
		initLeftText(true);
		leftText.setText(text);
		return this;
	}

	public ToolbarEx setLeftTextAndEvent(String text, OnClickListener listener) {
		initLeftText(true);
		leftText.setText(text);
		leftText.setOnClickListener(listener);

		return this;
	}

	public ToolbarEx setLeftTextAppearance(@StyleRes int styleId) {
		initLeftText(false);
		leftText.setTextAppearance(getContext(), styleId);

		return this;
	}

	public TextView getLeftText() {
		return leftText;
	}

	private void initRightText(boolean isDefaultStyle) {
		if (rightText == null) {
			rightText = new TextView(getContext());
			if (isDefaultStyle) {
				rightText.setTextAppearance(getContext(), R.style.NavigationRightTextAppearance);
			}
			Toolbar.LayoutParams params = generateDefaultLayoutParams();
			params.height = ViewGroup.LayoutParams.MATCH_PARENT;
			params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			addView(rightText, params);
		}
	}

	public ToolbarEx setRightText(String text) {
		initRightText(true);
		rightText.setText(text);
		return this;
	}

	public ToolbarEx setRightTextAndEvent(String text, OnClickListener listener) {
		initRightText(true);
		rightText.setText(text);
		rightText.setOnClickListener(listener);
		return this;
	}

	public ToolbarEx setRightTextAppearance(@StyleRes int styleId) {
		initRightText(false);
		rightText.setTextAppearance(getContext(), styleId);

		return this;
	}

	public TextView getRightText() {
		return rightText;
	}

	public ToolbarEx setRightIcon(@DrawableRes int resId, int paddingRight, OnClickListener listeners) {
		Toolbar.LayoutParams params = generateDefaultLayoutParams();
		params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		rightIcon = new ImageView(getContext());
		rightIcon.setImageResource(resId);
		rightIcon.setPadding(0,0, paddingRight, 0);
		rightIcon.setOnClickListener(listeners);
		addView(rightIcon, params);

		return this;
	}

	public ImageView getRightIcon() {
		return rightIcon;
	}

	public ToolbarEx setRightIcons(@DrawableRes int[] resIds, int paddingRight, OnClickListener[] listeners) {
		if (resIds == null || resIds.length == 0) {
			return this;
		}
		if (listeners == null || listeners.length == 0) {
			return this;
		}
		if (resIds.length != listeners.length) {
			return this;
		}

		Toolbar.LayoutParams params = generateDefaultLayoutParams();
		params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		LinearLayout rightIconLayout = new LinearLayout(getContext());
		rightIconLayout.setPadding(0, 0, paddingRight, 0);
		rightIconLayout.setOrientation(LinearLayout.HORIZONTAL);
		rightIcons = new ImageView[resIds.length];
		for (int i = 0; i < resIds.length; i++) {
			rightIcons[i] = new ImageView(getContext());
			rightIcons[i].setImageResource(resIds[0]);
			rightIcons[i].setOnClickListener(listeners[i]);
			rightIconLayout.addView(rightIcons[i]);
		}
		addView(rightIconLayout, params);

		return this;
	}

	public ImageView[] getRightIcons() {
		return rightIcons;
	}

	public void setBackgroundAlpha(int alpha) {
		Drawable drawable = super.getBackground();
		if (drawable != null) {
			drawable.setAlpha(alpha);
		}
	}
}
