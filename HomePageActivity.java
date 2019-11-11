package com.cs188group6.hiddengems_dsm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class HomePageActivity extends Activity {
//this code is designed for the grid view of gems
    private Button courtAve, eastVillage, ingersoll, graysLake, downtown, valleyJunction;
    private ImageButton hamburgerMenu;
    private List<Gem> gems = new ArrayList<>();
    private String user;

    private void startActivityBasedOnNeighborhood(String neighborhood) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("username", user);
        intent.putExtra("neighborhood", neighborhood);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        user = getIntent().getStringExtra("user");

        courtAve = (Button) findViewById(R.id.courtAve_button);
        eastVillage = (Button) findViewById(R.id.eastVillage_button);
        ingersoll = (Button) findViewById(R.id.ingersoll_button);
        graysLake = (Button) findViewById(R.id.graysLake_button);
        downtown = (Button) findViewById(R.id.downtown_button);
        valleyJunction = (Button) findViewById(R.id.valleyJunction_button);

        hamburgerMenu = (ImageButton) findViewById(R.id.hamburger_menu);


        courtAve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBasedOnNeighborhood("Court Ave District");
            }
        });

        eastVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBasedOnNeighborhood("East Village");
            }
        });

        ingersoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBasedOnNeighborhood("Ingersoll");
            }
        });

        graysLake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBasedOnNeighborhood("Gray's Lake");
            }
        });

        downtown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBasedOnNeighborhood("Downtown DSM");
            }
        });

        valleyJunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBasedOnNeighborhood("Valley Junction");

            }
        });

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream is = getResources().openRawResource(R.raw.gems_revision);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, Charset.forName("UTF-8"))
                );

                String line = "";
                Integer count = 0;

                try {
                    while ((line = reader.readLine()) != null) {
                        // Split by a tab
                        String[] tokens = line.split("\t");
                        // Read data
                        if (tokens.length > 0) {
                            Gem sample = new Gem();
                            sample.setId(count.toString());
                            count += 1;
                            sample.setGemName(tokens[0]);
                            sample.setAddress(tokens[1]);
                            sample.setLatitude(Double.parseDouble(tokens[2]));
                            sample.setLongitude(Double.parseDouble(tokens[3]));
                            sample.setHours(tokens[4]);
                            sample.setNeighborhood(tokens[5]);
                            sample.setPoints(Integer.parseInt(tokens[6]));
                            try {
                                int resourceID = getResources().getIdentifier(tokens[7], "drawable", getPackageName());
                                BitmapDrawable image = (BitmapDrawable) getDrawable(resourceID);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] imageInByte = baos.toByteArray();
                                sample.setImage(imageInByte);
                            } catch (Error e) {
                                Log.d("CREATION", "Failed to find image for " + tokens[7]);
                            }
                            sample.setDescription(tokens[8]);
                            if (tokens.length > 9) {
                                String[] tabList = tokens[9].split(",");
                                String intermediate = String.join("  + ", tabList);
                                String tabs = "+ " + intermediate;
                                sample.setTags(tabs);
                            } else {
                                sample.setTags("");
                            }
                            realm.copyToRealmOrUpdate(sample);
                            Log.d("CREATION", sample.toString());
                        }
                    }
                } catch(IOException e) {
                    Log.d("CREATION", "Failed to read input file.");
                }
            }
        });
    }
}
