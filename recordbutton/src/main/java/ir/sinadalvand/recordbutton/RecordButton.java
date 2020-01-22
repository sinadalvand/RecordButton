/*
 *
 *   Created by Sina Dalvand on 1/21/20 8:23 AM
 *   Copyright (c) 2020 . All rights reserved.
 *
 *
 */
package ir.sinadalvand.recordbutton;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;


public class RecordButton extends View implements Animatable {
    private Paint buttonPaint, behindButtonPaint, strokeProgressPaint;
    private RectF rectF;

    private float buttonRadius;
    private float behindButtonRadius;
    private int progressStroke;

    private int buttonColor;
    private int progressColor;
    private int behindButtonColor;

    private int startAngle = 270;
    private int sweepAngle;

    private float buttonGap;

    private int currentMilliSecond = 0;
    private int maxMillisecond;

    private boolean isRecording = false;

    private RecordButtonListener onRecordListener;


    public RecordButton(Context context) {
        super(context);
        init(context, null);
    }

    public RecordButton(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecordButton(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        buttonRadius = 85f;
        behindButtonRadius = 100f;
        progressStroke = 5;
        buttonGap = 0f;
        behindButtonColor = Color.parseColor("#e5e5e7");
        buttonColor = Color.parseColor("#FFFFFF");
        progressColor = Color.YELLOW;
        maxMillisecond = 7000;


        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecordButton);

            buttonColor = a.getColor(R.styleable.RecordButton_rb_buttonColor, buttonColor);
            behindButtonColor = a.getColor(R.styleable.RecordButton_rb_backColor, behindButtonColor);
            progressColor = a.getColor(R.styleable.RecordButton_rb_progressbarColor, progressColor);
            maxMillisecond = a.getInt(R.styleable.RecordButton_rb_progressTime, maxMillisecond);
            progressStroke = a.getDimensionPixelSize(R.styleable.RecordButton_rb_progressbarWidth, progressStroke);

            a.recycle();
        }


        buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint.setColor(buttonColor);
        buttonPaint.setStyle(Paint.Style.FILL);

        behindButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        behindButtonPaint.setColor(behindButtonColor);
        behindButtonPaint.setStyle(Paint.Style.FILL);
        behindButtonPaint.setAlpha(60);


        strokeProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeProgressPaint.setColor(progressColor);
        strokeProgressPaint.setStyle(Paint.Style.STROKE);
        strokeProgressPaint.setStrokeWidth(progressStroke);
        strokeProgressPaint.setStrokeCap(Paint.Cap.ROUND);


        rectF = new RectF();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        canvas.drawCircle(cx, cy, behindButtonRadius, behindButtonPaint);
        canvas.drawCircle(cx, cy, buttonRadius, buttonPaint);
        sweepAngle = 360 * currentMilliSecond / maxMillisecond;

        rectF.set(cx - (behindButtonRadius) - buttonGap, cy - (behindButtonRadius) - buttonGap, cx + (behindButtonRadius) + buttonGap, cy + (behindButtonRadius) + buttonGap);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, strokeProgressPaint);

    }


    ObjectAnimator animator;

    @SuppressLint("ObjectAnimatorBinding")
    private ObjectAnimator progressAnimate() {
        animator = ObjectAnimator.ofFloat(this, "progress", currentMilliSecond, maxMillisecond);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Float) (animation.getAnimatedValue())).intValue();

                if (isRecording) {
                    currentMilliSecond = value;
                    postInvalidate();

                    if (onRecordListener != null) {
                        onRecordListener.onProgressing(currentMilliSecond / (maxMillisecond * 1.0f));
                    }

                    if (currentMilliSecond == maxMillisecond)
                        stop();

                }

            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(maxMillisecond);
        return animator;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (onRecordListener != null) {
                    if (onRecordListener.onStartProgress()) {
                        start();
                        progressAnimate().start();
                    }

                } else {
                    start();
                    progressAnimate().start();
                }


                break;
            case MotionEvent.ACTION_UP:
                stop();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = ((int) (behindButtonRadius + 10) * 2 + (int) buttonGap * 2 + progressStroke + 30);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(size, widthSize);
        } else {
            width = size;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(size, heightSize);
        } else {
            height = size;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void start() {
        isRecording = true;
        scaleAnimation(1.3f, 1.3f);
    }

    @Override
    public void stop() {

        if (isRecording)
            if (currentMilliSecond != maxMillisecond) {
                animator.cancel();
                isRecording = false;
                if (onRecordListener != null)
                    onRecordListener.onCancell();
            } else {
                if (onRecordListener != null)
                    onRecordListener.onFinish();
            }


        isRecording = false;
        currentMilliSecond = 0;
        scaleAnimation(1f, 1f);


    }


    @Override
    public boolean isRunning() {
        return isRecording;
    }

    private void scaleAnimation(float scaleX, float scaleY) {
        this.animate()
                .scaleX(scaleX)
                .scaleY(scaleY)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }


    public void setPercent(float percent) {
        this.currentMilliSecond = (int) (maxMillisecond * (percent / 100));
        postInvalidate();
    }

    public void setProgressColor(int color) {
        progressColor = color;
        strokeProgressPaint.setColor(progressColor);
        postInvalidate();
    }

    public void setBackColor(int color) {
        behindButtonColor = color;
        behindButtonPaint.setColor(behindButtonColor);
        behindButtonPaint.setAlpha(60);
        postInvalidate();
    }

    public void setButtonColor(int color) {
        buttonColor = color;
        buttonPaint.setColor(buttonColor);
        postInvalidate();
    }

    public void setProgressWidth(int width) {
        progressStroke = width;
        strokeProgressPaint.setStrokeWidth(progressStroke);
        postInvalidate();
    }

    /* Millisec */
    public void setProgressTime(int time) {
        maxMillisecond = time;
        postInvalidate();
    }


    public void setOnRecordListener(RecordButtonListener onRecordListener) {
        this.onRecordListener = onRecordListener;
    }


}