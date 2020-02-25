package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VehicleBooking extends AppCompatActivity {

    private TextView textDirection,textBoooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_booking);

        textDirection = (TextView)findViewById(R.id.textDirection);
        textBoooking = (TextView)findViewById(R.id.textBooking);

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
