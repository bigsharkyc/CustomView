package com.example.cylan.canvas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.cylan.canvas.R;

/**
 * Created by yangc on 2016/5/16.
 * @author yangc
 */
public class VerticalView extends View{

    private int width, height;

    private Bitmap mImage;
    private Bitmap bg_top;

    private Rect topRect;
    private Rect pointRect;
    private Paint mPaint;

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
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画顶部
        topRect.left = (width - bg_top.getWidth()) / 2;
        topRect.right = (width + bg_top.getWidth()) / 2;
        topRect.bottom = bg_top.getHeight();
        topRect.top = 0;
        canvas.drawBitmap(bg_top, null, topRect, null);

        //画指针
        pointRect.left = (width - mImage.getWidth()) / 2;
        pointRect.right = (width + mImage.getWidth()) / 2;
        pointRect.top = 100;
        pointRect.bottom = mImage.getHeight() + pointRect.top;
        canvas.drawBitmap(mImage, null, pointRect, null);
    }
}
