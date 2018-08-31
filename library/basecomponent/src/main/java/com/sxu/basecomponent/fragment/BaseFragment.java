package com.sxu.basecomponent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxu.basecomponent.uiwidget.ToolbarEx;

/*******************************************************************************
 * Description: 不需要网络请求的Fragment
 *
 * Author: Freeman
 *
 * Date: 2018/7/27
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseFragment extends CommonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initLayout(toolbarStyle);

        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
        initFragment();
    }

    @Override
    public void initLayout(int toolbarStyle) {
        if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
            contentView = View.inflate(context, getLayoutResId(), null);
        } else {
            super.initLayout(toolbarStyle);
            if (toolbarStyle == TOOL_BAR_STYLE_NORMAL) {
                View.inflate(context, getLayoutResId(), containerLayout);
            } else {
                containerLayout.addView(View.inflate(context, getLayoutResId(),null), 0);
            }
            contentView = containerLayout;
        }
    }
}
