package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityUserChangePassword extends AppCompatActivity {

    private Button passwordChange;
    private EditText currentPass,newPass,confirmNewPass;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);

        passwordChange = (Button)findViewById(R.id.buttonUpdatePassword);
        currentPass = (EditText) findViewById(R.id.editCurrentPassword);
        newPass = (EditText) findViewById(R.id.editNewPassword);
        confirmNewPass = (EditText) findViewById(R.id.editConfirmNewPassword);

        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        String currentPassword = currentPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String confirmNewPassword = confirmNewPass.getText().toString();

        if(TextUtils.isEmpty(currentPassword)){
            currentPass.setError("Current password is required");
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            newPass.setError("New password is required");
            return;
        }
        if(TextUtils.isEmpty(confirmNewPassword)){
            confirmNewPass.setError("New confirm password is required");
            return;
        }
        FirebaseDatabase.getInstance().getReference("AccountDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
}
