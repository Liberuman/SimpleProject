package com.sxu.basecomponent.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.baselibrary.commonutils.DisplayUtil;
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
    private View failureLayout;
    private View emptyLayout;
    private View loginLayout;
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };

    @Override
    protected void initLayout() {
        loadLoadingLayout();
        if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
            setContentView(loadingLayout);
        } else {
            super.initLayout();
            containerLayout.addView(loadingLayout);
        }
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

    private void handleMsg(Message msg) {
        switch (msg.what) {
            case MSG_LOAD_FINISH:
                loadContentLayout();
                break;
            case MSG_LOAD_FAILURE:
                if (failureLayout == null) {
                    failureLayout = loadEmptyLayout();
                }
                setContentView(failureLayout);
                break;
            case MSG_LOAD_EMPTY:
                if (emptyLayout == null) {
                    emptyLayout = loadEmptyLayout();
                }
                setContentView(emptyLayout);
                break;
            case MSG_NO_MORE:
                ToastUtil.show(this, "没有更多数据啦");
                break;
            case MSG_LOAD_NO_LOGIN:
                if (loadingLayout == null) {
                    loadingLayout = loadEmptyLayout();
                }
                setContentView(loadingLayout);
                break;
            default:
                throw new IllegalArgumentException("The Message not supported!");
        }
    }

    protected View loadLoadingLayout() {
        return View.inflate(this, R.layout.common_progress_layout, null);
    }

    private void loadContentLayout() {
        if (!hasLoaded) {
            hasLoaded = true;
            setContentView(getLayoutResId());
            getViews();
            initActivity();
        } else {
            updateCallback();
        }
    }

    protected View loadEmptyLayout() {
        return View.inflate(this, R.layout.common_empty_layout, null);
    }

    protected View loadFailureLayout() {
        return View.inflate(this, R.layout.common_failure_layout, null);
    }

    protected View loadLoginLayout() {
        return View.inflate(this, R.layout.common_login_layout, null);
    }

    //todo 需要将布局添加到containerLayout
}
