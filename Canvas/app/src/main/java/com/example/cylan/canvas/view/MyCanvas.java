package com.example.cylan.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
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
    private int degress;


    public MyCanvas(Context context) {
        super(context);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintArc = new Paint();
        paintArc.setColor(Color.argb(180, 254, 208, 178));
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
        this.degress = degress;
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
        float r = width / 2;
        float l =  r * (float) Math.sin(Math.toRadians(degress / 2));
        Log.d("MyCanvas", "l-->" + l + " sin-->" + Math.sin(Math.toRadians(degress / 2)));

        RectF oval = new RectF(width / 2 - l, width - 140, width / 2 + l, width);
        canvas.drawOval(oval, paintOval);

        RectF rectF = new RectF(0f, 0f, width, width);
        canvas.drawArc(rectF, 90 - degress / 2, degress, true, paintArc);
    }
}
