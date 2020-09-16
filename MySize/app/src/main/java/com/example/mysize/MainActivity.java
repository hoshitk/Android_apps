package com.example.mysize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String NECK = "NECK";  //各値を共有プリファレンスに保存する際にキーとなる文字列を定数として設定
    private static final String SLEEVE = "SLEEVE";
    private static final String WAIST = "WAIST";
    private static final String INSEAM = "INSEAM";
    private EditText editNeck;
    private EditText editSleeve;
    private EditText editWaist;
    private EditText editInseam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);

        String neck = pref.getString(NECK, ""); //onCreate()メソッドが呼ばれるタイミングで共有プリファレンスから値を取得
        String sleeve = pref.getString(SLEEVE, "");
        String waist = pref.getString(WAIST, "");
        String inseam = pref.getString(INSEAM, "");
        editNeck = (EditText) findViewById(R.id.neck);
        editSleeve = (EditText) findViewById(R.id.sleeve);
        editWaist = (EditText) findViewById(R.id.waist);
        editInseam = (EditText) findViewById(R.id.inseam);

        editNeck.setText(neck); //獲得した値を各EditTextビューに表示させる
        editSleeve.setText(sleeve);
        editWaist.setText(waist);
        editInseam.setText(inseam);

        //Intentを用いた画面遷移の実装 (p.118)
        findViewById(R.id.height_button)    //findViewById()でidがR.id.height_buttonであるボタンを取得
                .setOnClickListener(new View.OnClickListener() {    //setOnClickListenerを設定
                    @Override
                    public void onClick(View v) {   //このボタンがタップされるとonClick()が実行
                        Intent intent =
                                new Intent(MainActivity.this, HeightActivity.class);
                        startActivity(intent);
                    }
                });
    }

    //保存ボタンの動作
    public void onSaveTapped(View view) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NECK, editNeck.getText().toString());  //EditTextビューに保存されている値を共有プリファレンスに保存
        editor.putString(SLEEVE, editSleeve.getText().toString());
        editor.putString(WAIST, editWaist.getText().toString());
        editor.putString(INSEAM, editInseam.getText().toString());
        editor.commit();

    }
}