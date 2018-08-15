package com.sxu.basecomponent.activity;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.basecomponent.uiwidget.ToolbarEx;
import com.sxu.baselibrary.commonutils.InputMethodUtil;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

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
