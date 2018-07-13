package com.sxu.basecomponent.uiwidget;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.ViewAnimationUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

/*******************************************************************************
 * Description: 通用的广告弹框
 *
 * Author: Freeman
 *
 * Date: 2018/3/6
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class CommonAdDialog extends AlertDialog {
	private ImageView adImage;
	private TextView button;
	private View closeIcon;
	private View rootLayout;
	private View contentLayout;

	private int imageResId;
	private boolean isDismissing = false;
	private final int MAX_ALPHA = 153;

	public CommonAdDialog(Context context, @DrawableRes int imageResId) {
		super(context, R.style.CommonAdDialog);
		this.imageResId = imageResId;
	}

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.dialog_common_ad_layout);
		setCanceledOnTouchOutside(false);

		adImage = findViewById(R.id.ad_image);
		rootLayout = findViewById(R.id.root_layout);
		contentLayout = findViewById(R.id.content_layout);
		button = findViewById(R.id.button);
		closeIcon = findViewById(R.id.close_icon);
		adImage.setImageResource(imageResId);

		ViewBgUtil.setShapeBg(button, GradientDrawable.RECTANGLE, GradientDrawable.Orientation.LEFT_RIGHT,
				new int[] {
						getContext().getResources().getColor(R.color.z2),
						getContext().getResources().getColor(R.color.z3)
				},
				DisplayUtil.dpToPx(25));
		ViewBgUtil.setShapeBg(contentLayout, GradientDrawable.RECTANGLE, Color.WHITE, DisplayUtil.dpToPx(12));
		// 设置背景渐现动画
		ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(300);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				rootLayout.setBackgroundColor(Color.argb((int) (MAX_ALPHA * (float)animation.getAnimatedValue()),
						0, 0, 0));
			}
		});
		animator.start();
		// 设置内容区域的弹性动画
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			contentLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.sliding_from_bottom_with_jitter_anim));
		} else {
			SpringForce spring = new SpringForce(0)
					.setDampingRatio(0.70f)
					.setStiffness(SpringForce.STIFFNESS_LOW);
			final SpringAnimation anim = new SpringAnimation(contentLayout, SpringAnimation.TRANSLATION_Y).setSpring(spring);
			anim.setStartValue(1000);
			anim.start();
		}

		setButtonOnClickListener(null, null);
		closeIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setButtonOnClickListener(String text, final View.OnClickListener listener) {
		if (!TextUtils.isEmpty(text)) {
			button.setText(text);
			closeIcon.setVisibility(View.VISIBLE);
		}
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onClick(v);
				}
				dismiss();
			}
		});
	}

	@Override
	public void dismiss() {
		if (!isDismissing) {
			isDismissing = true;
			if (rootLayout != null) {
				ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
				animator.setDuration(300);
				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float alpha = (float) animation.getAnimatedValue();
						rootLayout.setBackgroundColor(Color.argb((int) (MAX_ALPHA * alpha),
								0, 0, 0));
						if (alpha <= 0) {
							CommonAdDialog.super.dismiss();
						}
					}
				});
				animator.start();
			}

			if (contentLayout != null) {
				contentLayout.setAnimation(ViewAnimationUtil.slidingToBottomAnimation(300));
				contentLayout.setVisibility(View.GONE);
			}
		}
	}
}
