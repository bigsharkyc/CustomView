package com.example.cylan.canvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.cylan.canvas.view.MyCanvas;

public class MainActivity extends AppCompatActivity implements MyCanvas.DegreesCallback{

    MyCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        myCanvas.setCurrentAngle(80);
        myCanvas.setDegreesCallback(this);
    }

    @Override
    public void valueCallback(int degrees) {
        Log.d("MainActivity", "degrees-->" + degrees);
    }
}
