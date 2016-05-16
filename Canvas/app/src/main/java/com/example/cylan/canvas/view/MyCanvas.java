package com.example.cylan.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yangc on 2016/5/6.
 * @author yangc
 */
public class MyCanvas extends View{

    private static final float SCALE_LONG = 15f;
    private static final float SCALE_SHORT = 10f;
    private static final float NUMBER_SIZE = 30f;

    private int width;
    private int height;
    private Paint paintArc;
    private Paint paintOval;
    private Paint paintTrigon;
    private Paint paintRect;
    private Paint black;
    private int degrees;
    private int average;

    private DegreesCallback callback;

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
        paintOval.setColor(Color.argb(200, 254, 163, 102));
        paintOval.setStrokeWidth(2);
        paintOval.setStyle(Paint.Style.FILL_AND_STROKE);
        paintOval.setStrokeCap(Paint.Cap.ROUND);
        paintOval.setStrokeJoin(Paint.Join.ROUND);

        paintTrigon = new Paint();
        paintTrigon.setColor(Color.argb(180, 254, 208, 178));

        paintRect = new Paint();
        paintRect.setColor(Color.argb(254, 255, 104, 1));

        black = new Paint();
        black.setColor(Color.BLACK);
    }

    public void setCurrentAngle(int degrees){
        this.degrees = degrees;
    }

    public void setDegreesCallback(DegreesCallback callback){
        this.callback = callback;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        average = (width / 2) / 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画顶部矩形
        canvas.drawRect(width / 2 - 100, 0, width / 2 + 100, 120, paintRect);

        //画梯形
        Path trigon = new Path();
        trigon.moveTo(width / 2 - 50, 120);
        trigon.lineTo(width / 2 + 50, 120);
        if (degrees >= width / 2){
            trigon.lineTo(width, height / 2);
            trigon.lineTo(0, height / 2);
        }else {
            trigon.lineTo(width / 2 + degrees, height / 2);
            trigon.lineTo(width / 2 - degrees, height / 2);
        }
        trigon.close();
        canvas.drawPath(trigon, paintTrigon);

        //画椭圆
        RectF oval;
        if (degrees >= width / 2 ){
            oval = new RectF(0, height / 2 - 50, width, height / 2 + 50);
        }else {
            if (degrees <= 50){
                oval = new RectF(width / 2 - degrees, height / 2 - degrees,
                        width / 2 + degrees, height / 2 + degrees);
            }else {
                oval = new RectF(width / 2 - degrees, height / 2 - 50,
                        width / 2 + degrees, height / 2 + 50);
            }
        }
        canvas.drawOval(oval, paintOval);

        //画标尺原点
        canvas.drawCircle(width / 2, height / 2, 10f, black);

        //画标尺直线
        black.setStrokeWidth(3f);
        canvas.drawLine(width / 2, height / 2, width, height / 2, black);

        //画标尺刻度
        black.setStrokeWidth(1f);
        float length = width / 16;
        for (int i = 1; i <= 8; i++){
            if (i % 2 == 0)
                canvas.drawLine(width / 2 + length * i, height / 2, width / 2 + length * i,
                        height / 2 + SCALE_LONG, black);
            else
                canvas.drawLine(width / 2 + length * i, height / 2, width / 2 + length * i,
                        height / 2 + SCALE_SHORT, black);

        }
        //因精度问题最后一个刻度没有显示，额外画出
        canvas.drawLine(width - 1f, height / 2, width - 1f, height / 2 + SCALE_LONG, black);

        //写数字
        black.setTextSize(NUMBER_SIZE);
        black.setStrokeWidth(5f);
        canvas.drawText("15", width / 2 + 20f, height / 2 - 20f, black);
        canvas.drawText("50", width - 35f, height / 2 - 20f, black);
    }

    private int downX, moveX, distance, temp, lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                distance = 0;
                lastX = downX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int)event.getX();
                if (moveX > width / 2 && moveX <= width){             ////右侧滑动
                    distance = Math.abs(downX - moveX);
                    if (lastX < moveX){       /////手势向右
                        setUp(Math.abs(distance - temp));
                    }else {
                        setDown(Math.abs(distance - temp));
                    }
                }else if (moveX < width / 2 && moveX >= 0){      ////左侧滑动
                    distance = Math.abs(downX - moveX);
                    if (lastX > moveX){      /////手势向左
                        setUp(Math.abs(distance - temp));
                    }else {
                        setDown(Math.abs(distance - temp));
                    }
                }
                temp = distance;
                lastX = (int)event.getX();
                break;
            case MotionEvent.ACTION_UP:
                downX = moveX = distance = temp = lastX = 0;
                break;
        }
        return true;
    }

    private void setUp(int distanceX){
        if (degrees >= width / 2)
            return;
        degrees += distanceX;
//        Log.d("MyCanvas", "setUp, distanceX-->" + distanceX + " degrees-->" + degrees);
        postInvalidate();
        if (callback != null){
            callback.valueCallback((degrees / average - 3) >= 50 ? 50 : degrees / average);
        }
    }

    private void setDown(int distanceX){
        if (degrees <= 5)
            return;
        degrees -= distanceX;
//        Log.d("MyCanvas", "setDown, distanceX-->" + distanceX + " degrees-->" + degrees);
        postInvalidate();
        if (callback != null){
            callback.valueCallback((degrees / average - 3) >= 50 ? 50 : degrees / average);
        }
    }

    public interface DegreesCallback{
        void valueCallback(int degrees);
    }
}
