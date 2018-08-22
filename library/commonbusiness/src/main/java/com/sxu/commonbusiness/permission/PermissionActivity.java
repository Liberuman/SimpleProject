package com.sxu.commonbusiness.permission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sxu.baselibrary.commonutils.PermissionUtil;

/*******************************************************************************
 * Description: 用于申请权限的透明页面
 *
 * Author: Freeman
 *
 * Date: 2018/8/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PermissionActivity extends AppCompatActivity {

	private static PermissionUtil.OnPermissionRequestListener permissionListener;

	private static final String PARAMS_PERMISSION_DESC = "permission_desc";
	private static final String PARAMS_SETTING_DESC = "setting_desc";
	private static final String PARAMS_PERMISSIONS = "permissions";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
		String desc = getIntent().getStringExtra(PARAMS_PERMISSION_DESC);
		String settingDesc = getIntent().getStringExtra(PARAMS_SETTING_DESC);
		String[] requestPermissions = getIntent().getStringArrayExtra(PARAMS_PERMISSIONS);
		PermissionUtil.setPermissionRequestListener(desc, settingDesc, permissionListener);
		PermissionUtil.checkPermission(this, requestPermissions);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionUtil.requestCallback(this, requestCode, permissions, grantResults);
	}

	public static void enter(Context context, String[] permissions, String permissionDesc,
	                         String permissionSettingDesc, PermissionUtil.OnPermissionRequestListener listener) {
		Intent intent = new Intent(context, PermissionActivity.class);
		intent.putExtra(PARAMS_PERMISSION_DESC, permissionDesc);
		intent.putExtra(PARAMS_SETTING_DESC, permissionSettingDesc);
		intent.putExtra(PARAMS_PERMISSIONS, permissions);
		permissionListener = listener;
		context.startActivity(intent);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}
}
