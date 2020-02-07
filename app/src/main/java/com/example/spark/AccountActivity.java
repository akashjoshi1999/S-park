package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void addVehicle(View view) {
        Intent intent = new Intent(AccountActivity.this,Vehicle.class);
        startActivity(intent);
    }

    public void accountDeactivate(View view) {
    }
}
