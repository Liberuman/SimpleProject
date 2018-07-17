package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;

import java.util.zip.Inflater;

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

	private TextView titleText;
	private TextView descText;
	private TextView cancelText;
	private TextView okText;
	private TextView gapLine;

	private String title;
	private String desc;

	private AlertDialog.Builder builder;

	public NewDialog(Context context) {
		super(context);
	}
	public NewDialog(Context context, String title, String desc) {
		super(context, R.style.CommonDialog);
		this.title = title;
		this.desc = desc;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ToastUtil.show("onCreate");
	}

	// 设置富文本内容
	public void setSpanContent(SpannableString text) {
		descText.setText(text);
	}

	public void setCancelColor(int color) {
		cancelText.setTextColor(color);
	}

	public void setOkButtonText(String text) {
		okText.setText(text);
	}
	public TextView getOkButton(){
		return okText;
	}

	public void setCancelText(String text) {
		cancelText.setText(text);
	}

	public void setCancelTextHide() {
		cancelText.setVisibility(View.GONE);
		gapLine.setVisibility(View.GONE);
	}

	public void setOkButtonClickListener(final DialogInterface.OnClickListener listener) {
		if (listener == null) {
			return;
		}

		builder.setPositiveButton("确定", listener);
	}

	public void setCancelButtonClickListener(final DialogInterface.OnClickListener listener) {
		if (listener == null) {
			return;
		}

		builder.setNegativeButton("取消", listener);
	}

	public void show() {
		ToastUtil.show("show");
		if (builder == null) {
			View itemView = View.inflate(getContext(), R.layout.dialog_common_layout, null);
			builder = new AlertDialog.Builder(getContext())
					.setView(itemView);
		}


		builder.show();
	}
}