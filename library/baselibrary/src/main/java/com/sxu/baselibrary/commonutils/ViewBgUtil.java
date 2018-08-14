package com.sxu.baselibrary.commonutils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

/*******************************************************************************
 * Description: 为View设置背景
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ViewBgUtil {

	/**
	 * 圆角相同的纯色背景
	 * @param view
	 * @param bgColor
	 * @param radius
	 */
	public static void setShapeBg(View view, int bgColor, int radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, bgColor, radius));
	}

	public static void setShapeBg(View view, int bgColor, int borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, bgColor, borderColor, borderWidth, radius));
	}

	public static void setShapeBg(View view, int shape, int bgColor, int radius) {
		ViewCompat.setBackground(view, getDrawable(shape, bgColor, radius));
	}

	public static void setShapeBg(View view, int shape, int bgColor, int borderColor,
	                              int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(shape, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 圆角不同的纯色背景
	 * @param view
	 * @param bgColor
	 * @param radius
	 */
	public static void setShapeBg(View view, int bgColor, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, bgColor, radius));
	}

	public static void setShapeBg(View view, int bgColor, int borderColor,
	                              int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, bgColor, borderColor, borderWidth, radius));
	}

	public static void setShapeBg(View view, int shape, int bgColor, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(shape, bgColor, radius));
	}

	public static void setShapeBg(View view, int shape, int bgColor, int borderColor,
	                              int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(shape, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 圆角相同的渐变背景
	 * @param view
	 * @param orientation
	 * @param bgColor
	 * @param radius
	 */
	public static void setShapeBg(View view, GradientDrawable.Orientation orientation,
	                              int[] bgColor, int radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, orientation, bgColor,
				0, 0, radius));
	}

	public static void setShapeBg(View view, GradientDrawable.Orientation orientation,
	                              int[] bgColor, int borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, orientation, bgColor,
				borderColor, borderWidth, radius));
	}

	public static void setShapeBg(View view, int shape, GradientDrawable.Orientation orientation,
	                              int[] bgColor, int radius) {
		ViewCompat.setBackground(view, getDrawable(shape, orientation, bgColor, 0, 0, radius));
	}

	public static void setShapeBg(View view, int shape, GradientDrawable.Orientation orientation,
	                              int[] bgColor, int borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(shape, orientation, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 圆角不同的渐变背景
	 * @param view
	 * @param orientation
	 * @param bgColor
	 * @param radius
	 */
	public static void setShapeBg(View view, GradientDrawable.Orientation orientation,
	                              int[] bgColor, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, orientation, bgColor,
				0, 0, radius));
	}

	public static void setShapeBg(View view, GradientDrawable.Orientation orientation,
	                              int[] bgColor, int borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(GradientDrawable.RECTANGLE, orientation, bgColor,
				borderColor, borderWidth, radius));
	}

	public static void setShapeBg(View view, int shape, GradientDrawable.Orientation orientation,
	                              int[] bgColor, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(shape, orientation, bgColor, 0, 0, radius));
	}

	public static void setShapeBg(View view, int shape, GradientDrawable.Orientation orientation,
	                              int[] bgColor, int borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(shape, orientation, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 设置与状态关联的背景 -> 有相同圆角的纯色背景
	 * @param view
	 * @param state
	 * @param bgColor
	 * @param radius
	 */
	public static void setSelectorBg(View view, int state, int[] bgColor, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, bgColor, radius));
	}

	public static void setSelectorBg(View view, int state, int[] bgColor,
	                                 int[] borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, bgColor,
				borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, int[] bgColor, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, bgColor, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, int[] bgColor,
	                                 int[] borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 设置与状态关联的背景 -> 有不同圆角的纯色背景
	 * @param view
	 * @param state
	 * @param bgColor
	 * @param radius
	 */
	public static void setSelectorBg(View view, int state, int[] bgColor, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, bgColor, radius));
	}

	public static void setSelectorBg(View view, int state, int[] bgColor,
	                                 int[] borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, bgColor,
				borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, int[] bgColor, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, bgColor, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, int[] bgColor,
	                                 int[] borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 设置与状态关联的背景 -> 有相同圆角的渐变背景
	 * @param view
	 * @param state
	 * @param orientation
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 */
	public static void setSelectorBg(View view, int state, GradientDrawable.Orientation orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, orientation, bgColor,
				borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, GradientDrawable.Orientation[] orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, orientation, bgColor,
				borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, GradientDrawable.Orientation orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, orientation, bgColor, borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, GradientDrawable.Orientation[] orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, int radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, orientation, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 设置与状态关联的背景 -> 有不同圆角的渐变背景
	 * @param view
	 * @param state
	 * @param orientation
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 */
	public static void setSelectorBg(View view, int state, GradientDrawable.Orientation orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, orientation,
				bgColor, borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, GradientDrawable.Orientation[] orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, GradientDrawable.RECTANGLE, orientation,
				bgColor, borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, GradientDrawable.Orientation orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, orientation, bgColor, borderColor, borderWidth, radius));
	}

	public static void setSelectorBg(View view, int state, int shape, GradientDrawable.Orientation[] orientation,
	                                 int[][] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		ViewCompat.setBackground(view, getDrawable(state, shape, orientation, bgColor, borderColor, borderWidth, radius));
	}

	/**
	 * 根据View状态设置不同的背景
	 * @param view
	 * @param state
	 * @param resId 可为color, drawable
	 */
	public static void setSelectorBg(View view, int state, int[] resId) {
		ViewCompat.setBackground(view, getDrawable(view.getContext(), state, resId));
	}

	/**
	 * 获取通过资源文件构造的背景
	 * @param context
	 * @param state
	 * @param resId
	 * @return
	 */
	public static Drawable getDrawable(Context context, int state, @DrawableRes int[] resId) {
		if (resId == null || resId.length < 2) {
			throw new IllegalArgumentException();
		}
		if (context != null && context.getResources() != null) {
			StateListDrawable drawable = new StateListDrawable();
			drawable.addState(new int[]{state}, context.getResources().getDrawable(resId[1]));
			drawable.addState(new int[]{}, context.getResources().getDrawable(resId[0]));
			return drawable;
		}

		return null;
	}

	public static Drawable getDrawable(int bgColor, int radius) {
		return getDrawable(GradientDrawable.RECTANGLE, bgColor, 0, 0, radius);
	}

	public static Drawable getDrawable(int shape, int bgColor, int radius) {
		return getDrawable(shape, bgColor, 0, 0, radius);
	}

	public static Drawable getDrawable(int bgColor, float[] radius) {
		return getDrawable(GradientDrawable.RECTANGLE, bgColor, 0, 0, radius);
	}

	public static Drawable getDrawable(int shape, int bgColor, float[] radius) {
		return getDrawable(shape, bgColor, 0, 0, radius);
	}

	public static Drawable getDrawable(int state, int shape, int[] bgColor, int radius) {
		return getDrawable(state, shape, bgColor, new int[2], 0, radius);
	}

	public static Drawable getDrawable(int state, int shape, int[] bgColor, float[] radius) {
		return getDrawable(state, shape, bgColor, new int[2], 0, radius);
	}

	public static Drawable getDrawable(int bgColor, int borderColor,
	                                   int borderWidth, int radius) {
		return getDrawable(GradientDrawable.RECTANGLE, bgColor, borderColor, borderWidth, radius);
	}

	/**
	 * 获取构造的纯色背景 -> 具有相同圆角的场景
	 * @param shape 表示背景形状，取值为：
	 *      GradientDrawable.RECTANGLE: 表示矩形
	 *      GradientDrawable.OVAL: 表示圆形
	 *      GradientDrawable.LINE: 表示线条
	 * @param bgColor 背景颜色
	 * @param borderColor 边框颜色
	 * @param borderWidth 边框宽度
	 * @param radius 圆角大小
	 * @return
	 */
	public static Drawable getDrawable(int shape, int bgColor, int borderColor,
	                                   int borderWidth, int radius) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(shape);
		drawable.setColor(bgColor);
		drawable.setStroke(borderWidth, borderColor);
		drawable.setCornerRadius(radius);
		return drawable;
	}

	/**
	 * 获取构造的纯色背景 -> 圆角有差异的场景
	 * @param shape
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius 圆角大小，是一个包含8个元素的数组，每2个表示一个圆角，顺序依次为左上，右上，右下，左下
	 * @return
	 */
	public static Drawable getDrawable(int shape, int bgColor, int borderColor,
	                                   int borderWidth, float[] radius) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(shape);
		drawable.setColor(bgColor);
		drawable.setStroke(borderWidth, borderColor);
		drawable.setCornerRadii(radius);
		return drawable;
	}

	/**
	 * 获取构造的渐变背景 -> 具有相同圆角的场景
	 * @param shape
	 * @param orientation
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 * @return
	 */
	public static Drawable getDrawable(int shape, GradientDrawable.Orientation orientation,
	                                   int[] bgColor, int borderColor, int borderWidth, int radius) {
		GradientDrawable drawable = new GradientDrawable(orientation, bgColor);
		drawable.setShape(shape);
		drawable.setStroke(borderWidth, borderColor);
		drawable.setCornerRadius(radius);
		return drawable;
	}

	/**
	 * 获取构造的渐变背景 -> 圆角有差异的场景
	 * @param shape
	 * @param orientation
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 * @return
	 */
	public static Drawable getDrawable(int shape, GradientDrawable.Orientation orientation,
	                                   int[] bgColor, int borderColor, int borderWidth, float[] radius) {
		GradientDrawable drawable = new GradientDrawable(orientation, bgColor);
		drawable.setShape(shape);
		drawable.setStroke(borderWidth, borderColor);
		drawable.setCornerRadii(radius);
		return drawable;
	}

	/**
	 * 获取构造的纯色背景 -> 与状态相关联的背景【有相同圆角】
	 * @param state 取值为：android.R.attr.*
	 *      public static final int state_above_anchor = 16842922;
	 *		public static final int state_accelerated = 16843547;
	 *		public static final int state_activated = 16843518;
	 *		public static final int state_active = 16842914;
	 *		public static final int state_checkable = 16842911;
	 *		public static final int state_checked = 16842912;
	 *		public static final int state_drag_can_accept = 16843624;
	 *		public static final int state_drag_hovered = 16843625;
	 *		public static final int state_empty = 16842921;
	 *		public static final int state_enabled = 16842910;
	 *		public static final int state_expanded = 16842920;
	 *		public static final int state_first = 16842916;
	 *		public static final int state_focused = 16842908;
	 *		public static final int state_hovered = 16843623;
	 *		public static final int state_last = 16842918;
	 *		public static final int state_long_pressable = 16843324;
	 *		public static final int state_middle = 16842917;
	 *		public static final int state_multiline = 16843597;
	 *		public static final int state_pressed = 16842919;
	 *		public static final int state_selected = 16842913;
	 *		public static final int state_single = 16842915;
	 *		public static final int state_window_focused = 16842909;
	 *
	 * @param shape
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 * @return
	 */
	public static Drawable getDrawable(int state, int shape, int[] bgColor, int[] borderColor, int borderWidth, int radius) {
		if (bgColor == null || bgColor.length < 2) {
			throw new IllegalArgumentException();
		}
		StateListDrawable drawable = new StateListDrawable();
		// addState第一个参数设置为new int[]{}或者new int[] {-state}表示默认状态
		drawable.addState(new int[] {state}, getDrawable(shape, bgColor[1], borderColor[1], borderWidth, radius));
		drawable.addState(new int[] {}, getDrawable(shape, bgColor[0], borderColor[0], borderWidth, radius));

		return drawable;
	}

	/**
	 * 获取构造的纯色背景 -> 与状态相关联的背景【圆角有差异的场景】
	 * @param state
	 * @param shape
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 * @return
	 */
	public static Drawable getDrawable(int state, int shape, int[] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		if (bgColor == null || bgColor.length < 2) {
			throw new IllegalArgumentException();
		}
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] {state}, getDrawable(shape, bgColor[1], borderColor[1], borderWidth, radius));
		drawable.addState(new int[] {}, getDrawable(shape, bgColor[0], borderColor[0], borderWidth, radius));

		return drawable;
	}

	/**
	 * 获取构造的渐变背景 -> 与状态相关联的背景【圆角相同的场景】
	 * @param state
	 * @param shape
	 * @param orientation
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 * @return
	 */
	public static Drawable getDrawable(int state, int shape, GradientDrawable.Orientation orientation,
	                                   int[][] bgColor, int[] borderColor, int borderWidth, int radius) {
		return getDrawable(state, shape, new GradientDrawable.Orientation[] {orientation, orientation},
				bgColor, borderColor, borderWidth, radius);
	}

	public static Drawable getDrawable(int state, int shape, GradientDrawable.Orientation[] orientation,
	                                   int[][] bgColor, int[] borderColor, int borderWidth, int radius) {
		if (bgColor == null || bgColor.length < 2) {
			throw new IllegalArgumentException();
		}
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] {state}, getDrawable(shape, orientation[1], bgColor[1], borderColor[1], borderWidth, radius));
		drawable.addState(new int[] {}, getDrawable(shape, orientation[0], bgColor[0], borderColor[0], borderWidth, radius));

		return drawable;
	}

	/**
	 * 获取构造的渐变背景 -> 与状态相关联的背景【圆角有差异的场景】
	 * @param state
	 * @param shape
	 * @param orientation
	 * @param bgColor
	 * @param borderColor
	 * @param borderWidth
	 * @param radius
	 * @return
	 */
	public static Drawable getDrawable(int state, int shape, GradientDrawable.Orientation orientation,
	                                   int[][] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		return getDrawable(state, shape, new GradientDrawable.Orientation[] {orientation, orientation},
				bgColor, borderColor, borderWidth, radius);
	}

	public static Drawable getDrawable(int state, int shape, GradientDrawable.Orientation orientation[],
	                                   int[][] bgColor, int[] borderColor, int borderWidth, float[] radius) {
		if (bgColor == null || bgColor.length < 2) {
			throw new IllegalArgumentException();
		}
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] {state}, getDrawable(shape, orientation[1], bgColor[1], borderColor[1], borderWidth, radius));
		drawable.addState(new int[] {}, getDrawable(shape, orientation[0], bgColor[0], borderColor[0], borderWidth, radius));

		return drawable;
	}

	/**
	 * @param textView
	 * @param state
	 * @param textColor textColor[0]表示状态为true时的颜色，textColor[1]表示正常状态下的颜色
	 */
	public static void setTextColor(TextView textView, int state, int[] textColor) {
		ColorStateList colorState = new ColorStateList(new int[][]{
				new int[] {state},
				new int[] {}
		}, textColor);
		textView.setTextColor(colorState);
	}

	/**
	 * 创建分割线
	 * @param color
	 * @param width
	 * @param height
	 * @return
	 */
	public static Drawable getDivider(int color, final int width, final int height) {
		return new ColorDrawable(color) {
			@Override
			public int getIntrinsicWidth() {
				return width != 0 ? width : super.getIntrinsicWidth();
			}

			@Override
			public int getIntrinsicHeight() {
				return height != 0 ? height : super.getIntrinsicHeight();
			}
		};
	}
}
