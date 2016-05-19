package com.example.cylan.canvas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.cylan.canvas.R;

/**
 * Created by yangc on 2016/5/19.
 * @author yangc
 */
public class LeftAndRightView extends View{

    private static final String TAG = "LeftAndRightView";
    private static final int DEFAULT_VIEW_WIDTH = 100;
    private static final int DEFAULT_VIEW_HEIGHT = 25;

    private int width, height;
    private int distance;

    private Bitmap bm_light, bm_pole;

    private Rect rect_light, rect_pole;

    private LeftAndRightCallback callback;

    public LeftAndRightView(Context context) {
        this(context, null);
    }

    public LeftAndRightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftAndRightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LeftAndRightView, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.LeftAndRightView_light:
                    bm_light = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
                case R.styleable.LeftAndRightView_pole:
                    bm_pole = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
            }
        }
        array.recycle();
        rect_light = new Rect();
        rect_pole = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width  = measureDimension(DEFAULT_VIEW_WIDTH, widthMeasureSpec);
        height = measureDimension(DEFAULT_VIEW_HEIGHT, heightMeasureSpec);

        setMeasuredDimension(width, height);
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        rect_pole.left = getPaddingLeft();
        rect_pole.right = width - getPaddingRight();
        rect_pole.top = height / 2 - bm_pole.getHeight() / 2;
        rect_pole.bottom = height / 2 + bm_pole.getHeight() / 2;
        canvas.drawBitmap(bm_pole, null, rect_pole, null);

        rect_light.left = distance;
        rect_light.right = bm_light.getWidth() + distance;
        rect_light.top = rect_pole.top;
        rect_light.bottom = height / 2 + bm_light.getHeight();
        canvas.drawBitmap(bm_light, null, rect_light, null);
    }

    private boolean isInArea = false;
    private int temp;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x , y ;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                if (x >= rect_light.left && x <= rect_light.right
                        && y >= rect_light.top && y <= rect_light.bottom){
                    isInArea = true;
                    temp = (int)x - rect_light.left;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInArea){
                    setDistance((int)event.getX() - getPaddingRight() - temp);
                }
                break;
            case MotionEvent.ACTION_UP:
                isInArea = false;
                break;
        }
        return true;
    }

    public void setDistance(int distance){
        if (distance > 0 && distance <= rect_pole.right - bm_light.getWidth()){
            Log.d(TAG, "distance-->" + distance + " rightMarge-->" + (rect_pole.right - bm_light.getWidth()));
            this.distance = distance;
            postInvalidate();
        }
    }

    public void setLeftAndRightCallback(LeftAndRightCallback callback){
        this.callback = callback;
    }

    public interface LeftAndRightCallback{
        void callback(int distance);
    }
}
