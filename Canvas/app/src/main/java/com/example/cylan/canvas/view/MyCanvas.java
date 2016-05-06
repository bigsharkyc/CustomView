package com.example.cylan.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cylan on 2016/5/6.
 * @author yangc
 */
public class MyCanvas extends View{

    private int width;
    private int height;
    private Paint paintArc;
    private Paint paintOval;


    public MyCanvas(Context context) {
        super(context);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintArc = new Paint();
        paintArc.setColor(Color.argb(200, 254, 208, 178));
        paintArc.setStrokeWidth(2);
        paintArc.setStyle(Paint.Style.FILL_AND_STROKE);
        paintArc.setStrokeCap(Paint.Cap.ROUND);
        paintArc.setStrokeJoin(Paint.Join.ROUND);

        paintOval = new Paint();
        paintOval.setColor(Color.argb(254, 254, 163, 102));
        paintOval.setStrokeWidth(2);
        paintOval.setStyle(Paint.Style.FILL_AND_STROKE);
        paintOval.setStrokeCap(Paint.Cap.ROUND);
        paintOval.setStrokeJoin(Paint.Join.ROUND);
    }

    public int getCurrentAngle(){
        return 0;
    }

    public void setCurrentAngle(int degress){

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
        RectF rectF = new RectF(0f, 0f, width, height - 200);
        canvas.drawArc(rectF, 30f, 120f, true, paintArc);

        RectF oval = new RectF(0f, 0f, width, height - 200);
        canvas.drawOval(oval, paintOval);
    }
}
