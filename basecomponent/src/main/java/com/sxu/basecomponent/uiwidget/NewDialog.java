package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class NewDialog extends BaseDialog {

	private String message;

	public NewDialog setMessage(String message) {
		this.message = message;
		return this;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		if (dialogStyle == DIALOG_STYLE_MATERIAL) {
			builder.setTitle(title).setMessage(message);
			if (cancelListener != null) {
				builder.setNegativeButton(cancelText, cancelListener);
			}
			if (okListener != null) {
				builder.setPositiveButton(okText, okListener);
			}
		} else {
			View itemView = View.inflate(getContext(), R.layout.dialog_common_layout, null);
			TextView titleText = itemView.findViewById(R.id.title_text);
			TextView messageText = itemView.findViewById(R.id.message_text);
			TextView cancelText = itemView.findViewById(R.id.cancel_text);
			final TextView okText = itemView.findViewById(R.id.ok_text);
			View gapLine = itemView.findViewById(R.id.gap_line);
			View buttonLayout = itemView.findViewById(R.id.button_layout);
			titleText.setText(title);
			messageText.setText(message);
			cancelText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					cancelListener.onClick(null, 0);
				}
			});
			okText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					okListener.onClick(null, 0);
				}
			});
			int state = android.R.attr.state_pressed;
			int radius = DisplayUtil.dpToPx(8);
			int[] bgColor = new int[] {Color.WHITE, ContextCompat.getColor(getContext(), R.color.b5)};
			// 根据设置的listener显示按钮，并设置相应的背景
			if (cancelListener != null && okListener != null) {
				ViewBgUtil.setSelectorBg(cancelText, state, bgColor,
						new float[] {0 , 0, 0, 0, 0, 0, radius, radius});
				ViewBgUtil.setSelectorBg(okText, state, bgColor,
						new float[] {0 , 0, 0, 0, radius, radius, 0, 0});
			} else if (cancelListener != null && okListener == null) {
				gapLine.setVisibility(View.GONE);
				okText.setVisibility(View.GONE);
				ViewBgUtil.setSelectorBg(cancelText, state, bgColor,
						new float[] {0 , 0, 0, 0, radius, radius, radius, radius});
			} else if (cancelListener == null && okListener != null) {
				gapLine.setVisibility(View.GONE);
				cancelText.setVisibility(View.GONE);
				ViewBgUtil.setSelectorBg(okText, state, bgColor,
						new float[] {0 , 0, 0, 0, radius, radius, radius, radius});
			} else {
				buttonLayout.setVisibility(View.GONE);
			}
			builder.setView(itemView);
		}

		return builder.create();
	}
}