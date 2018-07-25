package com.sxu.simpleproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxu.basecomponent.uiwidget.BaseDialog;
import com.sxu.basecomponent.uiwidget.ChooseDialog;
import com.sxu.basecomponent.uiwidget.NewDialog;
import com.sxu.baselibrary.commonutils.ToastUtil;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button publicButton = findViewById(R.id.public_button);
		Button baseButton = findViewById(R.id.base_button);
		Button commonButton = findViewById(R.id.common_button);

		publicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ChooseDialog dialog = new ChooseDialog();
				dialog.setTitle("提示");
				dialog.setDialogStyle(BaseDialog.DIALOG_STYLE_MATERIAL);
				dialog.setIsSingle(true);
				dialog.setListData(new String[] {"item1", "item2", "item3", "item4", "item5"});

				dialog.setCancelButtonClickListener("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ToastUtil.show("test");
					}
				});
				dialog.setOkButtonClickListener("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ToastUtil.show("test确定");
					}
				});
				dialog.show(getSupportFragmentManager());
			}
		});
		baseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, BaseHomeActivity.class));
			}
		});
		commonButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
