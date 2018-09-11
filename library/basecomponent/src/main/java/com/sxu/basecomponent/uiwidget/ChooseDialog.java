package com.sxu.basecomponent.uiwidget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.adapter.CommonAdapter;
import com.sxu.basecomponent.adapter.ViewHolder;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE_MODAL;
import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

/*******************************************************************************
 * Description: 选择类的对话框
 *
 * Author: Freeman
 *
 * Date: 2018/7/23
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ChooseDialog extends BaseDialog {

	private boolean isSingle = true;
	private String[] items;
	private int checkedItemIndex = -1;
	private boolean[] checkedItems;
	private Set<Integer> checkedIndexList =  new TreeSet<>();
	private Dialog.OnClickListener itemListener;
	private DialogInterface.OnMultiChoiceClickListener multiChoiceListener;

	private RadioButton selectedRadioButton;

	private AlertDialog.Builder builder;

	private final int VISIBLE_ITEM_MAX_COUNT = 4;
	private final int ITEM_HEIGHT = 56;

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
		if (checkedItems == null || checkedItems.length == 0) {
			return this;
		}

		for (int i = 0; i < checkedItems.length; i++) {
			if (checkedItems[i]) {
				checkedIndexList.add(i);
			}
		}

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

	public int getCheckedItemIndex() {
		return checkedItemIndex;
	}

	public Set<Integer> getCheckedIndexList() {
		return checkedIndexList;
	}

	private View createView(boolean isSingle) {
		View dialogView = View.inflate(getContext(), R.layout.dialog_choose_layout, null);
		TextView titleText = dialogView.findViewById(R.id.title_text);
		TextView cancelText = dialogView.findViewById(R.id.cancel_text);
		TextView okText = dialogView.findViewById(R.id.ok_text);
		View gapLine = dialogView.findViewById(R.id.gap_line);
		View buttonLayout = dialogView.findViewById(R.id.button_layout);
		final ListView contentList = dialogView.findViewById(R.id.content_list);

		if (!TextUtils.isEmpty(title)) {
			titleText.setText(title);
		} else {
			titleText.setVisibility(View.GONE);
		}
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

		if (items != null && items.length > 0) {
			if (items.length > VISIBLE_ITEM_MAX_COUNT) {
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentList.getLayoutParams();
				params.height = DisplayUtil.dpToPx(ITEM_HEIGHT) * VISIBLE_ITEM_MAX_COUNT;
				contentList.setLayoutParams(params);
			}
			BaseAdapter adapter;
			if (isSingle) {
				adapter = createSingleChooseAdapter();
			} else {
				adapter = createMultiChooseDataAdapter();
			}
			contentList.setAdapter(adapter);
			contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				}
			});
		}

		return dialogView;
	}

	private BaseAdapter createSingleChooseAdapter() {
		return new CommonAdapter<String>(getContext(), items, R.layout.dialog_item_single_choose_layout) {
			@Override
			public void convert(final ViewHolder holder, String paramT, final int position) {
				holder.setText(R.id.item_text, paramT);
				final RadioButton radioButton = holder.getView(R.id.radio_button);
				if (checkedItemIndex == position) {
					radioButton.setChecked(true);
					selectedRadioButton = radioButton;
				} else {
					radioButton.setChecked(false);
				}

				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (position != checkedItemIndex) {
							if (selectedRadioButton != null) {
								selectedRadioButton.setChecked(false);
							}
							radioButton.setChecked(true);
							selectedRadioButton = radioButton;
							checkedItemIndex = position;
						} else {
							radioButton.setChecked(false);
						}
					}
				};
				holder.getContentView().setOnClickListener(listener);
				radioButton.setOnClickListener(listener);
			}
		};
	}

	private BaseAdapter createMultiChooseDataAdapter() {
		return new CommonAdapter<String>(getContext(), items, R.layout.dialog_item_multi_choose_layout) {
			@Override
			public void convert(final ViewHolder holder, String paramT, final int position) {
				holder.setText(R.id.item_text, paramT);
				final CheckBox checkBox = holder.getView(R.id.checkbox);
				if (checkedIndexList.contains(position)) {
					checkBox.setChecked(true);
				} else {
					checkBox.setChecked(false);
				}

				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						checkBox.setChecked(!checkBox.isChecked());
					}
				};

				holder.getContentView().setOnClickListener(listener);
				checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							checkedIndexList.add(position);
						} else {
							checkedIndexList.remove(position);
						}
					}
				});
			}
		};
	}

	@Override
	protected void initMaterialDialog(AlertDialog.Builder builder) {
		if (isSingle) {
			builder.setSingleChoiceItems(items, checkedItemIndex, itemListener);
		} else {
			builder.setMultiChoiceItems(items, checkedItems, multiChoiceListener);
		}
	}

	@Override
	protected void initCustomDialog(AlertDialog.Builder builder) {
		builder.setView(createView(isSingle));
	}
}
