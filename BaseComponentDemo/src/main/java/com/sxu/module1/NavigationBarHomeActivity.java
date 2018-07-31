package com.sxu.module1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.sxu.basecomponent.uiwidget.ToolbarEx;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;

public class NavigationBarHomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_bar_home_layout);

		ToolbarEx toolbarEx1 = findViewById(R.id.toolbar1);
		ToolbarEx toolbarEx2 = findViewById(R.id.toolbar2);
		ToolbarEx toolbarEx3 = findViewById(R.id.toolbar3);
		ToolbarEx toolbarEx4 = findViewById(R.id.toolbar4);
		ToolbarEx toolbarEx5 = findViewById(R.id.toolbar5);
		ToolbarEx toolbarEx6 = findViewById(R.id.toolbar6);

		toolbarEx1.setTitle("NavigationBar样式");

		toolbarEx2.setTitle("标题");
		toolbarEx2.setTitleGravity(Gravity.CENTER);

		toolbarEx3.setTitle("标题");
		toolbarEx3.setTitleGravity(Gravity.CENTER);
		toolbarEx3.setRightIcon(R.drawable.nav_close_icon, DisplayUtil.dpToPx(20), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtil.show("关闭");
			}
		});

		toolbarEx4.setLeftText("关闭");
		toolbarEx4.setRightText("提交");

		toolbarEx5.setTitle("标题");
		toolbarEx5.setRightTextAndEvent("提交", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtil.show("已提交");
			}
		});

		toolbarEx6.setTitle("标题");
		toolbarEx6.setRightIcons(new int[] {R.drawable.nav_more_icon,
		R.drawable.nav_menu_icon, R.drawable.nav_close_icon},
				DisplayUtil.dpToPx(4), DisplayUtil.dpToPx(12), new View.OnClickListener[]{
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ToastUtil.show("icon1");
						}
					}, null, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ToastUtil.show("icon3");
						}
					}
				});
	}
}
