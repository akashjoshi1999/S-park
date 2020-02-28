package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VehicleBooking extends AppCompatActivity {

    private TextView textDirection,textBoooking;
    private ImageView imageView1,imageView2,imageView3;
    private TextView textViewimg1,TextViewimg2,TextViewimg3;

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
}
