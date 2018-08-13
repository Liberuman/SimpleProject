package com.sxu.baselibrary.uiwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sxu.baselibrary.R;
import com.sxu.baselibrary.commonutils.BitmapUtil;

/*******************************************************************************
 * Description: 可修改形状的ImageView
 *
 * Author: Freeman
 *
 * Date: 2018/7/11
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ShapeImageView extends AppCompatImageView {

	private int mBorderColor;
	private int mBorderWidth;
	private int mRadius;
	private int mShape;

	private final int SHAPE_TYPE_CIRCLE = 1;
	private final int SHAPE_TYPE_ROUND = 2;

	public ShapeImageView(Context context) {
		this(context, null, 0);
	}

	public ShapeImageView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ShapeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.bl_CircleImage);
		mBorderColor = typedArray.getColor(R.styleable.bl_ShapeImageView_bl_borderColor, Color.TRANSPARENT);
		mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.bl_ShapeImageView_bl_borderWidth, 0);
		mRadius = typedArray.getDimensionPixelSize(R.styleable.bl_ShapeImageView_bl_radius, 0);
		mShape = typedArray.getInt(R.styleable.bl_ShapeImageView_bl_shape, SHAPE_TYPE_ROUND);
		typedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			super.onDraw(canvas);
		}

		Bitmap bitmap = BitmapUtil.drawableToBitmap(drawable);
		BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(bitmapShader);
		RectF rect = new RectF(0, 0, getWidth(), getHeight());
		int radius = Math.min(getWidth(), getHeight()) / 2;
		if (mShape == SHAPE_TYPE_CIRCLE) {
			canvas.drawCircle(radius, radius, radius, paint);
		} else if (mShape == SHAPE_TYPE_ROUND) {
			canvas.drawRoundRect(rect, mRadius, mRadius, paint);
		}
	}
}
