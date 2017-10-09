package com.attracttest.attractgroup.retrofittask;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by nexus on 05.10.2017.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        RealmManager.init(this);

    }
}
