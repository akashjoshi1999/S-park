package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.spark.FetchUrl.getUrl;

public class PlaceDirectionMap extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallBack  {

    private GoogleMap mMap;
    private MarkerOptions place1,place2;
    private String API_KEY = "AIzaSyCTL3bmwQi5ZZb_8Cnwb4EHc5g37K3l2w4";
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_direction_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FirebaseDatabase.getInstance().getReference("AccountDetails").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        double Lat = Double.parseDouble(dataSnapshot.child("lat").getValue(String.class));
                        double Lng = Double.parseDouble(dataSnapshot.child("long").getValue(String.class));
                        place1 = new MarkerOptions().position(new LatLng(Lat,Lng)).title("location 1");
                        place2 = new MarkerOptions().position(new LatLng(22.557783, 72.955639)).title("location 2");
                        String URL = getUrl(place1.getPosition(),place2.getPosition());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    @Override
    public void onTaskDone(Object... values) {

    }
}
