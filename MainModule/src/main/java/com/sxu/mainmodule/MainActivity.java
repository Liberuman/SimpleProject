package com.sxu.mainmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/31
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
@Route(path = "/main/home")
public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ARouter.init(getApplication());

		Button publicButton = findViewById(R.id.public_button);
		Button baseButton = findViewById(R.id.base_button);
		Button commonButton = findViewById(R.id.common_button);

		publicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/base/home").navigation();
			}
		});
		baseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/component/home").navigation();
			}
		});
		commonButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/base/home").navigation();
			}
		});
	}
}
