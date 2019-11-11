package com.cs188group6.hiddengems_dsm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class HuntProgressActivity extends AppCompatActivity {

    private RecyclerView progressList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter progressAdapter;
    private TextView huntName, huntTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunt_progress);

        //connects data to realm for storage
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<Gem> gems = realm.where(Gem.class).equalTo("neighborhood","Hunt").findAll();
        progressList = (RecyclerView) findViewById(R.id.progress_view);
        huntName = (TextView) findViewById(R.id.huntTitle);
        huntTime = (TextView) findViewById(R.id.huntTime);

        huntName.setText("HOLIDAY HUNT");
        huntTime.setText("open: october 25 - december 25");


        layoutManager = new LinearLayoutManager(this);
        progressList.setLayoutManager(layoutManager);

        ProgressRVClickListener listener = new ProgressRVClickListener() {
            @Override
            public void onClick(View view, int position) {
                //needs to navigate to list view of hunt
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.putExtra("neighborhood","Hunt");
                startActivity(intent);
            }
        };

       progressAdapter = new ProgressAdapter(this, gems, listener);
       progressList.setAdapter(progressAdapter);
    }
}
