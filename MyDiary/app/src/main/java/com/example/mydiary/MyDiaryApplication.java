package com.example.mydiary;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyDiaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
