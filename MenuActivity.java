package com.cs188group6.hiddengems_dsm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;

public class MenuActivity extends AppCompatActivity {

    private String gemCount, accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final String userID =  getIntent().getStringExtra("user");

        ImageButton uncoverButton = (ImageButton) findViewById(R.id.uncover);
        ImageButton huntButton = (ImageButton) findViewById(R.id.hunt);
        ImageButton profileButton = (ImageButton) findViewById(R.id.profile);

        ImageButton hamburgerMenu = (ImageButton) findViewById(R.id.hamburger_menu_white);

        TextView gemsView = (TextView) findViewById(R.id.profile_gems);

        Realm realm = Realm.getDefaultInstance();

        final User user = realm.where(User.class).equalTo("id", userID).findFirst();

        if (user != null) {
            Gem gem =  user.getCollectedGem();
            if (gem != null) {
                gemCount = String.valueOf(gem.getPoints());
                accountName = user.getName();
                gemsView.setText(gemCount);
            }
        }

        uncoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HuntActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                    intent.putExtra("gem_count", gemCount);
                    intent.putExtra("userName", accountName);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                    startActivity(intent);
                }
            }
        });

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
