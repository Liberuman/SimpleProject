package com.sxu.mainmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/31
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button publicButton = findViewById(R.id.public_button);
		Button baseButton = findViewById(R.id.base_button);
		Button commonButton = findViewById(R.id.common_button);

		publicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		baseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(MainActivity.this, BaseHomeActivity.class));
			}
		});
		commonButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
