package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.sxu.basecomponent.R;

/*******************************************************************************
 * Description: 对话框基类
 *
 * Author: Freeman
 *
 * Date: 2018/7/25
 *
 * Copyright: all rights reserved by ZhiNanMao.
 *******************************************************************************/

public class BaseDialog extends DialogFragment {

	protected String title;
	protected String cancelText;
	protected String okText;
	protected DialogInterface.OnClickListener cancelListener;
	protected DialogInterface.OnClickListener okListener;

	public final static int DIALOG_STYLE_MATERIAL = 1;
	public final static int DIALOG_STYLE_CUSTOM = 2;

	protected int dialogStyle = DIALOG_STYLE_CUSTOM;

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

	public void show(FragmentManager fm) {
		super.show(fm, getClass().getName());
	}
}
