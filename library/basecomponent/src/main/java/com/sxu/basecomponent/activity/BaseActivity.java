package com.sxu.basecomponent.activity;

import android.os.Bundle;
import android.view.View;

/*******************************************************************************
 * Description: 启动时不进行网络操作的Activity的基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/13
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public abstract class BaseActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViews();
        initActivity();
    }

    @Override
    public void initLayout(int toolbarStyle) {
        if (getLayoutResId() == 0) {
            super.initLayout(toolbarStyle);
            return;
        }

        if (toolbarStyle == TOOL_BAR_STYLE_NONE) {
            setContentView(getLayoutResId());
        } else {
            super.initLayout(toolbarStyle);
            if (toolbarStyle == TOOL_BAR_STYLE_NORMAL) {
                View.inflate(this, getLayoutResId(), containerLayout);
            } else {
                containerLayout.addView(View.inflate(this, getLayoutResId(),null), 0);
            }
        }
    }
}
