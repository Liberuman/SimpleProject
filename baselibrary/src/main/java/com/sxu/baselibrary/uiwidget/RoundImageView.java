package com.sxu.baselibrary.uiwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/*******************************************************************************
 * FileName: RoundImageView
 * <p/>
 * Description: 圆角图片
 * <p/>
 * Author: juhg
 * <p/>
 * Version: v1.0
 * <p/>
 * Date: 16/4/7
 * <p/>
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class RoundImageView extends ImageView {

    private float radius = 16;
    private Matrix drawMatrix;

    public RoundImageView(Context context) {
        super(context);
        //this.radius = radius;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap bitmap = drawableToBitmap(drawable);
            if (bitmap != null && !bitmap.isRecycled()) {
                BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                if (drawMatrix != null) {
                    bitmapShader.setLocalMatrix(drawMatrix);
                }
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(bitmapShader);
                RectF rect = new RectF(0, 0, getWidth(), getHeight());
                canvas.drawRoundRect(rect, radius, radius, paint);
            } else {
                super.onDraw(canvas);
            }
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        configureBounds(drawable);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    private void configureBounds(Drawable drawable) {
        drawMatrix = new Matrix();
        int dwidth = drawable.getIntrinsicWidth();
        int dheight = drawable.getIntrinsicHeight();
        int vwidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int vheight = getHeight() - getPaddingTop() - getPaddingBottom();

        boolean fits = (dwidth < 0 || vwidth == dwidth) &&
                (dheight < 0 || vheight == dheight);

        if (dwidth <= 0 || dheight <= 0 || ScaleType.FIT_XY == getScaleType()) {
            drawable.setBounds(0, 0, vwidth, vheight);
            drawMatrix = null;
        } else {
            drawable.setBounds(0, 0, dwidth, dheight);
            if (ScaleType.MATRIX == getScaleType()) {
                // Use the specified matrix as-is.
                if (getMatrix().isIdentity()) {
                    drawMatrix = null;
                } else {
                    drawMatrix.set(getMatrix());
                }
            } else if (fits) {
                // The bitmap fits exactly, no transform needed.
                drawMatrix = null;
            } else if (ScaleType.CENTER == getScaleType()) {
                // Center bitmap in view, no scaling.
                drawMatrix.set(getMatrix());
                drawMatrix.setTranslate((int) ((vwidth - dwidth) * 0.5f + 0.5f),
                        (int) ((vheight - dheight) * 0.5f + 0.5f));
            } else if (ScaleType.CENTER_CROP == getScaleType()) {
                drawMatrix.set(getMatrix());

                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }
                drawMatrix.setScale(scale, scale);
                drawMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
            } else if (ScaleType.CENTER_INSIDE == getScaleType()) {
                drawMatrix.set(getMatrix());
                float scale;
                float dx;
                float dy;

                if (dwidth <= vwidth && dheight <= vheight) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) vwidth / (float) dwidth,
                            (float) vheight / (float) dheight);
                }

                dx = (int) ((vwidth - dwidth * scale) * 0.5f + 0.5f);
                dy = (int) ((vheight - dheight * scale) * 0.5f + 0.5f);

                drawMatrix.setScale(scale, scale);
                drawMatrix.postTranslate(dx, dy);
            } else {
                /**
                 * Nothing
                 */
            }
        }
    }
}
