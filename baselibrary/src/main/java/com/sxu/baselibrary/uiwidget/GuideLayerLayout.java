package com.sxu.baselibrary.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/*******************************************************************************
 * Description: 添加引导层或显示小红点
 *
 * Author: Freeman
 *
 * Date: 2018/3/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class GuideLayerLayout extends FrameLayout {

	private FrameLayout rootLayout;

	public GuideLayerLayout(Context context) {
		super(context);
	}

	public GuideLayerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GuideLayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * 全屏显示的引导页面
	 * @param resId
	 */
	public void setParams(@DrawableRes int resId) {
		setParams(resId, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
	}

	/**
	 * 自定义引导图片的属性
	 * @param resId
	 * @param params
	 */
	public void setParams(@DrawableRes int resId, LayoutParams params) {
		setBackgroundColor(Color.parseColor("#CC000000"));
		ImageView guideLayerImage = new ImageView(getContext());
		guideLayerImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		guideLayerImage.setImageResource(resId);
		guideLayerImage.setLayoutParams(params);
		addView(guideLayerImage);


		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rootLayout.removeView(GuideLayerLayout.this);
			}
		});
	}

	public void addRedPoint(@DrawableRes int resId, LayoutParams params) {
		ImageView guideLayerImage = new ImageView(getContext());
		guideLayerImage.setImageResource(resId);
		guideLayerImage.setLayoutParams(params);
		addView(guideLayerImage);
	}

	public void removeRedPoint() {
		if (rootLayout.getChildCount() > 1) {
			rootLayout.removeView(this);
		}
	}

	public void showRedPoint() {
		if (rootLayout.getChildCount() > 1) {
			rootLayout.getChildAt(1).setVisibility(VISIBLE);
		}
	}

	public void hideRedPoint() {
		if (rootLayout.getChildCount() > 1) {
			rootLayout.getChildAt(1).setVisibility(GONE);
		}
	}

	public void show() {
		rootLayout = (FrameLayout) ((Activity)getContext()).getWindow().findViewById(android.R.id.content);
		rootLayout.addView(GuideLayerLayout.this, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
	}

	public void show(Handler handler, long millsSeconds) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (getContext() != null) {
					rootLayout = (FrameLayout) ((Activity) getContext()).getWindow().findViewById(android.R.id.content);
					rootLayout.addView(GuideLayerLayout.this, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT));
				}
			}
		}, millsSeconds);
	}

	public void setOnCloseListener(final OnClickListener listener) {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rootLayout.removeView(GuideLayerLayout.this);
				listener.onClick(v);
			}
		});
	}
}
