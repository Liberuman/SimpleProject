package com.sxu.mainmodule;

import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.activity.BaseWebViewActivity;
import com.sxu.baselibrary.commonutils.EncodeUtil;
import com.sxu.baselibrary.commonutils.EncryptUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.uiwidget.TabLayout;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

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
	}
}
