package com.sxu.basecomponent.uiwidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sxu.basecomponent.R;

/*******************************************************************************
 * Description: 统计字数的EditText
 *
 * Author: Freeman
 *
 * Date: 2018/8/14
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class StatisticsEditText extends FrameLayout {

	private EditText editText;
	private TextView countTipsText;
	private View contentView;

	private int minCount = 0;
	private int maxCount = Integer.MAX_VALUE;
	private int maxVisibleLineCount = 1;
	private OnTextChangedListener listener;

	public StatisticsEditText(Context context) {
		this(context, null);
	}

	public StatisticsEditText(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		contentView = View.inflate(getContext(), R.layout.view_statistics_edit_text_layout, this);
		editText = contentView.findViewById(R.id.edit_text);
		countTipsText = contentView.findViewById(R.id.tips_text);

		final StringBuilder builder = new StringBuilder();
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				builder.setLength(0);
				if (minCount != 0) {
					if (s.length() > 0 && s.length() < minCount) {
						countTipsText.setText(builder.append("少于 ").append(minCount).append(" 字"));
						countTipsText.setTextColor(ContextCompat.getColor(getContext(), R.color.t3));
					} else if (s.length() == 0 || (s.length() >= minCount && s.length() < maxCount)){
						countTipsText.setText(builder.append("不少于 ").append(minCount)
								.append(" 字，不超过 ").append(maxCount).append(" 字"));
						countTipsText.setTextColor(ContextCompat.getColor(getContext(), R.color.g2));
					} else {
						countTipsText.setText(builder.append("超过 ").append(maxCount).append(" 字"));
						countTipsText.setTextColor(ContextCompat.getColor(getContext(), R.color.t3));
					}
				} else {
					if (s.length() >= maxCount) {
						countTipsText.setText(builder.append("超过 ").append(maxCount).append(" 字"));
						countTipsText.setTextColor(ContextCompat.getColor(getContext(), R.color.t3));
					} else {
						countTipsText.setText(builder.append("不超过 ").append(maxCount).append(" 字"));
						countTipsText.setTextColor(ContextCompat.getColor(getContext(), R.color.g2));
					}
				}

				if (listener != null) {
					listener.onTextChanged(s.toString());
				}
			}
		});

		editText.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (editText.getLineCount() > maxVisibleLineCount) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							v.getParent().requestDisallowInterceptTouchEvent(true);
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							v.getParent().requestDisallowInterceptTouchEvent(false);
							break;
					}
				}

				return editText.onTouchEvent(event);
			}
		});
	}

	public void setEditHint(String hintText) {
		editText.setHint(hintText);
	}

	public void setTextCountRange(int maxCount) {
		setTextCountRange(0, maxCount);
	}

	public void setTextCountRange(int minCount, int maxCount) {
		this.minCount = minCount;
		this.maxCount = maxCount;
		editText.setMinEms(minCount);
		editText.setMaxEms(maxCount);
		StringBuilder builder = new StringBuilder();
		if (minCount != 0) {
			countTipsText.setText(builder.append("不少于 ").append(minCount)
					.append(" 字，不超过 ").append(maxCount).append(" 字"));
		} else {
			countTipsText.setText(builder.append("不超过 ").append(maxCount).append(" 字"));
		}
	}

	public EditText getEditText() {
		return editText;
	}

	public String getText() {
		return editText.getText().toString();
	}

	public void setText(String text) {
		editText.setText(text);
	}

	public void setOnTextChangedListener(OnTextChangedListener listener) {
		this.listener = listener;
	}

	public void setMaxVisibleLineCount(int maxLineCount) {
		this.maxVisibleLineCount = maxLineCount;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode == MeasureSpec.EXACTLY) {
			int heightSize = MeasureSpec.getSize(heightMeasureSpec);
			contentView.setMinimumHeight(heightSize);
		}
	}

	public interface OnTextChangedListener {
		void onTextChanged(String value);
	}
}
