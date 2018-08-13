package com.sxu.basecomponent.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxu.basecomponent.processor.RequestProcessor;
import com.sxu.basecomponent.processor.RequestProcessorImpl;
import com.sxu.basecomponent.uiwidget.ToolbarEx;


/*******************************************************************************
 * FileName: BaseProgressFragment
 *
 * Description: 需要网络请求的的Fragment
 *
 * Author: Freeman
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseProgressFragment extends CommonFragment implements RequestProcessor {

    private View loadingLayout;
    private RequestProcessorImpl processor;

    /**
     * 内容页面是否已被加载，用于区分第一次加载和刷新
     */
    private boolean hasLoaded = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        requestData();
        initLayout(toolbarStyle);
        return contentView;
    }

    @Override
    public void initLayout(int toolbarStyle) {
        processor = new RequestProcessorImpl(context);
        loadingLayout = processor.getLoadingLayout();
        super.initLayout(toolbarStyle);
        containerLayout.addView(loadingLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        contentView = containerLayout;

        processor.setContainerLayout(containerLayout);
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
                initFragment();
            }
        });
    }

    /**
     * 网络请求的过程
     */
    protected abstract void requestData();

    protected void notifyLoadFinish(int msg) {
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
