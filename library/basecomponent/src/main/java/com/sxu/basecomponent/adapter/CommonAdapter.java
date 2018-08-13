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
 * Description: 通用的Adapter
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
			holder = new ViewHolder(view, i);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
			holder.position = i;
		}
		convert(holder , getItem(i));

		return view;
	}

	public abstract void convert(final ViewHolder holder, final T paramT);


	/**
	 * 更新数据，替换原有数据
	 */
	public void updateItems(List<T> items) {
		mData = new ArrayList<>(items);
		notifyDataSetChanged();
	}

	public class ViewHolder {

		private int position;
		private View mContentView;
		private SparseArray<View> childViews = new SparseArray<>();

		public ViewHolder(View contentView, int position) {
			this.mContentView = contentView;
			this.position = position;
		}

		public <T extends View> T getView(@IdRes int resId) {
			if (childViews.get(resId) == null) {
				childViews.put(resId, mContentView.findViewById(resId));
			}

			return (T)childViews.get(resId);
		}

		public void setText(@IdRes int resId, String text) {
			((TextView)getView(resId)).setText(text);
		}

		public void setTextColor(@IdRes int resId, int textColor) {
			((TextView)getView(resId)).setTextColor(textColor);
		}

		public void setImageResource(@IdRes int resId, @DrawableRes int drawableResId) {
			((ImageView)getView(resId)).setImageResource(drawableResId);
		}

		public View getContentView() {
			return mContentView;
		}

		public int getPosition() {
			return position;
		}

		public void setVisible(@IdRes int resId, int value) {
			getView(resId).setVisibility(value);
		}

		public void setVisible(@IdRes int resId, boolean visibility) {
			getView(resId).setVisibility(visibility ? View.VISIBLE : View.GONE);
		}

		public void setLayoutParams(int viewId, ViewGroup.LayoutParams layoutParams){
			View view = getView(viewId);
			view.setLayoutParams(layoutParams);
		}

		public void setOnClickListener(@IdRes int resId, View.OnClickListener listener) {
			getView(resId).setOnClickListener(listener);
		}
	}
}
