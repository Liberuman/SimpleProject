package com.sxu.basecomponent.processor;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.ToastUtil;

/*******************************************************************************
 * Description: 网络请求过程中页面的处理过程
 *
 * Author: Freeman
 *
 * Date: 2018/7/30
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class RequestProcessorImpl implements RequestProcessor {

	private ViewGroup containerLayout;
	private View loadingLayout;
	private View exceptionLayout;

	private int exceptionLayoutType;
	private View.OnClickListener refreshListener;
	private OnShowContentLayoutListener showLayoutListener;
	private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT);

	private Context context;

	/**
	 * 异常布局类型
	 */
	private final int LAYOUT_TYPE_FAILURE = 1; // 失败时的布局
	private final int LAYOUT_TYPE_EMPTY = 2;   // 数据为空时的布局
	private final int LAYOUT_TYPE_LOGIN = 3;   // 需要登录时的布局

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			handleMsg(msg);
		}
	};

	public RequestProcessorImpl(Context context) {
		this.context = context;
		loadingLayout = loadLoadingLayout();
	}

	public View getLoadingLayout() {
		return loadingLayout;
	}

	public void setContainerLayout(ViewGroup containerLayout) {
		this.containerLayout = containerLayout;
	}

	public void notifyLoadFinish(int msg) {
		handler.sendEmptyMessage(msg);
	}

	public void handleMsg(Message msg) {
		switch (msg.what) {
			case MSG_LOAD_FINISH:
				if (showLayoutListener != null) {
					showLayoutListener.onShowContentLayout();
				}
				break;
			case MSG_LOAD_FAILURE:
				showExceptionLayout(LAYOUT_TYPE_FAILURE);
				break;
			case MSG_LOAD_EMPTY:
				showExceptionLayout(LAYOUT_TYPE_EMPTY);
				break;
			case MSG_NO_MORE:
				ToastUtil.show("没有更多数据啦");
				break;
			case MSG_LOAD_NO_LOGIN:
				showExceptionLayout(LAYOUT_TYPE_LOGIN);
				break;
			default:
				throw new IllegalArgumentException("The Message not supported!");
		}
	}

	/**
	 * 网络请求完成后更换布局
	 * @param oldView
	 * @param newView
	 */
	public void updateContentView(View oldView, View newView) {
		if (containerLayout != null) {
			int index = containerLayout.indexOfChild(oldView);
			containerLayout.removeView(oldView);
			containerLayout.addView(newView, index, params);
		} else {
			((Activity)context).setContentView(newView);
		}
	}

	/**
	 * 网络请求失败时的刷新操作 调用requestData
	 * @param listener
	 */
	public void setRefreshListener(View.OnClickListener listener) {
		this.refreshListener = listener;
	}

	public void setOnShowContentLayoutListener(OnShowContentLayoutListener showLayoutListener) {
		this.showLayoutListener = showLayoutListener;
	}

	public void showExceptionLayout(int layoutType) {
		if (exceptionLayout == null || exceptionLayoutType != layoutType) {
			if (layoutType == LAYOUT_TYPE_EMPTY) {
				exceptionLayout = loadEmptyLayout();
				exceptionLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (refreshListener != null) {
							refreshListener.onClick(v);
						}
						updateContentView(exceptionLayout, loadingLayout);
					}
				});
			} else if (layoutType == LAYOUT_TYPE_FAILURE) {
				exceptionLayout = loadFailureLayout();
				exceptionLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (refreshListener != null) {
							refreshListener.onClick(v);
						}
						updateContentView(exceptionLayout, loadingLayout);
					}
				});
			} else if (layoutType == LAYOUT_TYPE_LOGIN) {
				exceptionLayout = loadLoginLayout();
				exceptionLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ToastUtil.show("跳转到登录页面");
						updateContentView(exceptionLayout, loadingLayout);
					}
				});
			}
		}

		exceptionLayoutType = layoutType;
		updateContentView(loadingLayout, exceptionLayout);
	}

	@Override
	public View loadLoadingLayout() {
		return View.inflate(context, R.layout.common_progress_layout, null);
	}

	@Override
	public View loadEmptyLayout() {
		return View.inflate(context, R.layout.common_empty_layout, null);
	}

	@Override
	public View loadFailureLayout() {
		return View.inflate(context, R.layout.common_failure_layout, null);
	}

	@Override
	public View loadLoginLayout() {
		return View.inflate(context, R.layout.common_login_layout, null);
	}

	public interface OnShowContentLayoutListener {
		void onShowContentLayout();
	}
}
