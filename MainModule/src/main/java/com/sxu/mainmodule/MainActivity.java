package com.sxu.mainmodule;

import android.Manifest;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.utils.BaseApplication;
import com.sxu.baselibrary.commonutils.LaunchUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.uiwidget.TabLayout;
import com.sxu.commonbusiness.login.aspect.LoginAspect;
import com.sxu.permission.CheckPermission;

import org.aspectj.lang.ProceedingJoinPoint;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/31
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
@Route(path = "/main/home")
public class MainActivity extends BaseActivity {

	private TabLayout tabLayout;
	private int[] textColor = new int[] {
			Color.parseColor("#2979FF"), Color.parseColor("#bfbfbf"),
	};
	private String[] itemText = new String[] {
			"Item1", "Item2", "Item3", "Item4"
	};
	private int[] selectedIcon = new int[] {
			R.drawable.home, R.drawable.home, R.drawable.home, R.drawable.home
	};
	private int[] unselectedIcon = new int[] {
			R.drawable.home_unselected_icon, R.drawable.home_unselected_icon,
			R.drawable.home_unselected_icon, R.drawable.home_unselected_icon
	};

	private long currentTime = 0;

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(TOOL_BAR_STYLE_NONE);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_main;
	}

	int count = 0;
	@Override
	protected void getViews() {
		Button publicButton = (Button) findViewById(R.id.public_button);
		Button baseButton = (Button) findViewById(R.id.base_button);
		Button commonButton = (Button) findViewById(R.id.common_button);
		tabLayout = (TabLayout) findViewById(R.id.tab_layout);


		Keyframe keyframe = Keyframe.ofFloat(0, 0);
		Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 180);
		Keyframe keyframe3 = Keyframe.ofFloat(1.0f, 270);
		PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("rotate", keyframe, keyframe2, keyframe3);
		ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(publicButton, valuesHolder);
		animator.start();

		publicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/base/home").navigation();
				//BaseWebViewActivity.enter(context, null, "http://m.baidu.com");
				//login();
				//setUserIcon();

			}
		});
		baseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/component/home").navigation();
			}
		});
		commonButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/business/Home").navigation();
			}
		});
	}

	@Override
	protected void initActivity() {
		ARouter.init(getApplication());
		tabLayout.setCurrentItem(1);
		tabLayout.setItemData(textColor, itemText, unselectedIcon, selectedIcon);
		tabLayout.setOnItemStateListener(new TabLayout.OnItemStateListener() {
			@Override
			public void onItemSelected(int position) {

			}

			@Override
			public void onItemUnSelected() {

			}
		});

		// 设置全局的登录逻辑
		LoginAspect.setOnLoginListener(new LoginAspect.OnLoginListener() {
			@Override
			public void onLogin(ProceedingJoinPoint joinPoint) {
				if (BaseApplication.isLogin) {
					try {
						joinPoint.proceed();
					} catch (Throwable throwable) {
						throwable.printStackTrace(System.err);
					}
				} else {
					LogUtil.i("===========跳转登录");
				}
			}
		});
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA,}, permissionDesc = "没有权限无法使用相机", settingDesc = "快去设置中开启权限")
	private void setUserIcon() {

	}

	/**
	 * 2s内连续两次点击返回键退出
	 */
	private void realExit() {
		if (System.currentTimeMillis() - currentTime < 2000) {
			super.onBackPressed();
		} else {
			currentTime = System.currentTimeMillis();
			ToastUtil.show("再次点击返回键退出~");
		}
	}

	/**
	 * 点击返回键时只是回到了桌面，并未直接退出
	 */
	private void pretendExit() {
		LaunchUtil.backToLauncher(this);
	}

	@Override
	public void onBackPressed() {
		pretendExit();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0,  0);
	}
}
