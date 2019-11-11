package com.cs188group6.hiddengems_dsm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass. Code is for the list tab in the map view activity
 */
public class ListFragment extends Fragment {

    private RecyclerView listFragment;
    private TextView neighborhoodText;
    private String neighborhood;
    private User user;
    private LayoutInflater activityInflater;

    private Location lastLocation;
    private LocationManager locationManager;
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private Integer granted = PackageManager.PERMISSION_GRANTED;

    private LocationListener llistener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and assign it to the view
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        activityInflater = inflater;

        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.removeUpdates(llistener);
        }
        if ( ContextCompat.checkSelfPermission(getContext(), permissions[0]) != granted ||
                ContextCompat.checkSelfPermission(getContext(), permissions[1]) != granted) {
            if (this.getActivity() != null) {
                ActivityCompat.requestPermissions(this.getActivity(), permissions, 1);
            }
        }

        listFragment = (RecyclerView)view.findViewById(R.id.fragment_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listFragment.setLayoutManager(layoutManager);
        neighborhoodText = (TextView)view.findViewById(R.id.big_neighborhood);
        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<Gem> gems = realm.where(Gem.class).findAll();
        final RealmResults<Gem> neighborhood_gems = gems.where().equalTo("neighborhood", neighborhood).findAllSorted("gemName");
        final MainActivity mainActivity = (MainActivity) this.getActivity();

        if (ContextCompat.checkSelfPermission(getContext(), permissions[0]) == granted &&
                ContextCompat.checkSelfPermission(getContext(), permissions[1]) == granted) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, llistener);
        }

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("CREATION", "Clicked on a bin!");
                final Gem gem = (Gem) neighborhood_gems.get(position);
                Log.d("LOADING", gem.toString());

                if (gem.getImage() != null) {
                    View layout = activityInflater.inflate(R.layout.gem_specific_layout,
                            (ViewGroup) view.findViewById(R.id.gem_specific_view));
                    final PopupWindow pw = new PopupWindow(layout, Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.MATCH_PARENT, true);

                    ImageView gem_image = (ImageView) layout.findViewById(R.id.gem_image);

                    Button gem_button = (Button) layout.findViewById(R.id.gem_specific_button);

                    TextView gem_name = (TextView)layout.findViewById(R.id.gem_specific_name);
                    TextView gem_neighborhood = (TextView)layout.findViewById(R.id.gem_specific_neighborhood);
                    TextView gem_address = (TextView)layout.findViewById(R.id.gem_specific_address);
                    TextView gem_description = (TextView)layout.findViewById(R.id.gem_specific_description);
                    TextView gem_point = (TextView)layout.findViewById(R.id.gem_specific_point);
                    TextView gem_tags = (TextView)layout.findViewById(R.id.gem_specific_tags);

                    byte[] image = gem.getImage();
                    Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                    gem_image.setImageBitmap(Bitmap.createBitmap(bmp));

                    /* Normally this would have some test of whether or not the user was near
                       the given gem, but the emulator will always report the LAT/LONG of a Google
                       building. So, the best test we can do for now is check whether or not it
                       gets set. To see the disabled view, set the if statement to be if (false) */
                    if (ContextCompat.checkSelfPermission(getContext(), permissions[0]) == granted &&
                            ContextCompat.checkSelfPermission(getContext(), permissions[1]) == granted &&
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null &&
                            user != null) {
                        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d("LATITUDE", String.valueOf(lastLocation.getLatitude()));
                        Log.d("LONGITUDE", String.valueOf(lastLocation.getLongitude()));
                        gem_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), barcodeActivity.class);
                                intent.putExtra("points", gem.getPoints());
                                intent.putExtra("user", user.getId());
                                intent.putExtra("gem", gem.getId());
                                startActivity(intent);
                            }
                        });
                    } else {
                        Log.d("LOCATION", "null");
                        gem_button.setClickable(false);
                        gem_button.setText("C̶H̶E̶C̶K̶ ̶I̶N̶");
                        gem_button.setBackgroundColor(getResources().getColor(R.color.light_grey));
                    }

                    gem_name.setText(gem.getGemName());
                    gem_neighborhood.setText(gem.getNeighborhood());
                    gem_address.setText(gem.getAddress());
                    gem_description.setText(gem.getDescription());
                    gem_point.setText(gem.getPoints());
                    gem_tags.setText(gem.getTags());

                    pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

                    ImageButton close = (ImageButton) layout.findViewById(R.id.gem_specific_back);
                    close.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            pw.dismiss();
                        }
                    });
                }
            }
        };

        user = mainActivity.user;
        neighborhood = mainActivity.neighborhood;
        neighborhoodText.setText(neighborhood);
        MainAdapter adapter = new MainAdapter(getActivity(), neighborhood_gems, listener);
        listFragment.setAdapter(adapter);
    }
}

