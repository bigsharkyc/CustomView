package com.example.cylan.canvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.cylan.canvas.view.MyCanvas;
import com.example.cylan.canvas.view.UpAndDownView;
import com.example.cylan.canvas.view.VerticalView;

public class MainActivity extends AppCompatActivity implements MyCanvas.DegreesCallback{

    MyCanvas myCanvas;
    VerticalView verticalView;
    UpAndDownView upAndDownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
//        myCanvas.setCurrentAngle(80);
//        myCanvas.setDegreesCallback(this);

//        verticalView = (VerticalView) findViewById(R.id.vertical_view);
        upAndDownView = (UpAndDownView) findViewById(R.id.vertical_view);
        if (upAndDownView != null)
            upAndDownView.setLength(49);
    }

    @Override
    public void valueCallback(int degrees) {
        Log.d("MainActivity", "degrees-->" + degrees);
    }
}
