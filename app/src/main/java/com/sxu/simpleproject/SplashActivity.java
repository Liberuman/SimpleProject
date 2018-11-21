package com.sxu.simpleproject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sxu.basecomponent.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

	@Override
	protected void pretreatment() {
		super.pretreatment();
		overridePendingTransition(0, 0);
		setToolbarStyle(TOOL_BAR_STYLE_NONE);
	}

	@Override
	protected int getLayoutResId() {
		return 0;
	}

	@Override
	protected void getViews() {

	}

	@Override
	protected void initActivity() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(android.R.id.content, new SplashFragment());
		transaction.commit();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0,  0);
	}
}
