package com.sxu.baselibrary.uiwidget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sxu.baselibrary.R;

/*******************************************************************************
 * Description: 高仿UBer的等级选择控件
 *
 * Author: Freeman
 *
 * Date: 2018/7/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class LevelView extends View {

    private int stepCount;
    private int defaultPos;
    private int scrollDuration;
    private Bitmap thumb;
    private float thumbWidth;
    private float thumbHeight;
    private float lineHeight;
    private int lineColor;
    private float pointRadius;
    private int pointColor;

    private float stepLength;
    private float startPos;
    private float startX;
    private float offset = 0;
    private float scrollLeft;   // the left limit of thumb scrolling
    private float scrollRight;  // the right limit of thumb scrolling
    private int oldPosition = 0;
    private boolean canScroll;
    private OnLevelChangeListener listener;

    /**
     * the default range of clicked
     */
    private final int DEFAULT_CLICK_RANGE = 45;

    public LevelView(Context context) {
        super(context);
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.bl_LevelLayout);
        thumb = drawableToBitmap(array.getDrawable(R.styleable.bl_LevelLayout_bl_thumb));
        thumbWidth = array.getDimension(R.styleable.bl_LevelLayout_bl_thumbWidth, 0);
        thumbHeight = array.getDimension(R.styleable.bl_LevelLayout_bl_thumbHeight, 0);
        stepCount = array.getInteger(R.styleable.bl_LevelLayout_bl_stepCount, 3);
        defaultPos = array.getInteger(R.styleable.bl_LevelLayout_bl_defaultPos, 0);
        scrollDuration = array.getInteger(R.styleable.bl_LevelLayout_bl_scrollDuration, 400);
        lineHeight = array.getDimension(R.styleable.bl_LevelLayout_bl_lineHeight, 6);
        lineColor = array.getColor(R.styleable.bl_LevelLayout_bl_lineColor, Color.parseColor("#eeeeee"));
        pointRadius = array.getDimension(R.styleable.bl_LevelLayout_bl_pointRadius, 15);
        pointColor = array.getColor(R.styleable.bl_LevelLayout_bl_pointColor, Color.parseColor("#eeeeee"));
        array.recycle();
    }

    public LevelView(Context context, AttributeSet attrs, int theme) {
        super(context, attrs, theme);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int startX = getPaddingLeft();
        // set line's paint
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(lineHeight);
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        // set point's paint
        Paint pointPaint = new Paint();
        pointPaint.setStrokeWidth(pointRadius);
        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);
        // get line's distance
        int centerHeight = height / 2;
        if (thumb != null && !thumb.isRecycled()) {
            if (thumbWidth == 0) {
                thumbWidth = thumb.getWidth();
            }
            if (thumbHeight == 0) {
                thumbHeight = thumb.getHeight();
            }
            stepLength = (getWidth() - getPaddingLeft() - getPaddingRight() - thumbWidth) * 1.0f / stepCount;
            if (thumbWidth != thumb.getWidth() || thumbHeight != thumb.getHeight()) {
                thumb = Bitmap.createScaledBitmap(thumb, (int) thumbWidth, (int) thumbHeight, true);
            }
            float bitmapX = startX += thumb.getWidth() / 2;
            canvas.drawLine(startX, centerHeight, startX + stepLength*stepCount, centerHeight, linePaint);
            for (int i = 0; i < stepCount + 1; i++) {
                if (defaultPos == i) {
                    bitmapX = startX - thumb.getWidth() / 2 + offset;
                }
                canvas.drawCircle(startX, centerHeight, pointRadius, pointPaint);
                startX += stepLength;
            }
            //canvas.drawCircle(startX, centerHeight, pointRadius, pointPaint);
//            if (defaultPos == stepCount) {
//                bitmapX = startX - thumb.getWidth() / 2 + offset;
//            }
            canvas.drawBitmap(thumb, bitmapX, (getHeight() - thumbHeight) / 2, pointPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) thumbHeight;
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
    }

    /**
     * Convert drawable to bitmap.
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else {
                try {
                    if (drawable instanceof ColorDrawable) {
                        bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.RGB_565);
                    } else {
                        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
                    }
                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                canScroll = true;
                oldPosition = defaultPos;
                startX = startPos = event.getX();
                scrollLeft = getPaddingLeft() + thumbWidth / 2;
                scrollRight = getWidth() - getPaddingRight() - thumbWidth / 2;
                float thumbPos = defaultPos * stepLength + getPaddingLeft() + thumbWidth / 2;
                if (startX < thumbPos - DEFAULT_CLICK_RANGE || startX > thumbPos + DEFAULT_CLICK_RANGE) {
                    canScroll = false;
                }
                if (listener != null) {
                    listener.onStartTrackingTouch();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // avoid thumb scrolls to extra area
                if (event.getX() > scrollLeft && event.getX() < scrollRight) {
                    offset += (event.getX() - startX);
                    startX = event.getX();
                    if (canScroll) {
                        oldPosition = (int) ((event.getX() - getPaddingLeft()) / stepLength);
                        if (listener != null) {
                            listener.onLevelChanged((int) (event.getX() - scrollLeft));
                        }
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                // get the distance of up and down
                offset = event.getX() - startPos;
                if (Math.abs(offset) < DEFAULT_CLICK_RANGE) { // thumb's click event
                    // get latest position of thumb
                    defaultPos = (int) ((event.getX() - getPaddingLeft()) / stepLength);
                    offset = event.getX() - defaultPos * stepLength - scrollLeft;
                    if (offset > stepLength / 2) {
                        defaultPos++;
                    }

                    final float distance = (oldPosition - defaultPos) * stepLength;
                    if (distance != 0) {
                        ValueAnimator animator = ValueAnimator.ofFloat(distance, 0).setDuration(scrollDuration);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                offset = (float) animation.getAnimatedValue();
                                invalidate();
                            }
                        });
                        animator.start();
                    } else {
                        if (Math.abs(offset) < thumbWidth && listener != null) {
                            listener.onLevelClick();
                        }
                    }
                } else { // thumb's scroll event
                    if (canScroll) {
                        defaultPos = (int) ((event.getX() - getPaddingLeft()) / stepLength);
                        offset = event.getX() - defaultPos * stepLength - scrollLeft;
                        if (offset > stepLength / 2 && defaultPos < stepCount) {
                            defaultPos++;
                        }
                        invalidate();
                    }
                }
                if (listener != null) {
                    listener.onStopTrackingTouch();
                }
                offset = 0;
                break;
            default:
                break;
        }

        return true;
    }

    public float getStepLength() {
        return stepLength;
    }

    public void setStepLength(float stepLength) {
        this.stepLength = stepLength;
    }

    public void setThumb(Bitmap thumb) {
        if (thumb == null) {
            throw new RuntimeException("thumb can't be null");
        }

        this.thumb = thumb;
        invalidate();
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setDefaultPos(int defaultPos) {
        if (defaultPos > stepCount) {
            throw new RuntimeException("defaultPos out of stepCount's range");
        }
        this.defaultPos = defaultPos;
    }

    /**
     * Get the default postion of thumb
     * @return
     */
    public int getDefaultPos() {
        return defaultPos;
    }

    public int getScrollDuration() {
        return scrollDuration;
    }

    public void setScrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public float getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(float thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public float getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(float thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public float getLineWidth() {
        return lineHeight;
    }

    public void setLineWidth(float lineHeight) {
        this.lineHeight = lineHeight;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public float getPointRadius() {
        return pointRadius;
    }

    public void setPointRadius(float pointRadius) {
        this.pointRadius = pointRadius;
    }

    public int getPointColor() {
        return pointColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    /**
     * Listen the scrolling of thumb
     * @param listener
     */
    public void setOnLevelChangeListener(OnLevelChangeListener listener) {
        this.listener = listener;
    }

    public interface OnLevelChangeListener {
        /**
         * It is called when thumb scrolling.
         * @param progress means current position
         */
        void onLevelChanged(int progress);

        /**
         * It is called when thumb is pressed.
         */
        void onStartTrackingTouch();

        /**
         * It is called when thumb is up.
         */
        void onStopTrackingTouch();

        /**
         * It is called when thumb is clicked.
         */
        void onLevelClick();
    }
}
