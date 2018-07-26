package com.sxu.basecomponent.uiwidget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.sxu.basecomponent.R;

/*******************************************************************************
 * Description: 加载对话框，用于执行异步任务时显示
 *
 * Author: Freeman
 *
 * Date: 2018/7/26
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ProgressDialog extends DialogFragment {

	private static ProgressDialog dialog;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ProgressDialogStyle);
		builder.setView(R.layout.dialog_progress_layout);
		Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	public void show(FragmentManager manager) {
		super.show(manager, getClass().getName());
	}

	public static void showDialog(FragmentManager fm) {
		if (dialog == null) {
			dialog = new ProgressDialog();
		}
		dialog.show(fm);
	}
	public static void closeDialog() {
		if (dialog == null) {
			return;
		}

		dialog.dismiss();
		dialog = null;
	}
}
