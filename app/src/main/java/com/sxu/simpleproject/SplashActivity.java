package com.sxu.simpleproject;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.annotation.CheckLogin;
import com.sxu.basecomponent.utils.PreferenceTag;
import com.sxu.baselibrary.commonutils.LaunchUtil;
import com.sxu.baselibrary.commonutils.SpannableStringUtil;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;

public class SplashActivity extends BaseActivity {

	private TextView descText;
	private Button entryButton;

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(TOOL_BAR_STYLE_NONE);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_splash_layout;
	}

	@Override
	protected void getViews() {
		entryButton = (Button) findViewById(R.id.entry_button);
		descText = (TextView) findViewById(R.id.desc_text);
	}

	@Override
	protected void initActivity() {
		String desc = "将启动页的主题背景设置成和启动页相同的背景，即可解决黑白屏问题，具体可参考：";
		String urlDesc = "Android开发细节";
		SpannableStringUtil.setText(descText, desc + urlDesc, SpannableStringUtil.SPAN_TYPE_TEXT_COLOR,
				Color.BLUE, desc.length(), urlDesc.length());

		descText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LaunchUtil.openBrowse(context,
						"https://juejin.im/post/5b3e14f8e51d45190570afae");
			}
		});
		entryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PreferencesManager.getBoolean(PreferenceTag.KEY_IS_NEW_USER, true)) {
					startActivity(new Intent(context, NewUserGuideActivity.class));
				} else {
					ARouter.getInstance().build("/main/home").navigation();
				}
				finish();
			}
		});
	}
}
