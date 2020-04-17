package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ActivityUserChangePassword extends AppCompatActivity {

    private EditText currentPass,newPass,confirmNewPass;
    private TextView passwordChange;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        passwordChange = (TextView) findViewById(R.id.buttonUpdatePassword);
        currentPass = (EditText) findViewById(R.id.editCurrentPassword);
        newPass = (EditText) findViewById(R.id.editNewPassword);
        confirmNewPass = (EditText) findViewById(R.id.editConfirmNewPassword);

        //firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        final String currentPassword = currentPass.getText().toString();
        final String newPassword = newPass.getText().toString();
        final String confirmNewPassword = confirmNewPass.getText().toString();

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

        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = user.getEmail();

                AuthCredential authCredential = EmailAuthProvider.getCredential(email,currentPassword);
                user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if(newPassword == confirmNewPassword){
                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ActivityUserChangePassword.this,"Password Changed",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ActivityUserChangePassword.this,MapActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ActivityUserChangePassword.this,"Both password nis different",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

//        firebaseUser.updatePassword(currentPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(ActivityUserChangePassword.this,"Password Changed",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(ActivityUserChangePassword.this,ActivityUserChangePassword.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(ActivityUserChangePassword.this,"Failed",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


}
