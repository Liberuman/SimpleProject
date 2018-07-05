package com.sxu.baselibrary.commonutils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

/**
 * Created by Freeman on 17/4/1.
 */

public class SpannableStringUtil {

	public static final int SPAN_TYPE_TEXT_SIZE = 1;
	public static final int SPAN_TYPE_TEXT_COLOR = 2;
	public static final int SPAN_TYPE_TEXT_STYLE = 3;
	public static final int SPAN_TYPE_STRIKETHROUGH = 4;
	
	/**
	 * 设置单个位置的文字样式
	 * @param textView
	 * @param text
	 * @param type      要设置的类型：文字大小，文字颜色，文字样式，添加删除线
	 * @param value     设置的类型不同传不同的值, 可表示文字大小，文字颜色，文字样式等
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static void setText(TextView textView, StringBuilder text, int type, int value, int startPos, int len) {
		switch (type) {
			case SPAN_TYPE_TEXT_SIZE:
				textView.setText(getTextSizeSpannable(text, value, startPos, len));
				break;
			case SPAN_TYPE_TEXT_COLOR:
				textView.setText(getColorSpannable(text, value, startPos, len));
				break;
			case SPAN_TYPE_TEXT_STYLE:
				textView.setText(getStyleSpannable(text, value, startPos, len));
				break;
			case SPAN_TYPE_STRIKETHROUGH:
				textView.setText(getStrikethroughSpannable(text, startPos, len));
				break;
			default:
				break;
		}
	}

	/**
	 * 设置多个位置的文字样式
	 * @param textView
	 * @param text
	 * @param type      要设置的类型：文字大小，文字颜色，文字样式，添加删除线
	 * @param value     设置的类型不同传不同的值 
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static void setText(TextView textView, StringBuilder text, int type, int value, int[] startPos, int[] len) {
		switch (type) {
			case SPAN_TYPE_TEXT_SIZE:
				textView.setText(getTextSizeSpannable(text, value, startPos, len));
				break;
			case SPAN_TYPE_TEXT_COLOR:
				textView.setText(getColorSpannable(text, value, startPos, len));
				break;
			case SPAN_TYPE_TEXT_STYLE:
				textView.setText(getStyleSpannable(text, value, startPos, len));
				break;
			case SPAN_TYPE_STRIKETHROUGH:
				textView.setText(getStrikethroughSpannable(text, startPos, len));
				break;
			default:
				break;
		}
	}

	public static SpannableString getColorSpannable(SpannableString span, int textColor, int startPos, int len) {
		span.setSpan(new ForegroundColorSpan(textColor), startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	/**
	 * 设置单个位置的文字颜色
	 * @param text
	 * @param textColor
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getColorSpannable(StringBuilder text, int textColor, int startPos, int len) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new ForegroundColorSpan(textColor), startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	/**
	 * 设置多个位置的文字颜色
	 * @param text
	 * @param textColor
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getColorSpannable(StringBuilder text, int textColor, int[] startPos, int[] len) {
		SpannableString span = new SpannableString(text);
		if (startPos != null && len != null && startPos.length > 0 && len.length > 0 && startPos.length == len.length) {
			for (int i = 0; i < startPos.length; i++) {
				span.setSpan(new ForegroundColorSpan(textColor), startPos[i], startPos[i]+len[i], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}
		}

		return span;
	}

	/**
	 * 设置单个位置的文字样式（加粗，斜体）
	 * @param text
	 * @param textStyle Typeface.BOLD(加粗)， Typeface.ITALIC(斜体)， Typeface.BOLD_ITALIC(加粗+斜体)
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getStyleSpannable(StringBuilder text, int textStyle, int startPos, int len) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new StyleSpan(textStyle), startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	/**
	 * 设置多个位置的文字样式（加粗，斜体）
	 * @param text
	 * @param textStyle Typeface.BOLD(加粗)， Typeface.ITALIC(斜体)， Typeface.BOLD_ITALIC(加粗+斜体)
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getStyleSpannable(StringBuilder text, int textStyle, int[] startPos, int[] len) {
		SpannableString span = new SpannableString(text);
		if (startPos != null && len != null && startPos.length > 0 && len.length > 0 && startPos.length == len.length) {
			for (int i = 0; i < startPos.length; i++) {
				span.setSpan(new StyleSpan(textStyle), startPos[i], startPos[i]+len[i], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}
		}

		return span;
	}


	/**
	 * 设置单个位置的文字大小
	 * @param text
	 * @param textSize Typeface.BOLD(加粗)， Typeface.ITALIC(斜体)， Typeface.BOLD_ITALIC(加粗+斜体)
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getTextSizeSpannable(StringBuilder text, int textSize, int startPos, int len) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new AbsoluteSizeSpan(textSize), startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	/**
	 * 设置单个位置的文字大小
	 * @param span
	 * @param textSize Typeface.BOLD(加粗)， Typeface.ITALIC(斜体)， Typeface.BOLD_ITALIC(加粗+斜体)
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getTextSizeSpannable(SpannableString span, int textSize, int startPos, int len) {
		span.setSpan(new AbsoluteSizeSpan(textSize), startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	/**
	 * 设置多个位置的文字大小
	 * @param text
	 * @param textSize
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getTextSizeSpannable(StringBuilder text, int textSize, int[] startPos, int[] len) {
		SpannableString span = new SpannableString(text);
		if (startPos != null && len != null && startPos.length > 0 && len.length > 0 && startPos.length == len.length) {
			for (int i = 0; i < startPos.length; i++) {
				span.setSpan(new AbsoluteSizeSpan(textSize), startPos[i], startPos[i]+len[i], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}
		}

		return span;
	}


	/**
	 * 为单个位置的文字添加删除线
	 * @param text
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getStrikethroughSpannable(StringBuilder text, int startPos, int len) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new StrikethroughSpan(), startPos, startPos+len, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return span;
	}

	/**
	 * 为多个位置的文字添加删除线
	 * @param text
	 * @param startPos
	 * @param len
	 * @return
	 */
	public static SpannableString getStrikethroughSpannable(StringBuilder text, int[] startPos, int[] len) {
		SpannableString span = new SpannableString(text);
		if (startPos != null && len != null && startPos.length > 0 && len.length > 0 && startPos.length == len.length) {
			for (int i = 0; i < startPos.length; i++) {
				span.setSpan(new StrikethroughSpan(), startPos[i], startPos[i]+len[i], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}
		}

		return span;
	}

	public static void setUnderlineSpan(TextView textView, String text, int startPos, int endPos) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new UnderlineSpan(), startPos, endPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		textView.setText(span);
	}
}
