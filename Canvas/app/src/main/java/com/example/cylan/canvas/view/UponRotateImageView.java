package com.example.cylan.canvas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.cylan.canvas.R;


public class UponRotateImageView extends ImageView {

    private static final int DEFAULT_VIEW_WIDTH = 200;
    private static final int DEFAULT_VIEW_HEIGHT = 200;

    private int mAngle;

    public UponRotateImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadAttributes(context, attrs);
    }

    public UponRotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context, attrs);
    }

    public UponRotateImageView(Context context) {
        super(context);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.UponRotateImageView);
        mAngle = arr.getInteger(R.styleable.UponRotateImageView_angle, 0);
        arr.recycle();
    }

    public int getAngle() {
        return mAngle;
    }

    public void setAngle(int angle) {
        mAngle = angle;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getDrawable().getIntrinsicWidth();
        int h = getDrawable().getIntrinsicHeight();
        double a = Math.toRadians(mAngle);

        int width = (int) (Math.abs(w * Math.cos(a)) + Math.abs(h * Math.sin(a)));
        int height = (int) (Math.abs(w * Math.sin(a)) + Math.abs(h * Math.cos(a)));

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(mAngle % 360, getWidth() / 2, getHeight() / 2);
        getDrawable().draw(canvas);
        canvas.restore();
    }
}
