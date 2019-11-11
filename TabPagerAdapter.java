package com.cs188group6.hiddengems_dsm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    //variables
    int mNumOfTabs;

    //constructor
    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        //this calls the default constructor we cannot see will need to put this in
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    //getItem will return a specific tab
    public Fragment getItem(int position){
        switch (position) {
            //ListFrag is set to zero
            case 0:
                ListFragment tab1 = new ListFragment();
                return tab1;
            //rankings is set to 1
            case 1:
                MapFragment tab2 = new MapFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
