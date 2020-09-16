package com.example.janken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    final int JANKEN_GU = 0;            //定数定義
    final int JANKEN_CHOKI = 1;
    final int JANKEN_PA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        int myHand = 0; //switch文で受け取った手の情報を格納
        Intent intent = getIntent();    //intent受け取り
        int id = intent.getIntExtra("MY_HAND", 0);

        ImageView myHandImageView =
                (ImageView) findViewById(R.id.my_hand_image);   //Idからイメージ画像取得
        switch (id) {
            case R.id.gu:
                myHandImageView.setImageResource(R.drawable.gu);    //グーのイメージ画像をセット
                myHand = JANKEN_GU; //グーの手(=0)をセット
                break;
            case R.id.choki:
                myHandImageView.setImageResource(R.drawable.choki);
                myHand = JANKEN_CHOKI;
                break;
            case R.id.pa:
                myHandImageView.setImageResource(R.drawable.pa);
                myHand = JANKEN_PA;
                break;
            default:
                myHand = JANKEN_GU;
                break;
        }

        /*
    onCreate()で引き継いだintentを展開
    intentに格納された情報をgetXXXExtra()で展開　受け取るデータ型ごとにメソッドも用意されている
    findViewById()はActivityに配置したイメージビューを取得する。
    switch文で分岐して受け取ったRの情報の手のimageをset
    */

        // コンピュータの手を決める
        int comHand = (int) (Math.random() * 3);    //random()は0~1の実数を返す　3倍してキャストすれば1or2or3を得られる
        ImageView comHandImageView =
                (ImageView) findViewById(R.id.com_hand_image);
        switch (comHand) {
            case JANKEN_GU:
                comHandImageView.setImageResource(R.drawable.com_gu);
                break;
            case JANKEN_CHOKI:
                comHandImageView.setImageResource(R.drawable.com_choki);
                break;
            case JANKEN_PA:
                comHandImageView.setImageResource(R.drawable.com_pa);
                break;
        }

        //勝敗を判定する
        TextView resultLabel  = (TextView) findViewById(R.id.result_label);
        int gameResult = (comHand - myHand + 3) % 3;    //結果の演算
        switch (gameResult) {
            case 0:
                //あいこの場合
                resultLabel.setText(R.string.result_draw);  //あいこのテキストをセット
                break;
            case 1:
                //勝ちの場合
                resultLabel.setText(R.string.result_win);
                break;
            case 2:
                //負けの場合
                resultLabel.setText(R.string.result_lose);
                break;
        }
    }

    public void onNextButtonTapped(View view) { //もう一回ボタン
        finish();   //ResultActivityが終了してMainActivityに戻る
    }

    /*
    共有プリファレンスによるデータの保存　(p.095~098)

    最初にPreferenceManagerクラスのgetDefaultSharedPreferencesクラスメソッドを使い、
    デフォルトの共有プリファレンスを取得する。
     */

    private void saveData(int myHand, int comHand, int gameResult) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);    //デフォルトの共有プリファレンスを取得
        SharedPreferences.Editor editor = pref.edit();  //共有プリファレンスに書き込みを行う　editメソッドを用いる

        int gameCount = pref.getInt("GAME_COUNT", 0);   //共有プリファレンスから各値を取得して変数に代入
        int winningStreakCount = pref.getInt("WINNING_STREAK_COUNT",0); //取り出すのはint型なので、getInt()を使用
        int lastComHand = pref.getInt("LAST_COM_HAND", 0);
        int lastGameResult = pref.getInt("GAME_RESULT", -1);
        editor.putInt("GAME_COUNT", gameCount + 1); //int型の値を書き込むので、putInt()を使用　値+1して書き込み
        if (lastGameResult == 2 && gameResult == 2) {
            //com連勝の場合
            editor.putInt("WINNING_STREAK_COUNT", winningStreakCount + 1);
        }
        else {
            editor.putInt("WINNING_STREAK_COUNT", 0);
        }
        editor.putInt("LAST_MY_HAND", myHand);  //今回の結果を、前回の結果として共有プリファレンスに書き込み
        editor.putInt("LAST_COM_HAND", comHand);
        editor.putInt("BEFORE_LAST_COM_HAND", lastComHand);
        editor.putInt("GAME_RESULT", gameResult);   //勝負した回数を、前回の結果として共有プリファレンスに書き込み

        editor.commit();    //editorインタフェースを通じて行った変更はcommitメソッドで保存　これやらないと保存されない
        }
    }


