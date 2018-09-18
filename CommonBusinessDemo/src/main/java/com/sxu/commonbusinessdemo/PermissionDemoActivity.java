package com.sxu.commonbusinessdemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.permission.CheckPermission;

public class PermissionDemoActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission_demo_layout);
		Button openCameraButton = findViewById(R.id.open_camera);
		openCameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openCamera();
			}
		});
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA},
			permissionDesc = "此功能需要访问相机", settingDesc = "快去设置中打开相机权限")
	public void openCamera() {
		ToastUtil.show("获取权限啦");
	}
}
