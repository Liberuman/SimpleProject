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


import com.sxu.basecomponent.R;
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
    protected View initLayout() {
        View contentView;
        switch (toolbarStyle) {
            case TOOL_BAR_STYLE_NONE:
                contentView = View.inflate(this, getLayoutResId(), null);
                break;
            case TOOL_BAR_STYLE_NORMAL:
                contentView = createNormalToolbarLayout();
                break;
            case TOOL_BAR_STYLE_TRANSPARENT:
                contentView = createTransparentToolbarLayout(true);
                break;
            case TOOL_BAR_STYLE_TRANSLUCENT:
                contentView = createTransparentToolbarLayout(false);
                break;
            default:
                throw new IllegalArgumentException("The toolbarStyle value is not supported");
        }

        return contentView;
    }

    private View createNormalToolbarLayout() {
        LinearLayout containerLayout = new LinearLayout(this);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        toolbar = new ToolbarEx(this);
        containerLayout.addView(toolbar);
        View.inflate(this, getLayoutResId(), containerLayout);
        return containerLayout;
    }

    private View createTransparentToolbarLayout(boolean isTransparent) {
        FrameLayout containerLayout = new FrameLayout(this);
        View.inflate(this, getLayoutResId(), containerLayout);
        toolbar = new ToolbarEx(this);
        if (isTransparent) {
            toolbar.setBackgroundAlpha(0);
        } else {
            toolbar.setBackgroundAlpha(128);
        }
        containerLayout.addView(toolbar);

        return containerLayout;
    }
}
