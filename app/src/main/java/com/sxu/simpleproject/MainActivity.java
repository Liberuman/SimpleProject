package com.sxu.simpleproject;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sxu.basecomponent.uiwidget.NewDialog;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//FragmentManager fm = getSupportFragmentManager();
				//final AlertDialog dialog = new NewDialog(MainActivity.this);
				//dialog.show();
				new NewDialog(MainActivity.this, "标题", "上海市").show();
//				dialog.setTitle("标题");
//				dialog.setMessage("内容内容内容内容内容内容内容内容内容内容内容内容内容");
//				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//					@Override
//					public void onCancel(DialogInterface dialog) {
//						dialog.cancel();
//					}
//				});
//
//				dialog.show();
			}
		});
	}
}
