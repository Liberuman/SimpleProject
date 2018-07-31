package com.sxu.baselibrarydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sxu.basecomponent.activity.BaseActivity;

public class ToolHomeActivity extends BaseActivity {

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_tool_home_layout;
	}

	@Override
	protected void getViews() {
		toolbar.setTitle("工具类介绍");
	}

	@Override
	protected void initActivity() {

	}
}
