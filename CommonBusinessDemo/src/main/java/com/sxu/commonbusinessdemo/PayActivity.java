package com.sxu.commonbusinessdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.pay.PayManager;
import com.sxu.commonbusiness.pay.PayRequestBean;

public class PayActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_layout);

		Button aliPayButton = findViewById(R.id.ali_pay_text);
		Button wxPayButton = findViewById(R.id.wx_pay_text);

		aliPayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PayManager.getInstance(PayActivity.this).payByAliPay(
						PayActivity.this, "", new PayManager.PayListener() {
							@Override
							public void onSuccess() {
								ToastUtil.show("支付成功");
							}

							@Override
							public void onFailure(Exception e) {
								ToastUtil.show("支付失败" + e.getMessage());
							}
						}
				);
			}
		});

		wxPayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PayManager.getInstance(PayActivity.this).payByWeChat(
						new PayRequestBean(), new PayManager.PayListener() {
							@Override
							public void onSuccess() {

							}

							@Override
							public void onFailure(Exception e) {

							}
						});
			}
		});
	}
}
