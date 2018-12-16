package com.sxu.basecomponent.uiwidget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sxu.basecomponent.activity.BaseActivity;

/*******************************************************************************
 * Description: 可在任意页面显示的Dialog（PS：仅为容器，Dialog的样式需自实现）
 *
 * Author: Freeman
 *
 * Date: 2018/12/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class MagicDialog extends AppCompatActivity {

	private boolean hasShown = false;
	private static View contentView;

	private final static String EXTRA_KEY_CANCELABLE = "canceledOnTouchOutside";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && !hasShown) {
			hasShown = true;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (contentView != null) {
				builder.setView(contentView);
			}
			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					finish();
				}
			}).show()
			.setCanceledOnTouchOutside(getIntent().getBooleanExtra(EXTRA_KEY_CANCELABLE, false));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		contentView = null;
	}

	public static void show(Context context, View dialogContentView, boolean canceledOnTouchOutside) {
		contentView = dialogContentView;
		Intent intent = new Intent(context, MagicDialog.class);
		intent.putExtra(EXTRA_KEY_CANCELABLE, canceledOnTouchOutside);
		context.startActivity(intent);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}
}
