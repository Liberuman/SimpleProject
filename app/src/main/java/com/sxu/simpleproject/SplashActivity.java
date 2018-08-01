package com.sxu.simpleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_layout);

		Button entryButton = findViewById(R.id.entry_button);

		entryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/main/home").navigation();
			}
		});
	}
}
