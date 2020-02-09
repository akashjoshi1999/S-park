package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
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
        final String newPassword = newPass.getText().toString();
        String confirmNewPassword = confirmNewPass.getText().toString();

        if(TextUtils.isEmpty(currentPassword)){
            currentPass.setError("Current password is required");
            return;
        }
//        if(TextUtils.isEmpty(newPassword)){
//            newPass.setError("New password is required");
//            return;
//        }
//        if(TextUtils.isEmpty(confirmNewPassword)){
//            confirmNewPass.setError("New confirm password is required");
//            return;
//        }

        firebaseUser.updatePassword(currentPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ActivityUserChangePassword.this,"Password Changed",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityUserChangePassword.this,ActivityUserChangePassword.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivityUserChangePassword.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
