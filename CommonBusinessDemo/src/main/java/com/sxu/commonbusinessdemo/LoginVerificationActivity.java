package com.sxu.commonbusinessdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxu.basecomponent.utils.BaseApplication;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.login.aspect.CheckLogin;
import com.sxu.commonbusiness.login.aspect.LoginAspect;

import org.aspectj.lang.ProceedingJoinPoint;

public class LoginVerificationActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_verification_layout);

		LoginAspect.setOnLoginListener(new LoginAspect.OnLoginListener() {
			@Override
			public void onLogin(ProceedingJoinPoint joinPoint) {
				if (BaseApplication.isLogin) {
					ToastUtil.show("已登录，跳转到新页面");
				} else {
					ToastUtil.show("还未登录，跳转到登录页面登录");
				}
			}
		});

		Button loginButton = findViewById(R.id.login_button);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterOrderDetail();
			}
		});
	}

	@CheckLogin
	private void enterOrderDetail() {
		ToastUtil.show("到新页面了");
	}
}
