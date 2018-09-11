package com.sxu.basecomponent.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*******************************************************************************
 * Description: RecyclerView的通用适配器
 *
 * Author: Freeman
 *
 * Date: 2018/9/11
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class CommonRecyclerAdapter<T extends Object>
		extends RecyclerView.Adapter<CommonRecyclerAdapter.RecyclerViewHolder> {

	private List<T> data;
	private int layoutId;

	public CommonRecyclerAdapter(List<T> data, @LayoutRes int layoutId) {
		this.data = data;
		this.layoutId = layoutId;
	}

	@NonNull
	@Override
	public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new RecyclerViewHolder(View.inflate(parent.getContext(), layoutId, null));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
		convert(holder, data.get(position), position);
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void addItem(T item) {
		data.add(item);
		notifyItemInserted(data.size() - 1);
	}

	public void addItem(T item, int position) {
		data.add(position, item);
		notifyItemInserted(position);
	}

	public void deleteItem(int position) {
		data.remove(position);
		notifyItemRemoved(position);
	}

	public void updateItem(T item, int position) {
		data.set(position, item);
		notifyItemChanged(position);
	}

	public abstract void convert(RecyclerViewHolder viewHolder, T itemData, int position);

	public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

		private ViewHolder viewHolder;

		public RecyclerViewHolder(View itemView) {
			super(itemView);
			viewHolder = ViewHolder.getInstance(itemView);
		}

		public <T extends View> T getView(@IdRes int resId) {
			return viewHolder.getView(resId);
		}

		public void setText(@IdRes int resId, String text) {
			viewHolder.setText(resId, text);
		}

		public void setTextColor(@IdRes int resId, int textColor) {
			viewHolder.setTextColor(resId, textColor);
		}

		public void setImageResource(@IdRes int resId, @DrawableRes int drawableResId) {
			viewHolder.setImageResource(resId, drawableResId);
		}

		public View getContentView() {
			return viewHolder.getContentView();
		}

		public void setVisible(@IdRes int resId, int value) {
			viewHolder.setVisible(resId, value);
		}

		public void setVisible(@IdRes int resId, boolean visibility) {
			viewHolder.setVisible(resId, visibility);
		}

		public void setLayoutParams(@IdRes int viewId, ViewGroup.LayoutParams layoutParams){
			viewHolder.setLayoutParams(viewId, layoutParams);
		}

		public void setOnClickListener(@IdRes int resId, View.OnClickListener listener) {
			viewHolder.setOnClickListener(resId, listener);
		}

		public void setOnLongClickListener(@IdRes int resId, View.OnLongClickListener listener) {
			viewHolder.setOnLongClickListener(resId, listener);
		}
	}
}
