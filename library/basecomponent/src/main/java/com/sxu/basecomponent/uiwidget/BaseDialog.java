package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.sxu.basecomponent.R;

/*******************************************************************************
 * Description: 对话框基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/25
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class BaseDialog extends DialogFragment {

	protected String title;
	protected String cancelText;
	protected String okText;
	protected DialogInterface.OnClickListener cancelListener;
	protected DialogInterface.OnClickListener okListener;

	public final static int DIALOG_STYLE_MATERIAL = 1;
	public final static int DIALOG_STYLE_CUSTOM = 2;

	protected int dialogStyle = DIALOG_STYLE_MATERIAL;

	public BaseDialog setTitle(String title) {
		this.title = title;
		return this;
	}

	public BaseDialog setDialogStyle(int dialogStyle) {
		this.dialogStyle = dialogStyle;
		return this;
	}

	public BaseDialog setCancelText(String cancelText) {
		this.cancelText = cancelText;
		return this;
	}

	public BaseDialog setOkText(String okText) {
		this.okText = okText;
		return this;
	}

	public BaseDialog setOkButtonClickListener(final DialogInterface.OnClickListener listener) {
		return setOkButtonClickListener("确定", listener);
	}

	public BaseDialog setOkButtonClickListener(String buttonText, final DialogInterface.OnClickListener listener) {
		this.okText = buttonText;
		this.okListener = listener;
		return this;
	}

	public BaseDialog setCancelButtonClickListener(final DialogInterface.OnClickListener listener) {
		return setCancelButtonClickListener("取消", listener);
	}

	public BaseDialog setCancelButtonClickListener(String buttonText, final DialogInterface.OnClickListener listener) {
		this.cancelText = buttonText;
		this.cancelListener = listener;
		return this;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder;
		if (dialogStyle == DIALOG_STYLE_MATERIAL) {
			builder = new AlertDialog.Builder(getContext());
			if (!TextUtils.isEmpty(title)) {
				builder.setTitle(title);
			}
			if (cancelListener != null) {
				builder.setNegativeButton(cancelText, cancelListener);
			}
			if (okListener != null) {
				builder.setPositiveButton(okText, okListener);
			}
			initMaterialDialog(builder);
		} else {
			builder = new AlertDialog.Builder(getContext(), R.style.CommonDialog);
			initCustomDialog(builder);
		}

		return builder.create();
	}

	protected abstract void initMaterialDialog(AlertDialog.Builder builder);

	protected abstract void initCustomDialog(AlertDialog.Builder builder);

	public void show(FragmentManager fm) {
		super.show(fm, getClass().getName());
	}
}
