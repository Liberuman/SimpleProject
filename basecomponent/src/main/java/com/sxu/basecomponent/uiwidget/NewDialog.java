package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.sxu.basecomponent.R;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class NewDialog extends android.support.v7.app.AlertDialog {

	private String title;
	private String message;

	private AlertDialog.Builder builder;

	public NewDialog(Context context) {
		this(context, null, null);
	}

	public NewDialog(Context context, String title, String message) {
		super(context);
		this.title = title;
		this.message = message;
		builder = new AlertDialog.Builder(context);
	}

	public NewDialog setTitle(String title) {
		this.title = title;
		builder.setTitle(title);
		return this;
	}

	public NewDialog setMessage(String message) {
		this.message = this.message;
		builder.setMessage(message);
		return this;
	}

	public NewDialog setOkButtonClickListener(final DialogInterface.OnClickListener listener) {
		return setOkButtonClickListener("确定", listener);
	}

	public NewDialog setOkButtonClickListener(String buttonText, final DialogInterface.OnClickListener listener) {
		builder.setPositiveButton(buttonText, listener);
		return this;
	}

	public NewDialog setCancelButtonClickListener(final DialogInterface.OnClickListener listener) {
		return setCancelButtonClickListener("取消", listener);
	}

	public NewDialog setCancelButtonClickListener(String buttonText, final DialogInterface.OnClickListener listener) {
		builder.setNegativeButton(buttonText, listener);
		return this;
	}

	public void show() {
		View itemView = View.inflate(getContext(), R.layout.dialog_common_layout, null);
		builder = new AlertDialog.Builder(getContext())
				.setView(itemView);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}
}