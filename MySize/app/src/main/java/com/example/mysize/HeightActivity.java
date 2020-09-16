package com.example.mysize;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//preferenceは"好み" 類義語: configuration(config), setting       設定のこと

public class HeightActivity extends AppCompatActivity {
    public static final String HEIGHT = "HEIGHT";   //共有プリファレンスに保存する時のキーとなる文字列を定数として宣言
    private TextView mHeight;   //変数宣言

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);

        mHeight = (TextView) findViewById(R.id.height); //heightのidを持つTextViewを変数に取得する
        SharedPreferences pref =    //デフォルトの共有設定を取得
                PreferenceManager.getDefaultSharedPreferences(this);
        int height = pref.getInt(HEIGHT, 160);  //宣言したキーを用いて共有プリファレンスから値を取得し
        mHeight.setText(String.valueOf(height));    //TextViewに表示

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener   //このメソッドで、いずれかの選択肢が選択された時のコールバックリスナーを設定する
                (new AdapterView.OnItemSelectedListener() { //無名インナークラスで直接渡し
            @Override   //オーバーライドするメソッド一つ目　parentは選択が発生した親ビュー（今回はspinner）、viewは選択された項目そのもの
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent; //parentはAdapterView型で渡されているのでSpinner型にキャスト
                String item = (String) spinner.getSelectedItem();   //getSelectedItemメソッドの戻り値はObject型なのでString型にキャスト
                if(!item.isEmpty()) {
                    mHeight.setText(item);
                }
            }
            @Override   //二つ目
            public void onNothingSelected(AdapterView<?> parent) { }    //何も選択されずにスピナーが閉じられた時の動作　何もしない

                });

            RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup);  //リスナー登録
            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //リスナーセット
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedid) { //ラジオボタン実装にはこのメソッドが必要
                    RadioButton radioButton =(RadioButton) findViewById(checkedid); //引数checkedidにラジオボタンのidが渡される　findViewByIdで取得
                    String value = radioButton.getText().toString();
                    mHeight.setText(value); //TextViewに表示
                }
            });



        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar); //SeekBar型の変数seekBarを用意し、画面レイアウトに配置したSeekBarオブジェクトを代入
        seekBar.setProgress(height);    //共有プリファレンスから取得した身長の値をsetProgressメソッドでシークバーに設定
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {  //シークバーを操作したときに実行するリスナー三つを登録　無名インナークラス
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {    //シークバーの値変更で、その値をtextViewに表示
                String value = String.valueOf(progress);
                mHeight.setText(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    @Override
    protected void onPause() {  //アクティビティが非表示になるときに呼ばれるonPause()
        super.onPause();    //onPause()に共有プリファレンスへの保存処理を記述すれば、保存ボタンとか用意しなくても自動セーブが実装できる
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();  //共有設定を書き換える際にSharedPreferencesのeditorのインスタンスを取得し、これを使ってeditする
        editor.putInt(HEIGHT, Integer.parseInt(mHeight.getText().toString()));
        editor.commit();
    }

    //Tips: Ctrl+o で、そのカーソル位置でオーバーライド可能なメソッドの一覧を表示　リスト表示中にonPauとか入力すれば雛形が出てくる　

    //Tips: ３つのUIパーツには、リスナー用インターフェイスを実装したクラスのインスタンスを、リスナー登録用のメソッドに渡すという流れが共通している
}