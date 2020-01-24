package com.example.spark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;

public class UserAndOwner extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.userandowner);
    }

    public void userSend(View view) {
        Intent user_intent = new Intent(UserAndOwner.this,MapActivity.class);
        startActivity(user_intent);
    }

    public void ownerSend(View view) {
        Intent owner_intent = new Intent(UserAndOwner.this,MapActivity.class);
        startActivity(owner_intent);
    }
}
