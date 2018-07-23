package com.sxu.basecomponent.uiwidget;

import android.app.Dialog;
import android.content.Context;
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

public class ChooseDialog extends AlertDialog {

	private boolean isSingle = true;
	private String title;
	private String[] items;
	private int checkedItemIndex = -1;
	private boolean[] checkedItems;
	private Dialog.OnClickListener itemListener;
	private OnMultiChoiceClickListener multiChoiceListener;

	private AlertDialog.Builder builder;

	public ChooseDialog(Context context) {
		super(context);
		builder = new AlertDialog.Builder(context);
	}

	public ChooseDialog(Context context, boolean isSingle, String title, String[] items) {
		super(context);
		this.isSingle = isSingle;
		this.title = title;
		this.items = items;
		builder = new AlertDialog.Builder(context);
	}

	public ChooseDialog(Context context, boolean isSingle, String title, List<String> itemList) {
		this(context, isSingle, title, itemList.toArray(new String[itemList != null ? itemList.size() : 0]));
	}

	public ChooseDialog setTitle(String title) {
		this.title = title;
		builder.setTitle(title);
		return this;
	}

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

	public ChooseDialog setMultiChoiceListener(OnMultiChoiceClickListener listener) {
		this.multiChoiceListener = listener;
		return this;
	}

	public void show() {
		if (isSingle) {
			builder.setSingleChoiceItems(items, checkedItemIndex, itemListener);
		} else {
			builder.setMultiChoiceItems(items, checkedItems, multiChoiceListener);
		}

		builder.show();
	}
}
