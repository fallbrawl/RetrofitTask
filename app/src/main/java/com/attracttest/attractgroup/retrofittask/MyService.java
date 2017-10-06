package com.attracttest.attractgroup.retrofittask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.attracttest.attractgroup.retrofittask.pojos.GitResponse;
import com.attracttest.attractgroup.retrofittask.pojos.Item;
import com.attracttest.attractgroup.retrofittask.pojos.Owner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService extends Service {
    private ArrayList<Item> gitItemsLists;
    private Retrofit retrofit;
    private GitHubService service;
    private Realm realm;
    private Handler handler;

    final String LOG_TAG = "services";

    public void onCreate() {
        super.onCreate();
        // Obtain realm instance
        realm = Realm.getDefaultInstance();

        // Obtain retrofit instance
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

                try {
                    //юзать енкуеуе только без треда во избежании создания потока в потоке
                    Response<GitResponse> response = service.getListReposByLang(language).execute();
                    if (response.isSuccessful()) {

                        //Log.d("TAGGY", "response:" + response.body().getItems().size());
                        List<Item> wow = response.body().getItems();
                        Log.d("TAGGY", "response:" + wow.size());
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("array", (ArrayList<Item>) wow);

                        Message msg;
                        msg = handler.obtainMessage(0, 0,
                                0, wow);

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
                Log.e("staty", "frompARSE " + items.size());

                for (Item item : items
                        )
                {
                    realm.beginTransaction();
                    Item gitItems = realm.createObject(Item.class);
                    Owner ownerItems = realm.createObject(Owner.class);
                    gitItems.set_id(item.get_id());
                    gitItems.setName(item.getName());
                    gitItems.setFullName(item.getFullName());

                    ownerItems.setUrl(item.getOwner().getUrl());
                    ownerItems.setLogin(item.getOwner().getLogin());
                    ownerItems.setWowid(item.getOwner().getWowid());
                    ownerItems.setAvatarUrl(item.getOwner().getAvatarUrl());
                    gitItems.setOwner(ownerItems);

                    gitItems.set_private(item.get_private());
                    gitItems.setHtmlUrl(item.getHtmlUrl());
                    gitItems.setDescription(item.getDescription());
                    gitItems.setForky(item.getForky());
                    gitItems.setUrl(item.getUrl());
                    gitItems.setLanguage(item.getLanguage());
                    realm.commitTransaction();

                }

                //int o = realm.where(Item.class).findAll();

                RealmResults<Item> resultFromDb = realm.where(Item.class).findAll();
                ArrayList<Item> arrayListObjects = new ArrayList<>(resultFromDb);

                Bundle args = new Bundle();
                args.putSerializable("wow", arrayListObjects);

                Log.e("staty", "fromDB " + arrayListObjects.size());
                //realm.where(Item.class).findAll().clear();

                //sendin

                //Log.e("staty", "fromDB " + resultFromDb.size());

                sendBroadcast(intent.putExtra("array", args));

//                            smth = new Item(item.get_id(), item.getName(), item.getFullName(), item.getOwner(),
//                                    item.get_private(), item.getHtmlUrl(), item.getDescription(),
//                                    item.getForky(), item.getUrl(), item.getLanguage());


            }
        };
    }

    ;
}

