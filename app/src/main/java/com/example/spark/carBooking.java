package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class carBooking extends AppCompatActivity {
    private RecyclerView recyclerView;
    public List<BookTheVehicle> bookTheVehicleslist;
    private DatabaseReference databaseReference;
    private MyBookAdapter myBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookTheVehicleslist = new ArrayList<BookTheVehicle>();
        FirebaseDatabase.getInstance().getReference("AccountDetails").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    BookTheVehicle bookTheVehicle = dataSnapshot1.getValue(BookTheVehicle.class);
                    bookTheVehicleslist.add(bookTheVehicle);
                }
//                myBookAdapter = new MyBookAdapter(carBooking.this,bookTheVehicleslist);
//                recyclerView.setAdapter(myBookAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(carBooking.this,"Something Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
}
