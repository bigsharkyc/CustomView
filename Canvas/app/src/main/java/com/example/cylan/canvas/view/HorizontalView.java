package com.example.cylan.canvas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.cylan.canvas.R;

/**
 * Created by yangc on 2016/5/23.
 * @author yangc
 */
public class HorizontalView extends View{
    private static final int DEFAULT_VIEW_WIDTH = 300;
    private static final int DEFAULT_VIEW_HEIGHT = 300;

    private int width, height;
    private Rect rect, rect_pointer;
    private Bitmap bm_instrument, bm_poniter;

    public HorizontalView(Context context) {
        this(context, null);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalView, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++){
            int index = array.getIndex(i);
            switch (index){
                case R.styleable.HorizontalView_instrument:
                    bm_instrument = BitmapFactory.decodeResource(getResources(), array.getResourceId(index, 0));
                    break;
                case R.styleable.HorizontalView_h_pointer:
                    bm_poniter = BitmapFactory.decodeResource(getResources(), array.getResourceId(index, 0));
                    break;
            }
        }
        array.recycle();
        rect = new Rect();
        rect_pointer = new Rect();
    }

    protected int measureDimension( int defaultSize, int measureSpec ) {

        int result = defaultSize;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //1. layout给出了确定的值，比如：100dp
        //2. layout使用的是match_parent，但父控件的size已经可以确定了，比如设置的是具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize; //建议：result直接使用确定值
        }
        //1. layout使用的是wrap_content
        //2. layout使用的是match_parent,但父控件使用的是确定的值或者wrap_content
        else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize); //建议：result不能大于specSize
        }
        //UNSPECIFIED,没有任何限制，所以可以设置任何大小
        //多半出现在自定义的父控件的情况下，期望由自控件自行决定大小
        else {
            result = defaultSize;
        }

        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width  = measureDimension(DEFAULT_VIEW_WIDTH, widthMeasureSpec);
        height = measureDimension(DEFAULT_VIEW_HEIGHT, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        rect.left = getPaddingLeft();
        rect.right = width - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = height - getPaddingBottom();
        canvas.drawBitmap(bm_instrument, null, rect, null);
        rect_pointer.left = width / 2 - bm_poniter.getWidth() / 2 - getPaddingLeft();
        rect_pointer.right = width / 2 + bm_poniter.getWidth() / 2 + getPaddingRight();
        rect_pointer.top = rect.top - 50;
        rect_pointer.bottom = bm_poniter.getHeight();
        canvas.drawBitmap(bm_poniter, null, rect_pointer, null);
    }
}
