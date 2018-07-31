package com.sxu.simpleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_layout);

		Button entryButton = findViewById(R.id.entry_button);

		entryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(SplashActivity.this, MainActivity.class));
			}
		});
	}
}
