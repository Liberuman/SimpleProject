package com.sxu.basecomponent.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************************************************************
 * Description: 通用的ListView/GridView Adapter
 *
 * Author: Freeman
 *
 * Date: 2018/7/26
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class CommonAdapter<T extends Object> extends BaseAdapter {

	private Context mContext;
	private List<T> mData;
	private int mResId;

	public CommonAdapter(Context context, List<T> data, @LayoutRes int resId) {
		this.mContext = context;
		this.mData = data;
		this.mResId = resId;
	}

	public CommonAdapter(Context context, T[] data, @LayoutRes int resId) {
		this.mContext = context;
		this.mData = Arrays.asList(data);
		this.mResId = resId;
	}

	@Override
	public int getCount() {
		return mData != null ? mData.size() : 0;
	}

	@Override
	public T getItem(int i) {
		return mData != null ? mData.get(i) : null;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(mResId, viewGroup, false);
			holder = ViewHolder.getInstance(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		convert(holder , getItem(i), i);

		return view;
	}

	public abstract void convert(final ViewHolder holder, final T paramT, final int position);


	/**
	 * 更新数据，替换原有数据
	 */
	public void updateItems(List<T> items) {
		mData = new ArrayList<>(items);
		notifyDataSetChanged();
	}
}
