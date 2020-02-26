package com.example.spark;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class MapActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener
        , OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private NavigationView navigationView;


    private GoogleMap mMap;
    private FirebaseAuth auth;
    public LatLng bvm;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Marker Mymarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        FirebaseDatabase.getInstance().getReference("AccountDetails").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        boolean x = dataSnapshot.hasChildren();
//                        x = dataSnapshot.hasChild("lat");
                        double Lat = Double.parseDouble(dataSnapshot.child("lat").getValue(String.class));
                        double Lng = Double.parseDouble(dataSnapshot.child("long").getValue(String.class));
                        bvm = new LatLng(Lat, Lng);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bvm, 8.5f));
                        Mymarker = mMap.addMarker(new MarkerOptions()
                                .position(bvm)
                                .title("")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_account:
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    auth.signOut();
                }
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
        }
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(Mymarker)) {
            Intent intent = new Intent(MapActivity.this, VehicleBooking.class);
            startActivity(intent);
        }
        return false;
    }
}
