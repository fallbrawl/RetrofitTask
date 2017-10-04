package com.attracttest.attractgroup.retrofittask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.attracttest.attractgroup.retrofittask.pojos.GitResponse;
import com.attracttest.attractgroup.retrofittask.pojos.Item;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService extends Service {
    private ArrayList<Item> gitItemsLists;
    private Retrofit retrofit;
    private GitHubService service;

    final String LOG_TAG = "services";

    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        someTask(intent.getStringExtra("language"));
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

    private void someTask(final String language) {


        final Thread tr = new Thread(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent("zhekalysak");
                try {
                    //юзать енкуеуе только без треда во избежании создания потока в потоке
                    Response<GitResponse> response = service.getListReposByLang(language).execute();
                    if (response.isSuccessful()) {

                        Log.d("TAG", "response:" + response.body().getItems().size());
                        intent.putExtra("array", (ArrayList<Item>) response.body().getItems());
                        //sendin
                        sendBroadcast(intent);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tr.start();

    }
}
