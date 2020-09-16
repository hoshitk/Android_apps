package com.example.janken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //callback_method (p.053~054) override
        super.onCreate(savedInstanceState);     //メソッドのオーバーライドにはsuperを用いて親クラスのメソッドを呼び出す
        setContentView(R.layout.activity_main); //Rクラスは画面レイアウト、文字列、画像などリソースファイルを参照する。int型のIDで管理

        //起動時に共有プリファレンスのデータをクリアする
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear(); //クリアメソッド使用
        editor.commit();

    }

    public void onJankenButtonTapped(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("MY_HAND", view.getId());
        startActivity(intent);
    }

    /*(p.086)
    onCreateメソッドはアクティビティが開始してまず呼ばれるコールバックメソッド
    onJankenButtonTapped()は、じゃんけんボタンの動作を記述したメソッド
    レイアウトエディタで配置されたパーツは全て"ビュー"と呼ばれ、Viewクラスを継承したオブジェクトである
    これらのビューはonCreateメソッドでsetContentView()を実行することでアクティビティに読み込まれて画面に表示される
    intentクラスのインスタントを生成し、これをstartActivity()メソッドに渡す
    intentに情報を格納して(intent.putExtra())、これを次のアクティビティに渡すことで情報を引き継ぐ
    今回はユーザが選んだ手を保存してResultActivityに渡す
    */

}