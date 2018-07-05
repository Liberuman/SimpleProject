package com.sxu.baselibrary.commonutils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

/*******************************************************************************
 * Description: 设置富文本样式
 *
 * Author: Freeman
 *
 * Date: 2018/5/2
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class SpanStyleUtil {

	private int startPos;
	private int len;
	private SpannableString span;

	/**
	 * 设置文字大小
	 */
	public static final int SPAN_TYPE_TEXT_SIZE = 1;
	/**
	 * 设置文字颜色
	 */
	public static final int SPAN_TYPE_TEXT_COLOR = 2;
	/**
	 * 设置文字样式：取值为：Typeface.BOLD(加粗)， Typeface.ITALIC(斜体)， Typeface.BOLD_ITALIC(加粗+斜体)
	 */
	public static final int SPAN_TYPE_TEXT_STYLE = 3;
	/**
	 * 为文字添加下划线
	 */
	public static final int SPAN_TYPE_STRIKE_THROUGH = 4;

	public SpannableString createSpan(String text) {
		span = new SpannableString(text);
		return span;
	}

	public SpannableString createSpan(String text, int startPos, int len) {
		this.startPos = startPos;
		this.len = len;
		span = new SpannableString(text);
		return span;
	}

	public SpannableString setStyle(int style, int value) {
		return setStyle(style, value, startPos, len);
	}

	public SpannableString setStyle(int style, int value, int startPos, int len) {
		Object object;
		switch (style) {
			case SPAN_TYPE_TEXT_SIZE:
				object = new AbsoluteSizeSpan(value);
				break;
			case SPAN_TYPE_TEXT_COLOR:
				object = new ForegroundColorSpan(value);
				break;
			case SPAN_TYPE_TEXT_STYLE:
				object = new StyleSpan(value);
				break;
			case SPAN_TYPE_STRIKE_THROUGH:
				object = new StrikethroughSpan();
				break;
			default:
				throw new IllegalArgumentException("The style is not supported");
		}
		span.setSpan(object, startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	public void apply(TextView textView) {
		textView.setText(span);
	}
}
