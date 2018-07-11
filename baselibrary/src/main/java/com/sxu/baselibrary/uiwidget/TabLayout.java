package com.sxu.baselibrary.uiwidget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxu.baselibrary.R;
import com.sxu.baselibrary.commonutils.ViewBgUtil;


/* *****************************************************************************
 * Description: 自定义TabLayout
 *
 * Author: Freeman
 *
 * Date: 2018/07/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class TabLayout extends LinearLayout {

	private int mTextSize;
	private int mMiddleGap;
	private int currentIndex = -1;
	private int lastSelectedViewIndex = -1;
	private OnItemStateListener mListener;

	public TabLayout(Context context) {
		this(context, null);
	}

	public TabLayout(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		TypedArray arrays = context.obtainStyledAttributes(attrs, R.styleable.bl_TabLayout);
		mTextSize = arrays.getDimensionPixelSize(R.styleable.bl_TabLayout_bl_textSize, 11);
		mMiddleGap = arrays.getDimensionPixelSize(R.styleable.bl_TabLayout_bl_middleGap, 0);
		arrays.recycle();
		setOrientation(LinearLayout.HORIZONTAL);
	}

	@Override
	public void setOrientation(int orientation) {
		if (orientation == LinearLayout.HORIZONTAL) {
			super.setOrientation(orientation);
		}
	}


	public void setItemData(int[] textColor, String[] tabText, int[] normalIconIds, int[] selectedIconIds) {
		setItemData(0, textColor, tabText, normalIconIds, selectedIconIds);
	}

	public void setItemData(int textPaddingTop, int[] textColor, String[] tabText, int[] normalIconIds, int[] selectedIconIds) {
		if (tabText == null || normalIconIds == null || selectedIconIds == null) {
			throw new NullPointerException("tabText or selectedIconId or unselectedIconId is null");
		} else if (tabText.length == 0 || tabText.length != normalIconIds.length
				|| selectedIconIds.length != normalIconIds.length) {
			throw new NullPointerException("tabText, selectedIconId and unselectedIconId should has same size");
		}

		LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		params.gravity = Gravity.CENTER;
		ColorStateList colorState = new ColorStateList(new int[][]{
				new int[]{android.R.attr.state_selected},
				new int[]{}
		}, textColor);
		for (int i = 0; i < tabText.length; i++) {
			View childView = View.inflate(getContext(), R.layout.bl_item_tab_layout, null);
			TextView textView = childView.findViewById(R.id.bl_tab_text);
			ImageView tabIcon = childView.findViewById(R.id.bl_tab_icon);
			textView.setPadding(0, textPaddingTop, 0, 0);
			textView.setText(tabText[i]);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
			textView.setTextColor(colorState);
			if (mMiddleGap != 0) {
				textView.setPadding(0, mMiddleGap, 0, 0);
			}
			ViewBgUtil.setTextColor(textView, android.R.attr.state_selected, textColor);
			ViewBgUtil.setSelectorBg(tabIcon, android.R.attr.state_selected, new int[]{
					normalIconIds[i], selectedIconIds[i]});
			addView(childView, params);
		}
	}

	public void setCurrentItem(int index) {
		this.currentIndex = index;
		if (mListener != null && index < getChildCount()) {
			if (lastSelectedViewIndex != -1) {
				if (lastSelectedViewIndex != index) {
					getChildAt(lastSelectedViewIndex).setSelected(false);
					mListener.onItemUnSelected();
					if (lastSelectedViewIndex != index) {
						getChildAt(index).setSelected(true);
						mListener.onItemSelected(index);
					}
				}
			} else {
				getChildAt(index).setSelected(true);
				mListener.onItemSelected(index);
			}

			lastSelectedViewIndex = index;
		}
	}

	public int getCurrentIndex() {
		return lastSelectedViewIndex != -1 ? lastSelectedViewIndex : 0;
	}

	public void setOnItemStateListener(OnItemStateListener listener) {
		this.mListener = listener;
		if (currentIndex != -1) {
			setCurrentItem(currentIndex);
		}
		for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
			final int index = i;
			getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setCurrentItem(index);
				}
			});
		}
	}

	public interface OnItemStateListener {
		void onItemSelected(int position);
		void onItemUnSelected();
	}
}
