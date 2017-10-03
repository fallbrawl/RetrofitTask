package com.attracttest.attractgroup.retrofittask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Handler;
import android.widget.TextView;

import com.attracttest.attractgroup.retrofittask.pojos.GitResponse;
import com.attracttest.attractgroup.retrofittask.pojos.Item;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> gitItemsLists;
    private GitItemsAdapter gitItemsAdapter;
    private ListView listView;
    private Handler handler;
    private EditText searchBar;
    private String searchq = "java";

    GitHubService service;

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

        //Adapter's init
        gitItemsLists = new ArrayList<>();
        gitItemsAdapter = new GitItemsAdapter(this, gitItemsLists);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubService.class);


        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                gitItemsAdapter.notifyDataSetChanged();
            }
        };
        startThread();

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchq = searchBar.getText().toString();
                if (!searchq.isEmpty()) {
                    startThread();
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

                intent.putExtra("avatar_url", gitItemsLists.get(i).getOwner().getAvatarUrl());
                intent.putExtra("type", gitItemsLists.get(i).getOwner().getType());
                intent.putExtra("login", gitItemsLists.get(i).getOwner().getLogin());
                intent.putExtra("owners_url", gitItemsLists.get(i).getOwner().getUrl());

                startActivity(intent);
            }
        });

    }

    private void startThread() {
        final Thread tr = new Thread(new Runnable() {
            Message msg;

            @Override
            public void run() {
                try {
                    //юзать енкуеуе только без треда во избежании создания потока в потоке
                    Response<GitResponse> response = service.getListReposByLang(searchq).execute();
                    if (response.isSuccessful()) {

                        Log.d("TAG", "response:" + response.body().getItems().size());
                        gitItemsLists.clear();
                        gitItemsLists.addAll(response.body().getItems());
                        //sendin
                        handler.sendEmptyMessage(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

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
            }
        });
        tr.start();
    }
}
