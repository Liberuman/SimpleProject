package com.sxu.basecomponent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sxu.baselibrary.commonutils.PermissionUtil;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/8/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PermissionActivity extends AppCompatActivity {

	private static OnPermissionListener permissionListener;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionUtil.requestCallback(this, requestCode, permissions, grantResults);
	}

	public static void enter(Context context, OnPermissionListener listener) {
		permissionListener = listener;
		context.startActivity(new Intent(context, PermissionActivity.class));
	}

	public interface OnPermissionListener {
		// 权限已获取
		void onGranted();

		// 权限被取消
		void onCanceled();

		// 权限别拒绝
		void onDenied();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}
}
