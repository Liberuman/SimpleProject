package com.sxu.module1;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sxu.basecomponent.uiwidget.BottomDialog;
import com.sxu.basecomponent.uiwidget.ChooseDialog;
import com.sxu.basecomponent.uiwidget.CommonDialog;
import com.sxu.basecomponent.uiwidget.ProgressDialog;
import com.sxu.baselibrary.commonutils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class DialogHomeActivity extends AppCompatActivity implements View.OnClickListener {


	private RadioButton materialRadio;
	private RadioButton customRadio;
	private RadioButton singleRadio;
	private RadioButton multiRadio;
	private RadioGroup styleGroup;
	private RadioGroup chooseGroup;

	private final int DIALOG_STYLE_MATERIAL = 1;
	private final int DIALOG_STYLE_CUSTOM = 2;

	private final int CHOOSE_TYPE_SINGLE = 1;
	private final int CHOOSE_TYPE_MULTI = 2;

	private int dialogStyle = DIALOG_STYLE_MATERIAL;
	private int chooseType = CHOOSE_TYPE_SINGLE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_home_layout);

		materialRadio = (RadioButton) findViewById(R.id.material_radio);
		customRadio = (RadioButton) findViewById(R.id.custom_radio);
		singleRadio = (RadioButton) findViewById(R.id.single_radio);
		multiRadio = (RadioButton) findViewById(R.id.multi_radio);
		styleGroup = findViewById(R.id.style_group);
		chooseGroup = findViewById(R.id.choose_group);

		findViewById(R.id.common_button).setOnClickListener(this);
		findViewById(R.id.choose_button).setOnClickListener(this);
		findViewById(R.id.progress_button).setOnClickListener(this);
		findViewById(R.id.bottom_button).setOnClickListener(this);

		styleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.material_radio) {
					dialogStyle = DIALOG_STYLE_MATERIAL;
				} else {
					dialogStyle = DIALOG_STYLE_CUSTOM;
				}
			}
		});

		chooseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.single_radio) {
					chooseType = CHOOSE_TYPE_SINGLE;
				} else {
					chooseType = CHOOSE_TYPE_MULTI;
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		int viewId = view.getId();
		if (viewId == R.id.common_button) {
			showCommonDialog();
		} else if (viewId == R.id.choose_button) {
			showChooseDialog();
		} else if (viewId == R.id.progress_button) {
			showProgressDialog();
		} else if (viewId == R.id.bottom_button) {
			showBottomDialog();
		}
	}

	private void showCommonDialog() {
		CommonDialog dialog = new CommonDialog();
		dialog.setTitle("提示");
		dialog.setMessage("确定要删除此条记录？");
		dialog.setCancelButtonClickListener("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ToastUtil.show("取消");
			}
		});
		dialog.setOkButtonClickListener("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ToastUtil.show("确定");
			}
		});

		if (dialogStyle == DIALOG_STYLE_MATERIAL) {
			dialog.setDialogStyle(DIALOG_STYLE_MATERIAL);
		} else {
			dialog.setDialogStyle(DIALOG_STYLE_CUSTOM);
		}
		dialog.show(getSupportFragmentManager());
	}

	private void showChooseDialog() {
		ChooseDialog dialog = new ChooseDialog();
		dialog.setTitle("计算机技术");
		List<String> items = new ArrayList<>();
		items.add("计算机网络");
		items.add("操作系统");
		items.add("编译原理");
		items.add("数据结构");
		items.add("数据库");
		dialog.setListData(items);
		dialog.setCancelButtonClickListener("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ToastUtil.show("取消");
			}
		});
		dialog.setOkButtonClickListener("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ToastUtil.show("确定");
			}
		});

		dialog.setIsSingle(chooseType == CHOOSE_TYPE_SINGLE);
		dialog.setDialogStyle(dialogStyle);
		dialog.show(getSupportFragmentManager());
	}

	private void showProgressDialog() {
		ProgressDialog.showDialog(getSupportFragmentManager());
	}

	private void showBottomDialog() {
		BottomDialog dialog = new BottomDialog();
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
		dialog.show(getSupportFragmentManager());
	}
}
