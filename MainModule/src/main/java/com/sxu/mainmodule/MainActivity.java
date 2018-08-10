package com.sxu.mainmodule;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.baselibrary.commonutils.EncodeUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.uiwidget.TabLayout;

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

		publicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				ARouter.getInstance().build("/base/home").navigation();
//				if (count < 2) {
//					ToastUtil.show("默认样式默认样式" + count);
//				} else if (count < 4) {
//					ToastUtil.show("正确样式正确样式" + count, ToastUtil.TOAST_STYLE_CORRECT);
//				} else if (count < 6) {
//					ToastUtil.show("警告样式警告样式" + count, ToastUtil.TOAST_STYLE_WARNING);
//				} else if (count < 8) {
//					ToastUtil.show("错误样式错误样式" + count, ToastUtil.TOAST_STYLE_ERROR);
//				}
//				ToastUtil.show("错误样式错误样式" + count);
				count++;
				LogUtil.i("value===" + EncodeUtil.encodeByHash("123" + count, "sha1"));
				LogUtil.i("value===" + EncodeUtil.getSha("123" + count));

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
				ARouter.getInstance().build("/base/home").navigation();
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
