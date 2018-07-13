package com.sxu.basecomponent.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.baselibrary.commonutils.DisplayUtil;


/*******************************************************************************
 * FileName: BaseProgressWithNavFragment
 *
 * Description: 自带NavigateBar, 需要网络请求的的Fragment
 *
 * Author: Freeman
 *
 * Version: v1.0
 *
 * Date: 2018/1/25
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class BaseProgressWithNavFragment extends BaseProgressFragment {

	protected NavigationBar navigationBar;

	private boolean isFloating;
	private String title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		requestData();
		FrameLayout contentLayout = new FrameLayout(getContext());
		contentLayout.setBackgroundColor(Color.WHITE);
		navigationBar = new NavigationBar(getContext());
		navigationBar.setMinimumHeight(DisplayUtil.dpToPx(48));
		navigationBar.setTitle(title);
		if (getLayoutResId() != 0) {
			mContentView = inflater.inflate(getLayoutResId(), null);
		}
		View loadingView = inflater.inflate(R.layout.fragment_progress_layout, null);
		resultStub = loadingView.findViewById(R.id.base_result_stub);
		//loadingLayout = loadingView.findViewById(R.id.base_loading_layout);
		containerLayout = loadingView.findViewById(R.id.base_container_layout);
		if (!isFloating) {
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			params.topMargin = DisplayUtil.dpToPx(48);
			loadingView.setLayoutParams(params);
		} else {
			navigationBar.setBackgroundAlpha(0);
		}
		contentLayout.addView(loadingLayout);
		contentLayout.addView(navigationBar);
		contentLayout.setClickable(true);

		return contentLayout;
	}

	protected void setNavigateStyle(boolean isFloating) {
		this.isFloating = isFloating;
	}

	protected void setNavigationTitle(String title) {
		this.title = title;
	}
}
