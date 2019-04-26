package com.sxu.mainmodule.setting;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.baselibrary.commonutils.AppUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;
import com.sxu.mainmodule.R;
import com.sxu.permission.CheckPermission;

import static com.sxu.mainmodule.setting.SettingPresenter.KEY_IS_PRODUCE_ENVIRONMENT;

/*******************************************************************************
 * Description: 设置页面
 *
 * Author: Freeman
 *
 * Date: 2019/3/21
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class SettingActivity extends BaseActivity {

	private TextView currentVersionText;
	private TextView cacheSizeText;
	private TextView currentEnvironmentText;

	private SettingPresenter presenter;

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_setting_layout;
	}

	@Override
	protected void getViews() {
		cacheSizeText = (TextView) findViewById(R.id.cache_size_text);
		currentVersionText = (TextView) findViewById(R.id.current_version_text);
		currentEnvironmentText = (TextView) findViewById(R.id.current_environment_text);
	}

	@Override
	protected void initActivity() {
		toolbar.setTitle("设置");
		presenter = new SettingPresenter(context);

		cacheSizeText.setText(presenter.getTotalCacheSize() + "M");
		currentVersionText.setText(AppUtil.getVersionName(context));
		boolean isProductEnvironment = PreferencesManager.getBoolean(KEY_IS_PRODUCE_ENVIRONMENT);
		if (isProductEnvironment) {
			currentEnvironmentText.setText("线上环境");
		} else {
			currentEnvironmentText.setText("测试环境");
		}
	}

	public void onAbout(View v) {
		presenter.aboutUs("关于", "http://m.baidu.com");
	}

	public void onShare(View v) {
		presenter.shareApp("App名字", "App描述", "http://m.baidu.com",
				"https://www.baidu.com/img/baidu_jgylogo3.gif");
	}

	public void onAppraise(View v) {
		presenter.appraiseApp();
	}

	public void onVersion(View v) {
		presenter.updateVersion();
	}

	public void onFeedback(View v) {
		startActivity(new Intent(this, FeedbackActivity.class));
	}

	public void onContactUs(View v) {
		presenter.contactUs("10086");
	}

	public void onClearCache(View v) {
		presenter.clearCache();
	}

	public void onOpenSourceLicence(View v) {
		startActivity(new Intent(this, OpenSourceLicenceActivity.class));
	}

	public void onSwitchEnvironment(View v) {
		presenter.switchEnvironment(currentEnvironmentText);
	}
}
