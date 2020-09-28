package com.example.slideshow;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;       //ImageSwitcherのインスタンスを保持するための変数を用意
    int[] mImageResorces = {R.drawable.slide00,R.drawable.slide01,R.drawable.slide02,
            R.drawable.slide03,R.drawable.slide04,R.drawable.slide05,
            R.drawable.slide06,R.drawable.slide07,R.drawable.slide08,
            R.drawable.slide09,};   //integer型配列で管理
    int mPosition = 0;      //どの画像を表示しているかの変数

    boolean mIsSlideshow = false;
    MediaPlayer mMediaPlayer;

    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            if(mIsSlideshow) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        movePosition(1);
                    }
                });
            }
        }
    }

    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {  //setfactory()でビューを生成
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());   //imageViewインスタンス生成　渡してるのがわからん
                return imageView;
            }
        });
        mImageSwitcher.setImageResource(mImageResorces[0]);
        mTimer.schedule(mTimerTask, 0, 5000);
        mMediaPlayer = MediaPlayer.create(this, R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }

    public void onAnimationButtonTapped(final View view) {
        ViewPropertyAnimator animator = view.animate();
        animator.setDuration(3000).rotation(360.0f * 5.0f);
    }

    private void movePosition(int move) {

        mPosition = mPosition + move;
        if (mPosition >= mImageResorces.length) {
            mPosition = 0;
        } else if (mPosition < 0) {
            mPosition = mImageResorces.length - 1;
        }

        mImageSwitcher.setImageResource(mImageResorces[mPosition]);
        //positionのリソースIDを渡して、画像を表示
    }

    public void onPrevButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        movePosition(-1);
        findViewById(R.id.imageView).animate().setDuration(1000).alpha(0.0f);   //R.id.imageViewはドロイド君　こいつを消す
    }

    public void onNextButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_out_right);
        movePosition(1);
        findViewById(R.id.imageView).animate().setDuration(1000).alpha(5.0f);   //alpha()ビューの透過度
    }

    public void onSlideshowButtonTapped(View view) {
        mIsSlideshow =! mIsSlideshow;   //スライドショーボタン押すと切り替わる。スイッチ

        //音楽の再生と停止
        if(mIsSlideshow) {
            mMediaPlayer.start();
        } else {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0); //0msecにseekして先頭から再生
        }
    }
}