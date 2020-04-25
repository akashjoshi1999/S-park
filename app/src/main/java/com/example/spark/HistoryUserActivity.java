package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryUserActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    ArrayList<PaymentUser> list;
    MyAdapterForUser myAdapterForUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistoryUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<PaymentUser>();

        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("AccountDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("history")) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        PaymentUser p = dataSnapshot1.getValue(PaymentUser.class);
                        list.add(p);
                    }
                    myAdapterForUser = new MyAdapterForUser(HistoryUserActivity.this, list);
                } else {
                    Toast.makeText(HistoryUserActivity.this,"you don`t have any transaction",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
