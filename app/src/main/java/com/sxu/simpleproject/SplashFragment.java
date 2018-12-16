package com.sxu.simpleproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;

import com.sxu.basecomponent.fragment.BaseFragment;
import com.sxu.basecomponent.utils.PreferenceTag;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;
import com.sxu.imageloader.ImageLoaderListener;
import com.sxu.imageloader.ImageLoaderManager;
import com.sxu.imageloader.WrapImageView;

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

	private ImageView nameIcon;
	private TextView rightText;
	private View bottomLayout;
	private WrapImageView adImage;

	private Handler handler = new Handler();

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_splash_layout;
	}

	@Override
	protected void getViews() {
		adImage = contentView.findViewById(R.id.ad_image);
		nameIcon = contentView.findViewById(R.id.name_icon);
		rightText = contentView.findViewById(R.id.right_text);
		bottomLayout = contentView.findViewById(R.id.bottom_layout);
	}

	@Override
	protected void initFragment() {
		ImageLoaderManager.getInstance().displayImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544963782112&di=88042f9c65595a79866c0d2576279de1&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D890c50c9db00baa1ae214ff82f79d367%2Fcc11728b4710b912decd6bdbc9fdfc0392452282.jpg", adImage, new ImageLoaderListener() {
			@Override
			public void onStart() {

			}

			@Override
			public void onProcess(int i, int i1) {

			}

			@Override
			public void onCompleted(Bitmap bitmap) {
				updateView();
			}

			@Override
			public void onFailure(Exception e) {
				updateView();
			}
		});

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (PreferencesManager.getBoolean(PreferenceTag.KEY_IS_NEW_USER, true)) {
					context.startActivity(new Intent(context, WelcomeActivity.class));
				} else {
					ARouter.getInstance().build("/main/home").navigation();
				}
				((Activity)context).finish();
			}
		}, 3000);
	}

	private void updateView() {
		nameIcon.setImageResource(R.drawable.app_black_name_icon);
		rightText.setTextColor(Color.GRAY);
		bottomLayout.setBackgroundColor(Color.WHITE);
	}
}
