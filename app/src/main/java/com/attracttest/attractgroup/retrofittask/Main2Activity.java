package com.attracttest.attractgroup.retrofittask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
    private TextView tvType;
    private TextView tvLogin;
    private TextView tvUrl;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvType = (TextView) findViewById(R.id.owners_type);
        tvLogin = (TextView) findViewById(R.id.owners_login);
        tvUrl = (TextView) findViewById(R.id.owners_url);
        avatar = (ImageView) findViewById(R.id.owners_avatar);

        tvType.setText(getIntent().getStringExtra("type"));
        tvLogin.setText(getIntent().getStringExtra("login"));
        tvUrl.setText(getIntent().getStringExtra("owners_url"));

        Picasso.with(this).load(getIntent().getStringExtra("avatar_url")).placeholder(R.mipmap.ic_launcher).fit().into(avatar);

    }
}
