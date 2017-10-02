package com.attracttest.attractgroup.retrofittask;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.os.Handler;
import android.widget.TextView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<GitItem> gitItems;
    private GitItemsAdapter gitItemsAdapter;
    private ListView listView;
    Handler h;
    EditText searchBar;
    String searchq = "java";

    GitHubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Retro");
        searchBar = (EditText) findViewById(R.id.searchToolbar);
        searchBar.setImeActionLabel("Srch", KeyEvent.KEYCODE_SEARCH);

        //Adapter's init
        gitItems = new ArrayList<>();
        gitItemsAdapter = new GitItemsAdapter(this, gitItems);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubService.class);


        h = new Handler() {
            public void handleMessage(android.os.Message msg) {

                try {
                    gitItems.clear();
                    gitItems.addAll(JsonUtils.extractFeatureFromJson(String.valueOf(msg.obj)));
                    gitItemsAdapter.notifyDataSetChanged();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        startThread();

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchq = searchBar.getText().toString();
                if (!searchq.isEmpty()) {startThread();}
                searchBar.setText("");

                return true; // Focus will do whatever you put in the logic.
            }
        });

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(gitItemsAdapter);

    }

    private void startThread(){
        final Thread tr = new Thread(new Runnable() {
            Message msg;

            @Override
            public void run() {
                try {
                    //юзать енкуеуе только без треда во избежании создания потока в потоке
                    Response<ResponseBody> response= service.getListReposByLang(searchq).execute();
                    if(response.isSuccessful()){
                        msg = h.obtainMessage(0, response.body().string());
                        //sendin
                        h.sendMessage(msg);}

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
//                            msg = h.obtainMessage(0, response.body().body().string());
//                            //sendin
//                            h.sendMessage(msg);}
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
