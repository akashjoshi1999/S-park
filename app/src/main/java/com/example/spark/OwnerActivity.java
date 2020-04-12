package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class OwnerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up notitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_owner);
        Objects.requireNonNull(getSupportActionBar()).hide();
        databaseReference = FirebaseDatabase.getInstance().getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ParkingSpot> options =
                new FirebaseRecyclerOptions.Builder<ParkingSpot>()
                        .setQuery(databaseReference, ParkingSpot.class)
                        .build();

        FirebaseRecyclerAdapter<ParkingSpot,OwnerHolder> adapter =
                new FirebaseRecyclerAdapter<ParkingSpot, OwnerHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OwnerHolder holder, int position, @NonNull ParkingSpot model) {
                        String parkingSpot = model.getCarStanding();
                        if(parkingSpot.equals("Yes")){
                            holder.spot.setBackgroundColor(Color.parseColor("#ff0000"));
                        } else {
                            holder.spot.setBackgroundColor(Color.parseColor("#00ff00"));
                        }
                    }

                    @NonNull
                    @Override
                    public OwnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_owner, parent, false);
                        OwnerHolder holder = new OwnerHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_history:
                startActivity(new Intent(getApplicationContext(), HistoryOwnerActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                firebaseAuth = FirebaseAuth.getInstance();
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseAuth.signOut();
                }
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
        }
        return false;
    }
}
