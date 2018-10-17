package com.sxu.basecomponent.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxu.basecomponent.processor.RequestProcessor;
import com.sxu.basecomponent.processor.RequestProcessorImpl;


/*******************************************************************************
 * Description: 需要网络请求的Activity基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseProgressActivity extends CommonActivity implements RequestProcessor {

    private View loadingLayout;
    private RequestProcessorImpl processor;

    /**
     * 内容页面是否已被加载，用于区分第一次加载和刷新
     */
    private boolean hasLoaded = false;

    @Override
    public void initLayout(int toolbarStyle) {
        processor = new RequestProcessorImpl(this);
        loadingLayout = processor.getLoadingLayout();
        if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
            setContentView(loadingLayout);
        } else {
            super.initLayout(toolbarStyle);
            processor.setContainerLayout(containerLayout);
            containerLayout.addView(loadingLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        processor.setRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });

        processor.setOnShowContentLayoutListener(new RequestProcessorImpl.OnShowContentLayoutListener() {
            @Override
            public void onShowContentLayout() {
                if (!hasLoaded) { // 是否为初次加载，以区分刷新操作
                    hasLoaded = true;
                    View contentLayout = View.inflate(context, getLayoutResId(), null);
                    processor.updateContentView(loadingLayout, contentLayout);
                    getViews();
                }
                initActivity();
            }
        });
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

    public void notifyLoadFinish(int msg) {
        processor.notifyLoadFinish(msg);
    }

    @Override
    public View loadLoadingLayout() {
        return processor.loadLoadingLayout();
    }

    @Override
    public View loadEmptyLayout() {
        return processor.loadEmptyLayout();
    }

    @Override
    public View loadFailureLayout() {
        return processor.loadFailureLayout();
    }

    @Override
    public View loadLoginLayout() {
        return processor.loadLoginLayout();
    }
}
