package com.attracttest.attractgroup.retrofittask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.attracttest.attractgroup.retrofittask.pojos.Item;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> gitItemsLists;
    private GitItemsAdapter gitItemsAdapter;
    private ListView listView;
    private EditText searchBar;
    private String searchq = "java";
    BroadcastReceiver broadcastReceiver;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Retro");
        searchBar = (EditText) findViewById(R.id.searchToolbar);
        searchBar.setImeActionLabel("Srch", KeyEvent.KEYCODE_SEARCH);

        broadcastReceiver = new BroadcastReceiver() {
            // Actions on message recievin'
            public void onReceive(Context context, Intent intent) {
                gitItemsLists.clear();
                Bundle bundle = intent.getBundleExtra("array");
                gitItemsLists.addAll((ArrayList<Item>) bundle.getSerializable("wow"));
                gitItemsAdapter.notifyDataSetChanged();
                Log.d("staty", "recieved broadcast with size: " + gitItemsLists.size());
            }
        };

        IntentFilter intFilt = new IntentFilter("zhekalysak");
        // Registerin Broadcast
        registerReceiver(broadcastReceiver, intFilt);

        // Creatin' intent

        intent = new Intent(this, MyService.class).putExtra("language", "java");
        Log.e("staty", "sent!");
        // стартуем сервис
        startService(intent);

        // Adapter's init
        gitItemsLists = new ArrayList<>();
        gitItemsAdapter = new GitItemsAdapter(this, gitItemsLists);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchq = searchBar.getText().toString();
                if (!searchq.isEmpty()) {
                    intent = new Intent(getApplicationContext(), MyService.class).putExtra("language", searchq);
                    // стартуем сервис
                    startService(intent);
                }
                searchBar.setText("");

                return true; // Focus will do whatever you put in the logic.
            }
        });

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(gitItemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("staty", gitItemsLists.get(i).getOwner().getLogin());
                Intent intent = new Intent(getBaseContext(), Main2Activity.class);

                intent.putExtra("owner", gitItemsLists.get(i).getOwner());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        stopService(new Intent(this,  MyService.class));
        super.onDestroy();

    }
    //    private void startThread() {
//        final Thread tr = new Thread(new Runnable() {
//            Message msg;
//
//            @Override
//            public void run() {
//                try {
//                    //юзать енкуеуе только без треда во избежании создания потока в потоке
//                    Response<GitResponse> response = service.getListReposByLang(searchq).execute();
//                    if (response.isSuccessful()) {
//
//                        Log.d("TAG", "response:" + response.body().getItems().size());
//                        gitItemsLists.clear();
//                        gitItemsLists.addAll(response.body().getItems());
//                        //sendin
//                        handler.sendEmptyMessage(0);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                service.getListReposByLang(searchq).enqueue(new Callback<Response<ResponseBody>>() {
//                    @Override
//                    public void onResponse(Call<Response<ResponseBody>> call, retrofit2.Response<Response<ResponseBody>> response) {
//
//                        try {
//                            //creatin a message for handler
//                            if(response.isSuccessful()){
//                            msg = handler.obtainMessage(0, response.body().body().string());
//                            //sendin
//                            handler.sendMessage(msg);}
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Response<ResponseBody>> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//            }
//        });
//        tr.start();
//    }
}
