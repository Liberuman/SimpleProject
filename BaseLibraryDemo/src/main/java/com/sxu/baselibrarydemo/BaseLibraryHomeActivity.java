package com.sxu.baselibrarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxu.baselibrary.commonutils.LogUtil;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/31
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
@Route(path = "/base/home")
public class BaseLibraryHomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_library_home_layout);

		Button toolButton = findViewById(R.id.tool_button);
		Button uiButton = findViewById(R.id.ui_button);

		toolButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BaseLibraryHomeActivity.this, ToolHomeActivity.class));
			}
		});
		uiButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BaseLibraryHomeActivity.this, UIHomeActivity.class));
			}
		});
	}
}
