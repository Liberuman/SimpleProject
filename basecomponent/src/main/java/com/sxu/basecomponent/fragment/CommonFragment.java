package com.sxu.basecomponent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*******************************************************************************
 * Description: 通用Fragment基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/27
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class CommonFragment extends Fragment {

	protected Context context;
	protected View contentView;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.context = context;
	}

	protected abstract int getLayoutResId();

	protected abstract void getViews();

	protected abstract void initFragment();
}
