package com.sxu.module1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.sxu.basecomponent.activity.BaseProgressActivity;
import com.sxu.basecomponent.processor.RequestProcessor;

public class BaseProgressActivityStyleActivity extends BaseProgressActivity implements TestContract.TestView {

	private TestContract.TestPresenter presenter;

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
		if (presenter == null) {
			presenter = new BaseProgressActivityStylePresenter(this);
		}
		presenter.requestData();
	}

	@Override
	public void requestComplete(int msg) {
		notifyLoadFinish(msg);
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
