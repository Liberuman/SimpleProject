package com.sxu.module1;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.manager.SingletonManager;
import com.sxu.basecomponent.uiwidget.ContainerLayoutStyle;
import com.sxu.baselibrary.commonutils.LogUtil;

import java.io.Serializable;

@Route(path = "/component/home")
public class BaseHomeActivity extends BaseActivity {

	private int toolbarStyle;

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_base_home_layout;
	}

	@Override
	protected void pretreatment() {
		super.pretreatment();
		setToolbarStyle(ContainerLayoutStyle.TOOL_BAR_STYLE_NORMAL);
	}

	@Override
	protected void getViews() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		Button entryButton = (Button) findViewById(R.id.entry_button);
		Button entryButton2 = (Button) findViewById(R.id.entry_button2);
		Button fragmentButton = (Button) findViewById(R.id.fragment_button);
		Button progressFragmentButton = (Button) findViewById(R.id.fragment_progress_button);
		Button dialogButton = (Button) findViewById(R.id.dialog_button);
		Button navigationButton = (Button) findViewById(R.id.navigation_button);

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.no_toolbar_radio) {
					toolbarStyle = 0;
				} else if (checkedId == R.id.normal_radio) {
					toolbarStyle = 1;
				} else if (checkedId == R.id.transparent_radio) {
					toolbarStyle = 2;
				} else if (checkedId == R.id.translucent_radio) {
					toolbarStyle = 3;
				}
			}
		});

		entryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseActivityStyleActivity.enter(context, toolbarStyle);
			}
		});

		entryButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseProgressActivityStyleActivity.enter(context, toolbarStyle);
			}
		});

		fragmentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseFragmentStyleActivity.enter(context, toolbarStyle);
			}
		});

		progressFragmentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseProgressFragmentStyleActivity.enter(context, toolbarStyle);
			}
		});



		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, DialogHomeActivity.class));
			}
		});

		navigationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, NavigationBarHomeActivity.class));
			}
		});
	}

	@Override
	protected void initActivity() {
		toolbar.setTitle("基础业务层");
//		LogUtil.i("isLogin====" + UserManager.getInstance().isLogin());
//		UserManager.getInstance().setUserInfo(new UserInfo());
//		LogUtil.i("isLogin====" + UserManager.getInstance().isLogin() + " userInfo=" + UserManager.getInstance().getUserInfo().toString());
//		UserInfo userInfo = new UserInfo();
//		userInfo.userName = "Znm";
//		UserManager.getInstance().setUserInfo(userInfo);
//		LogUtil.i("userInfo====" + UserManager.getInstance().getUserInfo().toString());
//		UserManager.getInstance().clearUserInfo();
//		LogUtil.i("isLogin====" + UserManager.getInstance().isLogin());

		startService(new Intent(this, ShowMagicDialogTestService.class));
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					TestContract userInfo = SingletonManager.getInstance(TestContract.class);
					LogUtil.i("========userInfo" + userInfo.toString());
				}
			}).start();
			TestContract userInfo = SingletonManager.getInstance(TestContract.class);
			LogUtil.i("********userInfo" + userInfo.toString());
		}

		SingletonManager.removeInstance(TestContract.class);
	}
}
