package com.sxu.basecomponent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.baselibrary.commonutils.LogUtil;


/*******************************************************************************
 * FileName: BaseWithNavFragment
 *
 * Description: 
 *
 * Author: Freeman
 *
 * Version: v1.0
 *
 * Date: 2018/1/25
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class BaseWithNavFragment extends BaseFragment {

	protected NavigationBar navigationBar;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		LogUtil.i("=========onAttach");
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.i("=========onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout containerLayout = new LinearLayout(getContext());
		containerLayout.setOrientation(LinearLayout.VERTICAL);
		//containerLayout.setBackgroundColor(Color.WHITE);
		navigationBar = new NavigationBar(getContext());
		containerLayout.addView(navigationBar);
		if (getLayoutResId() != 0) {
			contentView = inflater.inflate(getLayoutResId(), containerLayout);
			contentView.setClickable(true);
			getViews();
			initFragment();
		}

		return contentView;
	}
}
