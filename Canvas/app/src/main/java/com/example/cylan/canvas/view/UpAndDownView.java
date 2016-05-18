package com.example.cylan.canvas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.cylan.canvas.R;

/**
 * Created by yangc on 2016/5/18.
 * @author yangc
 */
public class UpAndDownView extends View{

    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;
    private int width, height;
    private int length = 0;
    private Paint mPaint;

    private Bitmap bg;
    private Rect bg_rect;
    private Matrix bg_matrix;
    /**
     * 图片的缩放模式
     */
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;
    private int imageScaleType;

    public UpAndDownView(Context context) {
        this(context, null);
    }

    public UpAndDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mPaint = new Paint();
    }

    public UpAndDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UpAndDownView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.UpAndDownView_bg:
                    bg = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
                case R.styleable.UpAndDownView_image_scale_type:
                    imageScaleType = array.getInt(attr, 0);
                    break;
            }
        }
        array.recycle();
        bg_rect = new Rect();
        bg_matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            width = specSize;
        }else {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + bg.getWidth();

            if (specMode == MeasureSpec.AT_MOST){         /// wrap_content
                width = desireByImg;
                Log.e("xxx", "AT_MOST");
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){          // match_parent , accurate
            height = specSize;
        } else{
            int desire = getPaddingTop() + getPaddingBottom() + bg.getHeight();
            if (specMode == MeasureSpec.AT_MOST){         // wrap_content
                height = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.argb(254, 255, 104, 1));
        //画方块
        canvas.drawRect(width / 2 - 45, (height - bg.getHeight()) / 2 + length - 120, width / 2 + 45,
                (height + bg.getHeight()) / 2 + length, mPaint);
        //画背景图
        mPaint.setStyle(Paint.Style.FILL);
        bg_matrix.postScale(0.5f, 0.5f);
        if (imageScaleType == IMAGE_SCALE_FITXY){
            bg_rect.left = getPaddingLeft();
            bg_rect.right = width - getPaddingRight();
            bg_rect.top = getPaddingTop();
            bg_rect.bottom = height - getPaddingBottom();
            canvas.drawBitmap(bg,null, bg_rect, mPaint);
        }else if (imageScaleType == IMAGE_SCALE_CENTER){
            bg_rect.left = width / 2 - bg.getWidth() / 4;
            bg_rect.right = width / 2 + bg.getWidth() / 4;
            bg_rect.top = height / 2 - bg.getHeight() / 2;
            bg_rect.bottom = height / 2 + bg.getHeight() / 2;
            canvas.drawBitmap(bg, null, bg_rect, mPaint);
        }
    }

    public void setLength(int length){
        if (length >= MIN_LENGTH && length <= MAX_LENGTH)
            this.length = length;
        postInvalidate();
    }

    public int getLength(){
        return length;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (x >= width / 2 - 50 && x <= width / 2 + 50){
                if (y >= height / 2 + bg.getHeight() / 2 && y <= height){
                    setUp();
                }else if (y >= 0 && y <= height / 2 - bg.getHeight() / 2){
                    setDown();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setDown(){
        if (length >= MIN_LENGTH){
            length--;
            postInvalidate();
        }
    }

    private void setUp(){
        if (length <= MAX_LENGTH){
            length++;
            postInvalidate();
        }
    }

}
