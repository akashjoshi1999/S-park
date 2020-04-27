package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryOwnerActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    ArrayList<PaymentOwner> list;
    MyAdapterForOwner myAdapterForOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistoryUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<PaymentOwner>();


        FirebaseDatabase.getInstance().getReference("AccountDetails")
                .child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").child("history")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String key = dataSnapshot1.getKey();
                                PaymentOwner p = dataSnapshot.child(key).getValue(PaymentOwner.class);
                                list.add(p);
                            }
                            myAdapterForOwner = new MyAdapterForOwner(HistoryOwnerActivity.this, list);
                        recyclerView.setAdapter(myAdapterForOwner);
                        Log.v("abc","adapter");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
