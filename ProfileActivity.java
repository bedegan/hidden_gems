package com.cs188group6.hiddengems_dsm;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String gem_count = getIntent().getStringExtra("gem_count");
        String userID = getIntent().getStringExtra("user");

        Realm realm = Realm.getDefaultInstance();

        ImageButton hamburgerMenu = (ImageButton) findViewById(R.id.hamburger_menu_white_profile);
        TextView gemCount  = (TextView) findViewById(R.id.gem_count_profile);
        TextView userName = (TextView) findViewById(R.id.user_name);
        ImageView comingSoon = (ImageView) findViewById(R.id.coming_soon);

        User user = realm.where(User.class).equalTo("id", userID).findFirst();

        if (user != null) {
            userName.setText(user.getName());
        }

        if (gem_count != null) {
            gemCount.setText(gem_count);
        }

        comingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
