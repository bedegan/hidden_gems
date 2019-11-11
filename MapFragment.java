package com.cs188group6.hiddengems_dsm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass. This code is for the map tab in the map view activity
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and assign it to the view
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;

    }
    /**
     * Manipulates the map once available.
     * When maps ready to be used this callback is triggered
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near location of the user.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app. code was modified from:tutorials point
     */

    //this map is not working perfectly yet
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Gem> gems = realm.where(Gem.class).findAll();
        final MainActivity mainActivity = (MainActivity) this.getActivity();
        String neighborhood = mainActivity.neighborhood;
        final RealmResults<Gem> neighborhood_gems = gems.where().equalTo("neighborhood", neighborhood).findAllSorted("gemName");

        mMap = googleMap;
        //gets the gem attributes from gem and put in list marker
        for (int counter = 0; counter < neighborhood_gems.size(); counter++) {
            String name = neighborhood_gems.get(counter).getGemName();
            Double lat = neighborhood_gems.get(counter).getLatitude();
            Double lon = neighborhood_gems.get(counter).getLongitude();
            // Add a marker in map and move the camera

            LatLng g1 = new LatLng(lat, lon);

            mMap.addMarker(new MarkerOptions().position(g1).title( "Your Hidden Gem is: " + name ));
        }

        //repositions the camera according to the instructions defined in the update
        LatLng DSM = new LatLng(41.5868, -93.6250);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DSM, 12.5f));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f),2000,null);


       //changes the type of the MAP and gives a different view of ea map
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}
