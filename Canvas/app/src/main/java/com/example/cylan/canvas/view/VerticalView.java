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
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.cylan.canvas.R;

/**
 * Created by yangc on 2016/5/16.
 * @author yangc
 */
public class VerticalView extends View{

    private float width, height;

    private Bitmap mImage;
    private Bitmap bg_top;

    private RectF mBounds;
    private Rect topRect;
    private Rect pointRect;
    private Paint mPaint;
    private Matrix matrix;

    private float rotateAngle;
    private float radius;

    public VerticalView(Context context) {
        this(context, null);
    }

    public VerticalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerticalView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.VerticalView_pointer:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.VerticalView_bg_top:
                    bg_top = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                default:
                    break;
            }
        }
        typedArray.recycle();
        topRect = new Rect();
        pointRect = new Rect();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        rotateAngle = 0;
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBounds = new RectF(getLeft(), getTop(), getRight(), getBottom());
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
//        width = mBounds.right - mBounds.left;
//        height = mBounds.bottom - mBounds.top;
        if (width < height){
            radius = width / 4;
        }else {
            radius = height / 4;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画顶部
        topRect.left = (int)(width - bg_top.getWidth()) / 2;
        topRect.right = (int)(width + bg_top.getWidth()) / 2;
        topRect.bottom = bg_top.getHeight();
        topRect.top = 0;
        canvas.drawBitmap(bg_top, null, topRect, null);

        //画指针
//        pointRect.left =(int) (width - mImage.getWidth()) / 2;
//        pointRect.right = (int)(width + mImage.getWidth()) / 2;
//        pointRect.top = 100;
//        pointRect.bottom = mImage.getHeight() + pointRect.top;
//        canvas.drawBitmap(mImage, null, pointRect, null);
//
        Log.d("Vertical", "rotateAngle-->" + rotateAngle);
//
//        matrix.postRotate(-45);
//        Bitmap dstmap = Bitmap.createBitmap(mImage, 0, 0,mImage.getWidth(), mImage.getHeight(), matrix, true);
//        canvas.drawBitmap(dstmap, 0f, 0f, null);

        mPaint.setStrokeWidth(2f);
        mPaint.setColor(Color.argb(254, 255, 104, 1));
        canvas.drawCircle(mBounds.centerX(),mBounds.centerY(), radius, mPaint);
        float start_x,start_y;
        float end_x,end_y;
        for (int i = 0; i < 60; i++){
            start_x = radius * (float)Math.cos(Math.PI / 180 * i * 6);
            start_y = radius * (float)Math.sin(Math.PI / 180 * i * 6);
            if (i % 5 == 0){
                end_x = start_x + 20 * (float)Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y + 20 * (float)Math.sin(Math.PI / 180 * i * 6);
            }else {
                end_x = start_x + 10*(float)Math.cos(Math.PI/180 * i * 6);
                end_y = start_y + 10*(float)Math.sin(Math.PI/180 * i * 6);
            }
            start_x += mBounds.centerX();
            end_x += mBounds.centerX();
            start_y += mBounds.centerY();
            end_y += mBounds.centerY();
            canvas.drawLine(start_x, start_y, end_x , end_y, mPaint);
        }
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), 20, mPaint);
        canvas.drawLine( mBounds.centerX(),mBounds.centerY(), mBounds.centerX()+radius*(float)Math.sin(rotateAngle/180 * Math.PI)
                ,mBounds.centerY()-radius*(float) Math.cos(rotateAngle / 180 * Math.PI),mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        float dx = x - mBounds.centerX();
        float dy = y - mBounds.centerY();
        rotateAngle = (float)(Math.atan(-dx / dy) * 180 / Math.PI);
        if (dy > 0){
            rotateAngle += 180;
        }else if (dx < 0 && dy < 0){
            rotateAngle += 360;
        }
        invalidate();
        return true;
    }
}
