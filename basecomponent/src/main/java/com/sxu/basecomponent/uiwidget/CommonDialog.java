package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;
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

public class CommonDialog extends BaseDialog {

	private String message;

	public CommonDialog setMessage(String message) {
		this.message = message;
		return this;
	}

	private View createView() {
		View itemView = View.inflate(getContext(), R.layout.dialog_common_layout, null);
		TextView titleText = itemView.findViewById(R.id.title_text);
		TextView messageText = itemView.findViewById(R.id.message_text);
		final TextView cancelText = itemView.findViewById(R.id.cancel_text);
		final TextView okText = itemView.findViewById(R.id.ok_text);
		View gapLine = itemView.findViewById(R.id.gap_line);
		View buttonLayout = itemView.findViewById(R.id.button_layout);
		titleText.setText(title);
		messageText.setText(message);
		cancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelListener != null) {
					cancelListener.onClick(null, 0);
				}
				dismiss();
			}
		});
		okText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (okListener != null) {
					okListener.onClick(null, 0);
				}
				dismiss();
			}
		});
		int state = android.R.attr.state_pressed;
		int radius = DisplayUtil.dpToPx(8);
		int[] bgColor = new int[] {Color.WHITE, ContextCompat.getColor(getContext(), R.color.g5)};
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

		return itemView;
	}

	@Override
	protected void initMaterialDialog(AlertDialog.Builder builder) {
		if (!TextUtils.isEmpty(message)) {
			builder.setMessage(message);
		}
	}

	@Override
	protected void initCustomDialog(AlertDialog.Builder builder) {
		builder.setView(createView());
	}
}