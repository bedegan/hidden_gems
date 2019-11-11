package com.cs188group6.hiddengems_dsm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;

public class barcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode);
        String points = getIntent().getStringExtra("points");
        TextView pointsView = (TextView) findViewById(R.id.gem_value);
        String pointsText = "+ " + points;
        String id = getIntent().getStringExtra("id");
        Realm realm = Realm.getDefaultInstance();
        final User user = realm.where(User.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
                                     @Override
                                     public void execute(Realm realm) {
                                         if (user != null) {
                                             String gemID = getIntent().getStringExtra("gem");
                                             Gem gem = realm.where(Gem.class).equalTo("id", gemID).findFirst();
                                             if (gem != null) {
                                                 user.setCollectedGem(gem);
                                                 realm.copyToRealmOrUpdate(user);
                                             }
                                         }
                                     }
                                 });
        pointsView.setText(pointsText);
    }
}
