package com.example.spark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private EditText Email;
    private EditText Password;
    private ImageView Login;
    private TextView RegisterUser;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private UserProfileChangeRequest profileUpdates;
    private Uri uri;
    private Uri.Builder builder;
    private final String URI = "https://api.qrserver.com/v1/create-qr-code/?size=150x150";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        progressDialog = new ProgressDialog(this);
        Email = findViewById(R.id.editText);
        Password = findViewById(R.id.editText2);
        RegisterUser = findViewById(R.id.textView2);
        Login = findViewById(R.id.imageView4);
        builder = new Uri.Builder();
        profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(builder.build())
                .build();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {
                    if(firebaseAuth.getCurrentUser().getPhotoUrl()==null){
                        uri = Uri.parse(URI);
                        builder = uri.buildUpon();
                        builder.appendQueryParameter("data",firebaseAuth.getCurrentUser().getUid());
                        firebaseAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(builder.build()).build());
                    }
                    startActivity(new Intent(getApplicationContext(), MapActivity.class));
                }
            }
        };

        firebaseAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        firebaseAuth.getCurrentUser();
        RegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signUp.class));
            }
        });
    }

    private void loginUser(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Email.setError("Enter valid email address");
            return;
        }
        if(TextUtils.isEmpty(password)){
            Password.setError("Enter valid password");
            return;
        }

        progressDialog.setMessage("Authenticating User ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this,"User Authenticated...",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(Login.this, MapActivity.class);
                    //Log.v(TAG,"User id " + firebaseAuth.getCurrentUser().getUid());
                    Log.v(TAG,"User id " + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this,"User Not Authenticated...",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }
}
