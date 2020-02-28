package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class VehicleBooking extends AppCompatActivity {

    private TextView textDirection,textBoooking;
    private ImageView imageView1,imageView2,imageView3;
    private TextView textViewimg1,TextViewimg2,TextViewimg3;
    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_booking);

        textDirection = (TextView)findViewById(R.id.textDirection);
        textBoooking = (TextView)findViewById(R.id.textBooking);
        imageView1 = (ImageView) findViewById(R.id.image1);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);

        textViewimg1 = (TextView) findViewById(R.id.img1);
        TextViewimg2 = (TextView) findViewById(R.id.img2);
        TextViewimg3 = (TextView) findViewById(R.id.img3);

        databaseReference = FirebaseDatabase.getInstance().getReference("AccountDetails").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3");

        textDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textBoooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),carBooking.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageURL1 = dataSnapshot.child("image").child("image1").getValue().toString();
                Picasso.get().load(imageURL1).into(imageView1);

                String imageURL2 = dataSnapshot.child("image").child("image2").getValue().toString();
                Picasso.get().load(imageURL2).into(imageView1);

                String imageURL3 = dataSnapshot.child("image").child("image3").getValue().toString();
                Picasso.get().load(imageURL3).into(imageView1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VehicleBooking.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }
}
