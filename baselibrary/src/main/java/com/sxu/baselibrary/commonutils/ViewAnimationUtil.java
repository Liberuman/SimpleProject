package com.sxu.baselibrary.commonutils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/*******************************************************************************
 * Description: View动画工具类
 *
 * Author: Freeman
 *
 * Date: 2017/06/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ViewAnimationUtil {

	private final static int DEFAULT_DURATION = 350;

	public static void translate(View view, int startX, int startY, int endX, int endY) {
		translate(view, startX, startY, endX, endY, DEFAULT_DURATION, new LinearInterpolator());
	}

	public static void translate(View view, int startX, int startY, int endX, int endY, int duration) {
		translate(view, startX, startY, endX, endY, duration, new LinearInterpolator());
	}

	public static void translate(View view, int startX, int startY, int endX, int endY, Interpolator interpolator) {
		translate(view, startX, startY, endX, endY, DEFAULT_DURATION, interpolator);
	}

	public static void translate(View view, int startX, int startY, int endX, int endY, int duration, Interpolator interpolator) {
		TranslateAnimation animation = new TranslateAnimation(startX, startY, endX, endY);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		view.startAnimation(animation);
	}

	public static void scale(View view, int fromX, int toX, int fromY, int toY) {
		scale(view, fromX, toX, fromY, toY, DEFAULT_DURATION, new LinearInterpolator());
	}

	public static void scale(View view, int fromX, int toX, int fromY, int toY, int duration) {
		scale(view, fromX, toX, fromY, toY, duration, new LinearInterpolator());
	}

	public static void scale(View view, int fromX, int toX, int fromY, int toY, Interpolator interpolator) {
		scale(view, fromX, toX, fromY, toY, DEFAULT_DURATION, interpolator);
	}

	public static void scale(View view, int fromX, int toX, int fromY, int toY, int duration, Interpolator interpolator) {
		ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		view.startAnimation(animation);
	}

	public static void alpha(View view, float fromAlpha, float toAlpha) {
		alpha(view, fromAlpha, toAlpha, DEFAULT_DURATION, new LinearInterpolator());
	}

	public static void alpha(View view, float fromAlpha, float toAlpha, int duration) {
		alpha(view, fromAlpha, toAlpha, duration, new LinearInterpolator());
	}

	public static void alpha(View view, float fromAlpha, float toAlpha, Interpolator interpolator) {
		alpha(view, fromAlpha, toAlpha, DEFAULT_DURATION, interpolator);
	}

	public static void alpha(View view, float fromAlpha, float toAlpha, int duration, Interpolator interpolator) {
		AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		view.startAnimation(animation);
	}

	public static void rotate(View view, float fromDegree, float toDegree) {
		rotate(view, fromDegree, toDegree, DEFAULT_DURATION, new LinearInterpolator());
	}

	public static void rotate(View view, float fromDegree, float toDegree, int duration) {
		rotate(view, fromDegree, toDegree, duration, new LinearInterpolator());
	}

	public static void rotate(View view, float fromDegree, float toDegree, Interpolator interpolator) {
		rotate(view, fromDegree, toDegree, DEFAULT_DURATION, interpolator);
	}

	public static  void rotate(View view, float fromDegree, float toDegree, int duration, Interpolator interpolator) {
		RotateAnimation animation = new RotateAnimation(fromDegree, toDegree);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		view.startAnimation(animation);
	}

	public static Animation getFadeInAnimation(int duration) {
		AlphaAnimation animation = new AlphaAnimation(0, 1);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		return animation;
	}

	public static Animation getFadeOutAnimation(int duration) {
		AlphaAnimation animation = new AlphaAnimation(1, 0);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		return animation;
	}

	public static Animation slidingToBottomAnimation(int duration) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0,
				DisplayUtil.getScreenHeight());
		animation.setDuration(duration);
		animation.setFillAfter(true);
		return animation;
	}
}

