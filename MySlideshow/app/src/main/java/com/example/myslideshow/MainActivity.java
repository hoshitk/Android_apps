package com.example.myslideshow;

import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAnimationButtonTapped(View view) {
        ViewPropertyAnimator animator = view.animate();
        animator.setDuration(3000); //3000msかけてアニメーションを描画する
        animator.rotation(360.0f * 5.0f);   //360度を5.0回、つまり5回転する
    }

    public void onAnimationButtonTapped2(View view) {
        view.animate().setDuration(3000).scaleX(2.5f).scaleY(2.5f);
        //ViewPropertyAnimatorクラスのメソッドは殆どがそれ自身を戻り値として返す
    }

    public void onAnimationButtonTapped3(View view) {
        view.animate().setDuration(3000).x(200.0f).y(200.0f);
        //同時に実行される。３秒間かけてx,y軸共に200ポイント移動する
    }

    public void onAnimationButtonTapped4(final View view) {
        float y = view.getY() + 100;    //y座標取得+100pixel
        view.animate().setDuration(1000)
                .setInterpolator(new BounceInterpolator()).y(y);
    }

    public void onAnimationButtonTapped5(final View view) {
        float x = view.getX() + 500;
        float y = view.getY() + 100;    //y座標取得+100pixel
        view.animate().setDuration(1000)
                .setInterpolator(new BounceInterpolator()).x(x).y(y);
    }
}