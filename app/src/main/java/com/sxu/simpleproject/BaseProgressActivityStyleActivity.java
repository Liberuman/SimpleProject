package com.sxu.simpleproject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.sxu.basecomponent.activity.BaseProgressActivity;

public class BaseProgressActivityStyleActivity extends BaseProgressActivity {

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_base_progress_activity_style_layout;
	}

	@Override
	protected void getViews() {

	}

	@Override
	protected void initActivity() {

	}

	@Override
	protected void requestData() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				notifyLoadFinish(MSG_LOAD_EMPTY);
			}
		}, 2000);
	}

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(getIntent().getIntExtra("toolbarStyle", 0));
	}

	public static void enter(Context context, int toolbarStyle) {
		Intent intent = new Intent(context, BaseProgressActivityStyleActivity.class);
		intent.putExtra("toolbarStyle", toolbarStyle);
		context.startActivity(intent);
	}
}
