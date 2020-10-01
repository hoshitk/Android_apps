package com.example.mydiary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements DiaryListFlagment.OnFragmentInteractionListener {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm = mRealm.getDefaultInstance();

        //createTestData();
        showDiaryList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private void createTestData() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //idフィールドの最大値を取得
                Number maxId = mRealm.where(Diary.class).max("id");
                long nextId = 0;
                if (maxId != null) nextId =maxId.longValue() + 1;
                //createObjectではIDを渡してオブジェクトを生成する
                Diary diary = realm.createObject(Diary.class, new Long(nextId));
                diary.title="テストタイトル";
                diary.bodyText="テスト本文です";
                diary.date="Sep 29";
            }
        });
    }

    private void showDiaryList() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("DiaryListFlagment");
        if (fragment == null) {
            fragment = new DiaryListFlagment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.content, fragment, "DiaryListFragment");
            transaction.commit();
        }
    }

    @Override //新規ダイアリ追加処理をここに
    public void onAddDiarySelected() {
       mRealm.beginTransaction();
       Number maxId = mRealm.where(Diary.class).max("id");
       long nextId = 0;
       if (maxId != null) nextId = maxId.longValue() + 1;
       Diary diary = mRealm.createObject(Diary.class, new Long(nextId));
       diary.date = new SimpleDateFormat("MMM d", Locale.US).format(new Date());
       mRealm.commitTransaction();

       InputDiaryFragment inputDiaryFragment = InputDiaryFragment.newInstance(nextId);
       FragmentManager manager = getSupportFragmentManager();
       FragmentTransaction transaction = manager.beginTransaction();
       transaction.replace(R.id.content, inputDiaryFragment, "InputDiaryFragment");
       transaction.addToBackStack(null);
       transaction.commit();
    }
}