package com.sxu.basecomponent.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.ToastUtil;


/*******************************************************************************
 * FileName: BaseProgressFragment
 *
 * Description: 需要网络请求的的Fragment
 *
 * Author: Freeman
 *
 * Version: v1.0
 *
 * Date: 2018/1/25
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseProgressFragment extends Fragment {

    private TextView tipsText;
    private Button reloadButton;
    protected ViewStub resultStub;
    private ViewStub bookEmptyStub;
    private View emptyLayout;
    protected View loadingLayout;
    protected FrameLayout containerLayout;
    protected View mContentView;
    private View emptyView;
    private ViewGroup emptyContainerLayout;

    private String emptyTips;
    private boolean isInflated;
    private boolean canRefresh;
    protected int mScrWidth;

    // 数据为空
    protected static final int MSG_LOAD_EMPTY = -1;
    // 网络请求失败
    protected static final int MSG_LOAD_FAILURE = -7;
    // 数据请求成功
    protected static final int MSG_LOAD_FINISH = -2;
    // 有数据时的页面刷新（与初次加载相比，可避免布局重复加载，View重复获取的过程）
    protected static final int MSG_REFRESH_FINISH = -3;
    // 加载更多
    protected static final int MSG_LOAD_MORE = -4;
    protected static final int MSG_LOAD_NO_LOGIN = -8;
    protected static final int MSG_LOADING = -5;
    protected static final int MSG_LOADING_DONE = -6;
    protected static final int MSG_NO_MORE = -9;
    protected static final int MSG_SHOW_EMPTY_LAYOUT = -10;

    //当前请求状态
    protected int MSG_CODE = 0;

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        containerLayout = (FrameLayout) inflater.inflate(R.layout.fragment_progress_layout, null);
        loadingLayout = containerLayout.findViewById(R.id.base_loading_layout);
        resultStub = containerLayout.findViewById(R.id.base_result_stub);
        if (getActivity() != null) {
            requestData();
        }

        return containerLayout;
    }

    protected abstract int getLayoutResId();

    protected abstract void getViews();

    protected abstract void initFragment();

    protected abstract void requestData();

    protected void notifyLoadFinish(int msg) {
        mHandler.sendEmptyMessage(msg);
    }

    protected void setEmptyText(String text) {
        emptyTips = text;
    }

    public void setRefreshable(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    private void handleMsg(Message msg) {
        if (getActivity() != null) {
            loadingLayout.setVisibility(View.GONE);
            MSG_CODE = msg.what;
            switch (msg.what) {
                case MSG_LOAD_FINISH:
                    if (getLayoutResId() != 0) {
                        if (getActivity() != null && !isInflated) {
                            isInflated = true;
                            mContentView = LayoutInflater.from(getActivity()).inflate(getLayoutResId(), containerLayout);
                            getViews();
                            initFragment();
                        } else {
                            if (containerLayout.getChildCount() > 2) {
                                containerLayout.getChildAt(2).setVisibility(View.VISIBLE);
                            }
                            initFragment();
                        }
                    } else {
                        throw new IllegalArgumentException("you don't inflate layout");
                    }
                    break;
                case MSG_REFRESH_FINISH:
                    initFragment();
                    break;
                case MSG_NO_MORE:
                    ToastUtil.show(getActivity(), "没有更多数据啦");
                    break;
                default:
                    showEmptyLayout(msg.what);
                    break;
            }
        }
    }

    protected void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    private void showEmptyLayout(int msgCode) {
        if (emptyLayout == null) {
            emptyLayout = resultStub.inflate();
            tipsText = emptyLayout.findViewById(R.id.base_tips_text);
            reloadButton = emptyLayout.findViewById(R.id.base_reload_button);
            emptyContainerLayout = emptyLayout.findViewById(R.id.empty_container_layout);

        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
        if (containerLayout.getChildCount() > 2) {
            containerLayout.getChildAt(2).setVisibility(View.GONE);
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
        } else if (msgCode == MSG_LOAD_FAILURE) {
            tipsText.setText("网络异常，轻击屏幕重新加载");
            reloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emptyLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);
                    requestData();
                }
            });
        } else if (msgCode == MSG_LOAD_NO_LOGIN) {
            tipsText.setText("你还未登陆，请先登录");
            reloadButton.setText("登录指南猫");
        } else if (msgCode == MSG_SHOW_EMPTY_LAYOUT) {
            if (emptyView != null) {
                emptyContainerLayout.addView(emptyView);
            }
        } else {
            /**
             * Nothing
             */
        }
    }
}
