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

    private TextView tipsText;
    protected Button reloadButton;
    private ViewStub resultStub;
    private View emptyLayout;
    private View loadingLayout;
    private View baseResultLayout;
    private ViewGroup emptyContainerLayout;
    private View resultLayout;

    public NavigationBar navigationBar;
    private FrameLayout containerLayout;

    private String emptyTips;
    private int backgroundColor;
    private String navigationTitle;
    private boolean isInflated;
    private boolean isFloating;
    private boolean isTransparent;
    private boolean foregroundIsWhite;
    private boolean canRefresh;
    private boolean hideProgressBar;
    private boolean hideBottomLine;
    protected int mScrWidth;
    protected Context mContext;

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
    protected View initLayout() {
        if (loadingLayout == null) {

        }

        return loadingLayout;
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

    protected void notifyLoadFinish(int msg) {
        handler.sendEmptyMessage(msg);
    }

    protected void setEmptyText(String text) {
        emptyTips = text;
    }

    protected void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }


    private void handleMsg(Message msg) {
        loadingLayout.setVisibility(View.GONE);
        switch (msg.what) {
            case MSG_LOAD_FINISH:
                loadContentLayout();
                break;
            case MSG_LOAD_FAILURE:
                loadFailureLayout();
                break;
            case MSG_LOAD_EMPTY:
                loadEmptyLayout();
                break;
            case MSG_NO_MORE:
                ToastUtil.show(this, "没有更多数据啦");
                break;
            case MSG_LOAD_NO_LOGIN:
                loadLoginLayout();
                break;
            default:
                throw new IllegalArgumentException("The Message not supported!");
        }
    }

    private void showEmptyLayout(int msgCode) {
        if (emptyLayout == null) {
            emptyLayout = resultStub.inflate();
            tipsText = emptyLayout.findViewById(R.id.base_tips_text);
            reloadButton = emptyLayout.findViewById(R.id.base_reload_button);
            baseResultLayout = emptyLayout.findViewById(R.id.base_result_layout);
            emptyContainerLayout = emptyLayout.findViewById(R.id.empty_container_layout);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
        if (msgCode == MSG_LOAD_EMPTY) {
            reloadButton.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(emptyTips)) {
                tipsText.setText(emptyTips);
            } else {
                tipsText.setText("暂无数据");
            }
            if (canRefresh) {
                tipsText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emptyLayout.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.VISIBLE);
                        requestData();
                    }
                });
            }
            loadEmptyLayout();
        } else if (msgCode == MSG_LOAD_FAILURE) {
            tipsText.setText("网络异常，轻击屏幕重新加载");
            reloadButton.setVisibility(View.GONE);
            baseResultLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emptyLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);
                    requestData();
                }
            });
            loadFailureLayout();
        } else if (msgCode == MSG_LOAD_NO_LOGIN) {
            reloadButton.setVisibility(View.VISIBLE);
            tipsText.setText("你还未登陆，请先登录");
            reloadButton.setText("请先登录");
            loadLoginLayout();
        } else {
            /**
             * Nothing
             */
        }
    }

    protected void loadContentLayout() {

    }

    protected void loadEmptyLayout() {

    }

    protected void loadFailureLayout() {

    }

    protected void loadLoginLayout() {

    }
}
