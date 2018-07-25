package com.sxu.basecomponent.uiwidget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import java.util.Arrays;
import java.util.List;

/*******************************************************************************
 * Description: 选择类的对话框
 *
 * Author: Freeman
 *
 * Date: 2018/7/23
 *
 * Copyright: all rights reserved by ZhiNanMao.
 *******************************************************************************/

public class ChooseDialog extends BaseDialog {

	private boolean isSingle = true;
	private String[] items;
	private int checkedItemIndex = -1;
	private boolean[] checkedItems;
	private Dialog.OnClickListener itemListener;
	private DialogInterface.OnMultiChoiceClickListener multiChoiceListener;

	private AlertDialog.Builder builder;

	public ChooseDialog setIsSingle(boolean isSingle) {
		this.isSingle = isSingle;
		return this;
	}

	public ChooseDialog setListData(String[] itemList) {
		return setListData(Arrays.asList(itemList));
	}

	public ChooseDialog setListData(List<String> itemList) {
		this.items = itemList.toArray(new String[itemList != null ? itemList.size() : 0]);
		return this;
	}

	public ChooseDialog setCheckedItemIndex(int itemIndex) {
		this.checkedItemIndex = itemIndex;
		return this;
	}

	public ChooseDialog setDefaultMultiIndex(boolean[] checkedItems) {
		this.checkedItems = checkedItems;
		return this;
	}

	public ChooseDialog setOnItemClickListener(Dialog.OnClickListener listener) {
		this.itemListener = listener;
		return this;
	}

	public ChooseDialog setMultiChoiceListener(DialogInterface.OnMultiChoiceClickListener listener) {
		this.multiChoiceListener = listener;
		return this;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		builder = new AlertDialog.Builder(getContext());
		if (dialogStyle == DIALOG_STYLE_MATERIAL) {
			if (isSingle) {
				builder.setSingleChoiceItems(items, checkedItemIndex, itemListener);
			} else {
				builder.setMultiChoiceItems(items, checkedItems, multiChoiceListener);
			}
			if (okListener != null) {
				builder.setPositiveButton(okText, okListener);
			}
			if (cancelListener != null) {
				builder.setNegativeButton(cancelText, cancelListener);
			}
		} else {

		}

		return builder.create();
	}
}
