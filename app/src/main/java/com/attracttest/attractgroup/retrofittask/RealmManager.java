package com.attracttest.attractgroup.retrofittask;

import android.content.Context;

import com.attracttest.attractgroup.retrofittask.pojos.Item;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paul on 09.10.17.
 */

class RealmManager {

    private Realm realm;


    public RealmManager() {
        realm = Realm.getDefaultInstance();
    }

    public static void init(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public void writeToDb(ArrayList<Item> items) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
    }

    public RealmResults<Item> getResults(String language) {

       return realm.where(Item.class).contains("language", language, Case.INSENSITIVE).findAll();
    }

    public List<Item> copyDb (RealmResults<Item> items) {
        //arrayListObjects.addAll(RealmClass.getRealmInstance().copyFromRealm(realmManager.getResults(language)));
        return realm.copyFromRealm(items);
    }
}
