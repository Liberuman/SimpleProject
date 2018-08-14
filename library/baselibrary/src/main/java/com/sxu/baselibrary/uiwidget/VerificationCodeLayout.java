package com.sxu.baselibrary.uiwidget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AndroidException;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sxu.baselibrary.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.InputMethodUtil;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

import static android.graphics.Typeface.BOLD;

/*******************************************************************************
 * Description: 验证码输入框
 *
 * Author: Freeman
 *
 * Date: 2018/8/14
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class VerificationCodeLayout extends LinearLayout {

	private int itemWidth;
	private int itemHeight;
	private int itemCount;
	private int itemGap;
	private int itemDefaultGap;
	private int itemDefaultGapColor;
	private int itemBgColor;
	private int itemBorderWidth;
	private int itemBorderColor;
	private int itemRadius;
	private int textSize;
	private int textColor;
	private boolean textIsBold;
	private boolean isPassword;

	private OnInputListener listener;

	public VerificationCodeLayout(Context context) {
		this(context, null, 0);
	}

	public VerificationCodeLayout(Context context, @Nullable AttributeSet attrs) {
		this(context, null, 0);
	}

	public VerificationCodeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.bl_VerificationCodeLayout, defStyleAttr, 0);
		itemWidth = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemWidth, DisplayUtil.dpToPx(45));
		itemHeight = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemHeight, DisplayUtil.dpToPx(45));
		itemCount = typedArray.getInt(R.styleable.bl_VerificationCodeLayout_bl_itemCount, 6);
		itemGap = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemGap, 0);
		itemDefaultGap = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemDefaultGap, 2);
		itemDefaultGapColor = typedArray.getColor(R.styleable.bl_VerificationCodeLayout_bl_itemDefaultGapColor, Color.GRAY);
		itemBgColor = typedArray.getColor(R.styleable.bl_VerificationCodeLayout_bl_itemBgColor, Color.TRANSPARENT);
		itemBorderWidth = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemBorderWidth, DisplayUtil.dpToPx(1));
		itemBorderColor = typedArray.getColor(R.styleable.bl_VerificationCodeLayout_bl_itemBorderColor, Color.GRAY);
		itemRadius = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemRadius, DisplayUtil.dpToPx(4));
		textSize = typedArray.getDimensionPixelSize(R.styleable.bl_VerificationCodeLayout_bl_itemTextSize, 18);
		textColor = typedArray.getColor(R.styleable.bl_VerificationCodeLayout_bl_itemTextColor, Color.BLACK);
		textIsBold = typedArray.getBoolean(R.styleable.bl_VerificationCodeLayout_bl_itemTextIsBold, true);
		isPassword = typedArray.getBoolean(R.styleable.bl_VerificationCodeLayout_bl_isPassword, false);
		typedArray.recycle();
		initView();
	}

	private void initView() {
		setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(HORIZONTAL);
		setShowDividers(SHOW_DIVIDER_MIDDLE);
		Drawable divider = createDivider();
		setDividerDrawable(divider);

		Drawable itemBg = null;
		if (itemGap == 0) {
			ViewBgUtil.setShapeBg(this, itemBgColor, itemBorderColor, itemBorderWidth, itemRadius);
		} else {
			itemBg = ViewBgUtil.getDrawable(itemBgColor, itemBorderColor, itemBorderWidth, itemRadius);
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
		for (int i = 0; i < itemCount; i++) {
			final PasteEditText itemEdit = new PasteEditText(getContext());
			itemEdit.setGravity(Gravity.CENTER);
			itemEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
			itemEdit.setTextSize(textSize);
			itemEdit.setTextColor(textColor);
			if (textIsBold) {
				itemEdit.setTypeface(Typeface.DEFAULT_BOLD);
			}
			if (itemBg != null) {
				itemEdit.setBackground(itemBg);
			} else {
				itemEdit.setBackgroundColor(Color.TRANSPARENT);
			}
			if (isPassword) {
				itemEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
			} else {
				itemEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
			addView(itemEdit, params);

			itemEdit.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s.length() == 1) {
						View nextView = getChildAt(indexOfChild(itemEdit) + 1);
						if (nextView != null) {
							nextView.setFocusable(true);
							nextView.requestFocus();
						} else {
							if (listener != null) {
								listener.onInputComplete();
							}
						}
					}
				}
			});

			itemEdit.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
						itemEdit.setText(null);
						EditText nextView = (EditText) getChildAt(indexOfChild(itemEdit) - 1);
						if (nextView != null) {
							nextView.requestFocus();
							nextView.setSelection(nextView.getText().length());
						}
					}
					return false;
				}
			});

			itemEdit.setOnPasteListener(new PasteEditText.OnPasteListener() {
				@Override
				public void onPaste(String content) {
					setText(content);
				}
			});
		}
	}

	private Drawable createDivider() {
		Drawable divider;
		if (itemGap == 0) {
			divider = new ColorDrawable(itemDefaultGapColor) {
				@Override
				public int getIntrinsicWidth() {
					return itemDefaultGap;
				}
			};
		} else {
			divider = new ColorDrawable() {
				@Override
				public int getIntrinsicWidth() {
					return itemGap;
				}
			};
		}

		return divider;
	}

	@Override
	public void setOrientation(int orientation) {
		if (orientation == HORIZONTAL) {
			super.setOrientation(orientation);
		}
	}

	public void clearText() {
		if (itemCount <= 0) {
			return;
		}

		for (int i = 0; i < itemCount; i++) {
			((EditText)getChildAt(i)).setText("");
		}
		(getChildAt(0)).requestFocus();
	}

	private void setText(String text) {
		if (TextUtils.isEmpty(text) || text.length() != itemCount) {
			return;
		}

		for (int i = 0; i < itemCount; i++) {
			((EditText)getChildAt(i)).setText(text.substring(i, i+1));
		}
	}

	public void setOnInputListener(OnInputListener listener) {
		this.listener = listener;
	}

	@Nullable
	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable parcelable =  super.onSaveInstanceState();
		SaveState saveState = new SaveState(parcelable);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < itemCount; i++) {
			builder.append(((EditText)getChildAt(i)).getText().toString());
		}
		saveState.setContent(builder.toString());
		return saveState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		SaveState saveState = (SaveState) state;
		super.onRestoreInstanceState(((SaveState) state).getSuperState());
		setText(saveState.getContent());
	}

	public interface OnInputListener {
		void onInputComplete();
	}

	public static class PasteEditText extends android.support.v7.widget.AppCompatEditText {

		private OnPasteListener listener;

		public PasteEditText(Context context) {
			super(context);
		}

		public PasteEditText(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public PasteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
		}

		@Override
		public boolean onTextContextMenuItem(int id) {
			if (id == android.R.id.paste) {
				ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clipData = cm.getPrimaryClip();
				if (clipData != null && clipData.getItemCount() > 0) {
					String content =  clipData.getItemAt(0).coerceToText(getContext()).toString();
					if (listener != null) {
						listener.onPaste(content);
					}
				}
			}

			return super.onTextContextMenuItem(id);
		}

		public void setOnPasteListener(OnPasteListener listener) {
			this.listener = listener;
		}

		@Override
		public Parcelable onSaveInstanceState() {
			return super.onSaveInstanceState();
		}

		@Override
		public void onRestoreInstanceState(Parcelable state) {
			super.onRestoreInstanceState(state);
		}

		public interface OnPasteListener {
			void onPaste(String content);
		}
	}

	public static class SaveState extends BaseSavedState {

		private String content;

		public SaveState(Parcelable state) {
			super(state);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeString(content);
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}
	}
}
