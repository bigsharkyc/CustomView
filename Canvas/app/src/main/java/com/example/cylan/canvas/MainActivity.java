package com.example.cylan.canvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cylan.canvas.view.MyCanvas;

public class MainActivity extends AppCompatActivity {

    MyCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        myCanvas.setCurrentAngle(80);
    }
}
