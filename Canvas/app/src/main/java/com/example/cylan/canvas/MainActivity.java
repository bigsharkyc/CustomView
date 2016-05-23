package com.example.cylan.canvas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.cylan.canvas.view.HorizontalView;
import com.example.cylan.canvas.view.LeftAndRightView;
import com.example.cylan.canvas.view.ProjectionView;
import com.example.cylan.canvas.view.UpAndDownView;
import com.example.cylan.canvas.view.UponRotateImageView;
import com.example.cylan.canvas.view.VerticalView;

public class MainActivity extends AppCompatActivity implements ProjectionView.DegreesCallback, LeftAndRightView.LeftAndRightCallback{

    ProjectionView myCanvas;
    VerticalView verticalView;
    UpAndDownView upAndDownView;
    LeftAndRightView leftAndRightView;
    HorizontalView horizontalView;
    RelativeLayout layout;
    UponRotateImageView uponRotateImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
//        myCanvas.setCurrentAngle(80);
//        myCanvas.setDegreesCallback(this);

//        verticalView = (VerticalView) findViewById(R.id.vertical_view);


//        upAndDownView = (UpAndDownView) findViewById(R.id.vertical_view);
//        if (upAndDownView != null)
//            upAndDownView.setLength(49);

//        leftAndRightView = (LeftAndRightView) findViewById(R.id.left_right);
//        if (leftAndRightView != null) {
//            leftAndRightView.setDistance(50);
//            leftAndRightView.setLeftAndRightCallback(this);
//        }
//        horizontalView = (HorizontalView) findViewById(R.id.horizontal_view);
//        rotateImageView = (RotateImageView)findViewById(R.id.rotate_image);
//        if (rotateImageView != null){
//            rotateImageView.setResIds(R.drawable.bg_top, R.drawable.img_instrument);
//            rotateImageView.setDegree(43f, 200f, 200f);
//        }
//        layout = (RelativeLayout) findViewById(R.id.layout);
//        rotateImageView = new RotateImageView(this);
//        rotateImageView.setDegree(45f, 200f, 200f);
//        layout.addView(rotateImageView, new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        uponRotateImageView = (UponRotateImageView) findViewById(R.id.upon_rotate);
//        uponRotateImageView.setAngle(-90);

        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setRotation(130);

    }

    @Override
    public void valueCallback(int degrees) {
        Log.d("MainActivity", "degrees-->" + degrees);
    }

    @Override
    public void callback(int value) {
        Log.d("MainActivity", "degrees-->" + value);
    }
}
