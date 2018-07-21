package com.sxu.basecomponent.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.ToastUtil;


/*******************************************************************************
 * Description: 需要网络请求的Activity基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseProgressActivity extends CommonActivity {

    private View loadingLayout;
    private View exceptionLayout;
    private LinearLayout.LayoutParams params;
    private int exceptionLayoutType = 0;
    /**
     * 内容页面是否已被加载，用于区分第一次加载和刷新
     */
    private boolean hasLoaded = false;

    // 数据请求成功
    protected static final int MSG_LOAD_FINISH = 0x100;
    // 网络请求失败
    protected static final int MSG_LOAD_FAILURE  = 0x101;
    // 数据为空
    protected static final int MSG_LOAD_EMPTY = 0x102;
    // 没有更多数据
    protected static final int MSG_NO_MORE = 0x104;
    // 未登录
    protected static final int MSG_LOAD_NO_LOGIN = 0x105;

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

    @Override
    protected void initLayout() {
        loadingLayout = loadLoadingLayout();
        if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
            setContentView(loadingLayout);
        } else {
            super.initLayout();
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            containerLayout.addView(loadingLayout, params);
        }
    }

    /**
     * 网络请求完成后更换布局
     * @param oldView
     * @param newView
     */
    private void updateContentView(View oldView, View newView) {
        int index = containerLayout.indexOfChild(oldView);
        containerLayout.removeView(oldView);
        containerLayout.addView(newView, index, params);
    }

    @Override
    protected void pretreatment() {
        super.pretreatment();
        requestData();
    }

    /**
     * 网络请求的过程
     */
    protected abstract void requestData();

    /**
     * 刷新页面后的逻辑处理
     */
    protected void updateCallback() {

    }

    protected void notifyLoadFinish(int msg) {
        handler.sendEmptyMessage(msg);
    }

    /**
     * 网络请求结果的处理过程
     * @param msg
     */
    private void handleMsg(Message msg) {
        switch (msg.what) {
            case MSG_LOAD_FINISH:
                loadContentLayout();
                break;
            case MSG_LOAD_FAILURE:
                showExceptionLayout(LAYOUT_TYPE_FAILURE);
                break;
            case MSG_LOAD_EMPTY:
                showExceptionLayout(LAYOUT_TYPE_EMPTY);
                break;
            case MSG_NO_MORE:
                ToastUtil.show(this, "没有更多数据啦");
                break;
            case MSG_LOAD_NO_LOGIN:
                showExceptionLayout(LAYOUT_TYPE_LOGIN);
                break;
            default:
                throw new IllegalArgumentException("The Message not supported!");
        }
    }

    /**
     * 网络请求出现异常时加载相应的布局
     * @param layoutType
     */
    private void showExceptionLayout(int layoutType) {
        if (exceptionLayout == null || exceptionLayoutType != layoutType) {
            if (layoutType == LAYOUT_TYPE_EMPTY) {
                exceptionLayout = loadEmptyLayout();
            } else if (layoutType == LAYOUT_TYPE_FAILURE) {
                exceptionLayout = loadFailureLayout();
            } else if (layoutType == LAYOUT_TYPE_LOGIN) {
                exceptionLayout = loadLoginLayout();
            }

            final View tempView = exceptionLayout;
            exceptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestData();
                    updateContentView(tempView, loadingLayout);
                }
            });
        }

        exceptionLayoutType = layoutType;
        updateContentView(loadingLayout, exceptionLayout);
    }

    /**
     * 网络请求成功后的处理逻辑
     */
    private void loadContentLayout() {
        if (!hasLoaded) { // 是否为初次加载，以区分刷新操作
            hasLoaded = true;
            View contentLayout = View.inflate(this, getLayoutResId(), null);
            updateContentView(loadingLayout, contentLayout);
            getViews();
            initActivity();
        } else {
            updateCallback();
        }
    }

    /**
     * 加载加载中的布局
     * @return
     */
    protected View loadLoadingLayout() {
        return View.inflate(this, R.layout.common_progress_layout, null);
    }

    /**
     * 加载数据为空时的布局
     * @return
     */
    protected View loadEmptyLayout() {
        return View.inflate(this, R.layout.common_empty_layout, null);
    }

    /**
     * 加载网络请求失败时的布局
     * @return
     */
    protected View loadFailureLayout() {
        return View.inflate(this, R.layout.common_failure_layout, null);
    }

    /**
     * 加载需要登录时的布局
     * @return
     */
    protected View loadLoginLayout() {
        return View.inflate(this, R.layout.common_login_layout, null);
    }
}
