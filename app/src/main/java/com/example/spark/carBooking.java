package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class carBooking extends AppCompatActivity {
    private RecyclerView recyclerView;
    public List<BookTheVehicle> bookTheVehicleslist;
    private DatabaseReference databaseReference;
    private MyBookAdapter myBookAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference("data").child("OwwBjybbQcQuGqOp3pznp8T9Z7C3");

        FirebaseRecyclerOptions<ParkingSpot> options =
                new FirebaseRecyclerOptions.Builder<ParkingSpot>()
                .setQuery(databaseReference, ParkingSpot.class)
                .build();

        FirebaseRecyclerAdapter<ParkingSpot,bookViewHolder> adapter =
                new FirebaseRecyclerAdapter<ParkingSpot, bookViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull bookViewHolder holder, int position, @NonNull ParkingSpot model) {
                        String parkingSpot = model.getCarStanding();
                        if(parkingSpot.equals("Yes")){
                            holder.textViewChangeSpot.setBackgroundColor(Color.parseColor("#ff0000"));
                            holder.textViewBookSpot.setEnabled(false);
                        } else {
                            holder.textViewChangeSpot.setBackgroundColor(Color.parseColor("#00ff00"));
                            holder.textViewBookSpot.setEnabled(true);
                            holder.textViewChangeSpot.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getApplicationContext(),carBookingBytime.class));
                                }
                            });
                        }
                    }

                    @NonNull
                    @Override
                    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycler_view, parent, false);
                        bookViewHolder holder = new bookViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        bookTheVehicleslist = new ArrayList<BookTheVehicle>();
//        FirebaseDatabase.getInstance().getReference("AccountDetails").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3")
//        .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    BookTheVehicle bookTheVehicle = dataSnapshot1.getValue(BookTheVehicle.class);
//                    bookTheVehicleslist.add(bookTheVehicle);
//                }
////                myBookAdapter = new MyBookAdapter(carBooking.this,bookTheVehicleslist);
////                recyclerView.setAdapter(myBookAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(carBooking.this,"Something Wrong",Toast.LENGTH_LONG).show();
//            }
//        });


    }
}
