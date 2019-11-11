package com.cs188group6.hiddengems_dsm;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static User user;
    public static String neighborhood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        neighborhood = getIntent().getStringExtra("neighborhood");

        //adding tabs to the layout the findView will get access to the tabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //add tab command adds new tabs to our TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("LIST"));
        tabLayout.addTab(tabLayout.newTab().setText("MAP"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //this will tie our tabs to our viewPager with an Adapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final TabPagerAdapter adapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        //code below deals with page change and tab selected listeners
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Realm realm = Realm.getDefaultInstance();
        String username = (String) getIntent().getStringExtra("username");
        user = realm.where(User.class).equalTo("id", username).findFirst();
    }
}
