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
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<GitItem> gitItems;
    private GitItemsAdapter gitItemsAdapter;
    private ListView listView;
    Handler h;
    EditText searchBar;
    String searchq = "java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Retro");
        searchBar = (EditText) findViewById(R.id.searchToolbar);
        searchBar.setImeActionLabel("wow", KeyEvent.KEYCODE_SEARCH);

        //Adapter's init
        gitItems = new ArrayList<>();
        gitItemsAdapter = new GitItemsAdapter(this, gitItems);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        final GitHubService service = retrofit.create(GitHubService.class);

        final Thread tr = new Thread(new Runnable() {
            Message msg;

            @Override
            public void run() {
                Call<ResponseBody> repos = service.getListReposByLang(searchq);
                repos.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                        try {
                            //creatin a message for handler
                            msg = h.obtainMessage(0, response.body().string());
                            //sendin
                            Log.e("staty", "sebt");
                            h.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        tr.run();
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {

                try {
                    gitItemsAdapter.clear();
                    gitItems = JsonUtils.extractFeatureFromJson(String.valueOf(msg.obj));
                    Log.e("staty", "handler gets a message!");
                    gitItemsAdapter.addAll(gitItems);
//                    Log.e("staty", gitItems.get(0).getLogin());
                    gitItemsAdapter.notifyDataSetChanged();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchq = searchBar.getText().toString();
                if (!searchq.isEmpty()) {tr.run();}
                Log.e("staty", "searched");
                searchBar.setText("");

                return true; // Focus will do whatever you put in the logic.
            }
        });

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(gitItemsAdapter);

    }
}
