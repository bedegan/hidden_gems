package com.cs188group6.hiddengems_dsm;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;

    // The shared preference parameter
    int PRIVATE_MODE = 0;

    private static final String PREFERENCES_FILE_NAME = "preferences";
    private static final String FIRST_LAUNCH = "FirstLaunch";

    public PreferenceManager(Context context) {
        this._context = context;
        preferences = _context.getSharedPreferences(PREFERENCES_FILE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setFirstLaunch(boolean isFirstTime) {
        editor.putBoolean(FIRST_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(FIRST_LAUNCH, true);
    }
}
