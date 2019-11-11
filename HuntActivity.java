package com.cs188group6.hiddengems_dsm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import java.io.Serializable;
import java.util.ArrayList;


public class HuntActivity extends AppCompatActivity {

    //private RecyclerView.LayoutManager layoutManager;
   // private RecyclerView.Adapter huntAdapter;
   // private RecyclerView huntList;
    private Button huntButton;
    private ImageButton upcomingButtonOne, upcomingButtonTwo, hamburgerMenu;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunt);

       // final ArrayList<Hunt> hunts = new ArrayList<Hunt>();
       // huntList = (RecyclerView) findViewById(R.id.hunt_list);
        huntButton = (Button) findViewById(R.id.hunt_current_button);
        upcomingButtonOne = (ImageButton) findViewById(R.id.upcoming_hunt_one);
        upcomingButtonTwo = (ImageButton) findViewById(R.id.upcoming_hunt_two);
        hamburgerMenu = (ImageButton) findViewById(R.id.hamburger_menu_white_hunt);

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (true) {
                    Intent intent = new Intent(v.getContext(), HuntProgressActivity.class);
                    startActivity(intent);
                }

                else{
                   DialogFragment dialogFrag = new HuntDialogFragment();
                   dialogFrag.show(getSupportFragmentManager(), "dialog");
               }
            }
        });

        upcomingButtonOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HuntNotReady.class);
                startActivity(intent);
            }
        });

        upcomingButtonTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), HuntNotReady.class);
                startActivity(intent);
            }
        });



        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}



