package com.sxu.basecomponent.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;


import com.sxu.basecomponent.R;
import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.baselibrary.commonutils.InputMethodUtil;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 启动时不进行网络操作的Activity的基类
 *
 * Created by juhg on 16/2/17.
 */
public abstract class BaseActivity extends SwipeBackActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected Activity mContext;
    protected NavigationBar navigationBar;
    protected FragmentManager _fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        _fragmentManager = getSupportFragmentManager();
        // 设置Activity的切换动画
        overridePendingTransition(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300);
        mContext = this;
        if (savedInstanceState != null) {
            recreateActivity(savedInstanceState);
        }
        ViewGroup mContentView = (ViewGroup) View.inflate(this, R.layout.activity_layout, null);
        navigationBar = mContentView.findViewById(R.id.base_navigate_layout);
        if (getLayoutResId() != 0) {
            setContentView(View.inflate(this, getLayoutResId(), mContentView));
            getViews();
        }
        initActivity();
    }

    /**
     * 获取Activity加载的布局ID
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 获取布局中的所有控件
     */
    protected abstract void getViews();

    /**
     * Activity的逻辑处理过程
     */
    protected abstract void initActivity();

    protected void recreateActivity(Bundle savedInstanceState) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 重写原因：
     *    FragmentActivity中的onSaveInstanceState默认会保存其包含的所有Fragment的状态，然而，当用户按下Home键后，
     * 系统可能由于内存不足等原因销毁此Activity，当再次返回到此Activity后，会重建Activity，而Fragment却还是销毁前的
     * 状态，当Fragment和Activity进行通信时，getActivity()为空，导致程序Crash.
     *
     * 解决方案：
     *    通过重写onSaveInstanceState，使程序退到后台后不保存Fragment的状态.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        super.finish();
        InputMethodUtil.hideKeyboard(this);
        overridePendingTransition(R.anim.anim_translate_left_in_300, R.anim.anim_translate_right_out_300);
    }
}
