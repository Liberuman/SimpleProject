package com.sxu.simpleproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxu.basecomponent.uiwidget.BaseDialog;
import com.sxu.basecomponent.uiwidget.BottomDialog;
import com.sxu.basecomponent.uiwidget.ChooseDialog;
import com.sxu.basecomponent.uiwidget.NewDialog;
import com.sxu.baselibrary.commonutils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

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

//				final NewDialog dialog = new NewDialog();
//				dialog.setTitle("提示");
//				dialog.setMessage("哈哈哈哈哈哈哈");
//				dialog.setDialogStyle(BaseDialog.DIALOG_STYLE_CUSTOM);
////				dialog.setIsSingle(false);
////				dialog.setListData(new String[] {"item1", "item2", "item3", "item4", "item5",});
//
//				dialog.setCancelButtonClickListener("取消", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				});
//				dialog.setOkButtonClickListener("确定", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dd, int which) {
//						//ToastUtil.show(dialog.getCheckedIndexList().toString());
//					}
//				});
//				dialog.show(getSupportFragmentManager());
//
////				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
////						.setTitle("标题").setMessage("内容内容内容")
////						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
////							@Override
////							public void onClick(DialogInterface dialog, int which) {
////
////							}
////						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
////							@Override
////							public void onClick(DialogInterface dialog, int which) {
////
////							}
////						});
////				builder.show();

				//com.sxu.basecomponent.uiwidget.ProgressDialog.showDialog(getSupportFragmentManager());

//				BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
//				dialog.setContentView(R.layout.dialog_common_layout);
//				dialog.show();
//				BottomSheetDialogFragment dialogFragment = new BottomSheetDialogFragment();
//				BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogFragment.getDialog();
//				bottomSheetDialog.setContentView(R.layout.dialog_common_layout);
//				bottomSheetDialog.show();
				BottomDialog dialog = new BottomDialog();
				dialog.setTitle("选择照片");
				List<String> menuList = new ArrayList<>();
				menuList.add("拍照");
				menuList.add("从相册选择");
				dialog.setMenuList(menuList);
				dialog.setOnItemListener(new BottomDialog.OnItemClickListener() {
					@Override
					public void onItemClicked(String value, int position) {
						ToastUtil.show(value);
					}
				});
				dialog.show(getSupportFragmentManager(), "");
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
