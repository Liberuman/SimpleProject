package com.sxu.basecomponent.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*******************************************************************************
 * Description: 不需要网络请求的Fragment
 *
 * Author: Freeman
 *
 * Date: 2018/7/27
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseFragment extends BaseCommonFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
