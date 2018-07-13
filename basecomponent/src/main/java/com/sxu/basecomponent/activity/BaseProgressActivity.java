package com.sxu.basecomponent.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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


/**
 * Created by juhg on 16/2/17.
 */
public abstract class BaseProgressActivity extends AppCompatActivity {

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
    protected static final int MSG_LOAD_FINISH = -1;
    // 数据为空
    protected static final int MSG_LOAD_EMPTY = -2;
    // 网络请求失败
    protected static final int MSG_LOAD_FAILURE = -3;
    // 有数据时的页面刷新（与初次加载相比，可避免布局重复加载，View重复获取的过程）
    protected static final int MSG_REFRESH_FINISH = -4;
    // 没有更多数据
    protected static final int MSG_NO_MORE = -5;
    // 未登录
    protected static final int MSG_LOAD_NO_LOGIN = -6;
    // 显示自定义的结果布局
    protected static final int MSG_SHOW_RESULT_LAYOUT = -10;

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activity切换动画
        mContext = this;
        overridePendingTransition(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300);
        setContentView(getLayoutInflater().inflate(R.layout.activity_progress_layout, null));
        loadingLayout = findViewById(R.id.base_loading_layout);
        navigationBar = findViewById(R.id.base_navigate_layout);
        resultStub = findViewById(R.id.base_result_stub);
        containerLayout = findViewById(R.id.base_container_layout);

        requestData();

        mScrWidth = DisplayUtil.getScreenWidth();
        if (isFloating) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) containerLayout.getLayoutParams();
            params.topMargin = 0;
            containerLayout.setLayoutParams(params);
        }
    }

    protected abstract int getLayoutResId();

    protected abstract void getViews();

    protected abstract void initActivity();

    protected abstract void requestData();

    protected void notifyLoadFinish(int msg) {
        mHandler.sendEmptyMessage(msg);
    }

    protected void setEmptyText(String text) {
        emptyTips = text;
    }

    protected void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    // 设置Navigation为悬浮状态
    protected void setNavigationBarFloating() {
        this.isFloating = true;
    }

    private void handleMsg(Message msg) {
        loadingLayout.setVisibility(View.GONE);
        switch (msg.what) {
            case MSG_LOAD_FINISH:
                if (tipsText != null) {
                    tipsText.setText("");
                }
                int layoutResId = getLayoutResId();
                if (layoutResId != 0 && !isInflated) {
                    isInflated = true;
                    LayoutInflater.from(this).inflate(layoutResId, containerLayout);
                    getViews();
                    initActivity();
                } else { // 刷新页面时如果发送MSG_LOAD_FINISH消息，则只执行加载数据的过程
                    initActivity();
                }
                break;
            case MSG_REFRESH_FINISH:
                if (tipsText != null) {
                    tipsText.setText("");
                }
                initActivity();
                break;
            case MSG_NO_MORE:
                ToastUtil.show(this, "没有更多数据啦");
                break;
            default:
                showEmptyLayout(msg.what);
                break;
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
        } else if (msgCode == MSG_LOAD_NO_LOGIN) {
            reloadButton.setVisibility(View.VISIBLE);
            tipsText.setText("你还未登陆，请先登录");
            reloadButton.setText("登录指南猫");
        } else if (msgCode == MSG_SHOW_RESULT_LAYOUT) {
            if (resultLayout != null) {
                emptyContainerLayout.addView(resultLayout);
            }
        } else {
            /**
             * Nothing
             */
        }
    }

    protected void setResultLayout(View resultLayout) {
        this.resultLayout = resultLayout;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_translate_left_in_300, R.anim.anim_translate_right_out_300);
    }

    public String getAnalyticsName() {
        return getClass().getName();
    }
}
