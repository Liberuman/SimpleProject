package com.sxu.basecomponent.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxu.basecomponent.R;


/**
 * Created by Freeman on 17/4/18.
 */

public class NavigationBar extends FrameLayout {

	private View bottomLine;
	private View rootLayout;
	private TextView titleText;
	private TextView leftText;
	private TextView rightText;
	private ImageView returnIcon;
	private ImageView rightIcon;
	private ImageView rightIcon2;
	private ImageView rightIcon3;


	public NavigationBar(Context context) {
		this(context, null);
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_navigation_bar_layout, this);
		bottomLine = findViewById(R.id.nav_bottom_line);
		rootLayout = findViewById(R.id.root_layout);
		titleText = findViewById(R.id.title_text);
		leftText = findViewById(R.id.left_text);
		rightText = findViewById(R.id.right_text);
		returnIcon = findViewById(R.id.return_icon);
		rightIcon = findViewById(R.id.right_icon);
		rightIcon2 = findViewById(R.id.right_icon_2);
		rightIcon3 = findViewById(R.id.right_icon_3);
		returnIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				((Activity)getContext()).onBackPressed();
			}
		});
	}
	public NavigationBar setBackOnClickListener(OnClickListener listener){
		returnIcon.setOnClickListener(listener);
		return this;
	}

	public NavigationBar hideBackIcon() {
		returnIcon.setVisibility(GONE);
		return this;
	}

	public NavigationBar showBackIcon() {
		returnIcon.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar setBackIconResource(int resId) {
		returnIcon.setImageResource(resId);
		return this;
	}

	public NavigationBar hideBottomLine() {
		bottomLine.setVisibility(GONE);
		return this;
	}

	public NavigationBar showBottomLine() {
		bottomLine.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar showBottomLineAndColor(int color) {
		bottomLine.setVisibility(VISIBLE);
		bottomLine.setBackgroundColor(color);
		return this;
	}

	public NavigationBar setBottomLineAlpha(float alpha) {
		bottomLine.setAlpha(alpha);
		return this;
	}

	public NavigationBar setTitle(String title) {
		titleText.setText(title);
		return this;
	}

	public NavigationBar setTitleColor(int titleColor) {
		titleText.setTextColor(titleColor);
		return this;
	}

	public NavigationBar showRightText() {
		rightText.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar setRightTextColor(int color) {
		rightText.setTextColor(color);
		return this;
	}

	public NavigationBar hideRightText() {
		rightText.setVisibility(GONE);
		return this;
	}

	public NavigationBar setRightText(String text) {
		rightText.setText(text);
		return this;
	}

	public TextView getRightText() {
		rightText.setVisibility(VISIBLE);
		return rightText;
	}

	public NavigationBar setRightTextClickListener(OnClickListener listener) {
		rightText.setOnClickListener(listener);
		return this;
	}

	public NavigationBar showRightIcon() {
		rightIcon.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar showRightIcon2() {
		rightIcon2.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar showRightIcon3() {
		rightIcon3.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar hideRightIcon3() {
		rightIcon3.setVisibility(GONE);
		return this;
	}

	public NavigationBar hideRightIcon() {
		rightIcon.setVisibility(GONE);
		return this;
	}

	public NavigationBar hideRightIcon2() {
		rightIcon2.setVisibility(GONE);
		return this;
	}

	public ImageView getRightIcon() {
		return rightIcon;
	}

	public ImageView getRightIcon2() {
		return rightIcon2;
	}

	public ImageView getRightIcon3() {
		return rightIcon3;
	}

	public NavigationBar setRightIconResource(int resId) {
		rightIcon.setImageResource(resId);
		return this;
	}

	public NavigationBar setRightIcon2Resource(int resId) {
		rightIcon2.setImageResource(resId);
		return this;
	}

	public NavigationBar setRightIcon3Resource(int resId) {
		rightIcon3.setImageResource(resId);
		return this;
	}

	public NavigationBar setRightIconClickListener(OnClickListener listener) {
		rightIcon.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon2ClickListener(OnClickListener listener) {
		rightIcon2.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon3ClickListener(OnClickListener listener) {
		rightIcon3.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIconResAndEvent(int resId, OnClickListener listener) {
		rightIcon.setVisibility(VISIBLE);
		rightIcon.setImageResource(resId);
		rightIcon.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon2ResAndEvent(int resId, OnClickListener listener) {
		rightIcon2.setVisibility(VISIBLE);
		rightIcon2.setImageResource(resId);
		rightIcon2.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon3ResAndEvent(int resId, OnClickListener listener) {
		rightIcon3.setVisibility(VISIBLE);
		rightIcon3.setImageResource(resId);
		rightIcon3.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setLeftTextAndEvent(String text, OnClickListener listener) {
		leftText.setVisibility(VISIBLE);
		leftText.setText(text);
		leftText.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightTextAndEvent(String text, OnClickListener listener) {
		rightText.setVisibility(VISIBLE);
		rightText.setText(text);
		rightText.setOnClickListener(listener);
		return this;
	}

	public void setBackgroundAlpha(float alpha) {
		if (alpha < 1) {
			bottomLine.setVisibility(GONE);
			rootLayout.setBackgroundColor(Color.argb((int)(alpha * 255), 252, 252, 252));
		} else {
			bottomLine.setVisibility(VISIBLE);
			rootLayout.setBackgroundColor(getResources().getColor(R.color.b4));
		}
	}

	public void setBackgroundTranslucent(boolean isTranslucent) {
		if (isTranslucent) {
			bottomLine.setVisibility(GONE);
			returnIcon.setImageResource(R.drawable.nav_back_white_icon);
			rootLayout.setBackgroundResource(R.drawable.rectangle_80black_to_0black_bg);
		} else {
			bottomLine.setVisibility(VISIBLE);
			returnIcon.setImageResource(R.drawable.nav_back_grey_icon);
			rootLayout.setBackgroundColor(getResources().getColor(R.color.b4));
		}
	}

	public void setTransparentBackground(boolean isWhite) {
		if (isWhite) {
			returnIcon.setImageResource(R.drawable.nav_back_white_icon);
		} else {
			returnIcon.setImageResource(R.drawable.nav_back_grey_icon);
		}
		rootLayout.setBackgroundColor(Color.TRANSPARENT);
		bottomLine.setVisibility(GONE);
	}

	public void setBackgroundAlpha(float alpha, int red, int green, int blue) {
		if (alpha < 1) {
			bottomLine.setVisibility(GONE);
		} else {
			alpha = 1;
			bottomLine.setVisibility(VISIBLE);
		}
		rootLayout.setBackgroundColor(Color.argb((int)(alpha * 255), red, green, blue));
	}
}
