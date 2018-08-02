package com.sxu.commonbusinessdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareManager;
import com.sxu.commonbusiness.share.activity.ShareActivity;

@Route(path = "/business/Home")
public class CommonBusinessHomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_bussiness_home_layout);

		Button shareButton = findViewById(R.id.share_button);

		shareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareActivity.enter(CommonBusinessHomeActivity.this, "标题", "描述",
						"http://m.baidu.com",
						"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png");
			}
		});
	}
}
