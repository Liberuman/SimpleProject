package com.sxu.commonbusinessdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.pay.PayManager;
import com.sxu.commonbusiness.pay.PayRequestBean;
import com.sxu.commonbusiness.push.PushManager;
import com.sxu.commonbusiness.share.activity.ShareActivity;

@Route(path = "/business/Home")
public class CommonBusinessHomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_bussiness_home_layout);

		Button shareButton = findViewById(R.id.share_button);
		Button loginButton = findViewById(R.id.login_button);
		Button payButton = findViewById(R.id.pay_button);
		Button permissionButton = findViewById(R.id.permission_button);
		Button pushButton = findViewById(R.id.push_button);

		shareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareActivity.enter(CommonBusinessHomeActivity.this, "标题", "描述",
						"http://m.baidu.com",
						"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png");
			}
		});

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CommonBusinessHomeActivity.this, LoginVerificationActivity.class));
			}
		});

		payButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CommonBusinessHomeActivity.this, PayActivity.class));
			}
		});

		permissionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CommonBusinessHomeActivity.this, PermissionDemoActivity.class));
			}
		});

		pushButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CommonBusinessHomeActivity.this, PushActivity.class));
			}
		});
	}
}
