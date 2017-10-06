package com.attracttest.attractgroup.retrofittask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.attracttest.attractgroup.retrofittask.pojos.Owner;
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


        Owner owner = (Owner) getIntent().getSerializableExtra("owner");
        tvType.setText(owner.getType());
        tvLogin.setText(owner.getLogin());
        tvUrl.setText(owner.getUrl());

        Picasso.with(this).load(owner.getAvatarUrl()).placeholder(R.mipmap.ic_launcher).fit().into(avatar);

    }
}
