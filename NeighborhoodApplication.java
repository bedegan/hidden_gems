package com.cs188group6.hiddengems_dsm;

import android.app.Application;

import io.realm.Realm;

//this code will create a blank application file so Realm can be initialized from the launch of app
public class NeighborhoodApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
