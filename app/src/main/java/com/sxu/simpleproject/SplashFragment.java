package com.sxu.simpleproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;

import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.fragment.BaseFragment;
import com.sxu.basecomponent.utils.PreferenceTag;
import com.sxu.baselibrary.commonutils.LaunchUtil;
import com.sxu.baselibrary.commonutils.SpannableStringUtil;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;

import java.util.logging.Handler;

/**
 * 启动页的主要逻辑包括：
 * 1. 展示logo；
 * 2. 版本更新；
 * 3. 展示广告；
 * 4. 处理APP进入时的跳转逻辑（登录与否，推送的跳转）；
 */

/*******************************************************************************
 * Description: 启动页(对于广告的展示，直接在此页面中实现体验更好)
 *
 * Author: Freeman
 *
 * Date: 2018/11/21
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class SplashFragment extends BaseFragment {

	private TextView descText;

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_splash_layout;
	}

	@Override
	protected void getViews() {
		descText = contentView.findViewById(R.id.desc_text);
	}

	@Override
	protected void initFragment() {
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

		new android.os.Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (PreferencesManager.getBoolean(PreferenceTag.KEY_IS_NEW_USER, true)) {
					context.startActivity(new Intent(context, NewUserGuideActivity.class));
				} else {
					ARouter.getInstance().build("/main/home").navigation();
				}
				((Activity)context).finish();
			}
		}, 3000);
	}
}
