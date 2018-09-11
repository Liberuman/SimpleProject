package com.sxu.basecomponent.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*******************************************************************************
 * Description: 通用的ViewHolder, ListView, GridView, RecyclerView可共用
 *
 * Author: Freeman
 *
 * Date: 2018/9/11
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ViewHolder {

	private View itemView;
	private SparseArray<View> viewArray = new SparseArray<>();

	private ViewHolder(View itemView) {
		this.itemView = itemView;
	}

	public static ViewHolder getInstance(View itemView) {
		return new ViewHolder(itemView);
	}

	public <T extends View> T getView(@IdRes int resId) {
		T childView = (T) viewArray.get(resId);
		if (childView == null) {
			childView = itemView.findViewById(resId);
			viewArray.put(resId, childView);
		}

		return childView;
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
		return itemView;
	}

	public void setVisible(@IdRes int resId, int value) {
		getView(resId).setVisibility(value);
	}

	public void setVisible(@IdRes int resId, boolean visibility) {
		getView(resId).setVisibility(visibility ? View.VISIBLE : View.GONE);
	}

	public void setLayoutParams(@IdRes int viewId, ViewGroup.LayoutParams layoutParams){
		View view = getView(viewId);
		view.setLayoutParams(layoutParams);
	}

	public void setOnClickListener(@IdRes int resId, View.OnClickListener listener) {
		getView(resId).setOnClickListener(listener);
	}

	public void setOnLongClickListener(@IdRes int resId, View.OnLongClickListener listener) {
		getView(resId).setOnLongClickListener(listener);
	}
}
