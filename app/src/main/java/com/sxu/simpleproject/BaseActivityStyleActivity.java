package com.sxu.simpleproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sxu.basecomponent.activity.BaseActivity;

public class BaseActivityStyleActivity extends BaseActivity {

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_base_activity_style_layout;
	}

	@Override
	protected void getViews() {

	}

	@Override
	protected void initActivity() {

	}

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(getIntent().getIntExtra("toolbarStyle", 0));
	}

	public static void enter(Context context, int toolbarStyle) {
		Intent intent = new Intent(context, BaseActivityStyleActivity.class);
		intent.putExtra("toolbarStyle", toolbarStyle);
		context.startActivity(intent);
	}
}
