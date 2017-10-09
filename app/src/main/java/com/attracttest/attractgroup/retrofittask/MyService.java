package com.attracttest.attractgroup.retrofittask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.attracttest.attractgroup.retrofittask.pojos.GitResponse;
import com.attracttest.attractgroup.retrofittask.pojos.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService extends Service {
    private Handler handler;
    GitHubService gitHubService;
    final String LOG_TAG = "services";
    private Retrofit retrofit;
    private RealmManager realmManager;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        // Obtain retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubService = retrofit.create(GitHubService.class);
        realmManager = new RealmManager();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        if (intent != null)
            readWriteToDb(intent.getStringExtra("language"));
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void readWriteToDb(final String language) {

        final Thread tr = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    //юзать енкуеуе только без треда во избежании создания потока в потоке
                    Response<GitResponse> response = gitHubService.getListReposByLang(language).execute();
                    if (response.isSuccessful()) {

                        List<Item> wow = response.body().getItems();

                        Message msg;
                        msg = handler.obtainMessage(0, 0, 0, wow);

                        handler.sendMessage(msg);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tr.start();

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent("zhekalysak");
                ArrayList<Item> items = (ArrayList<Item>) msg.obj;

                realmManager.writeToDb(items);
                ArrayList<Item> arrayListObjects = new ArrayList<>();
                arrayListObjects.addAll(realmManager.copyDb(realmManager.getResults(language)));

                sendBroadcast(intent.putExtra("array", arrayListObjects));
            }
        };
    }
}

