package com.example.slideshow;

import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAnimationButtonTapped(final View view) {
        float x = view.getX() + 500;
        float y = view.getY() + 100;    //y座標取得+100pixel
        view.animate().setDuration(1000)
                .setInterpolator(new BounceInterpolator()).x(x).y(y);
    }
}