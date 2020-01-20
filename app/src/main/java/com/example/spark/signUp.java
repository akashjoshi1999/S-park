package com.example.spark;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView buttonRegister;
    //private Button buttonRegister;
    private EditText editTextEmail;
    //private EditText editTextName;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister =  findViewById(R.id.buttonRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        //
        //editTextName = findViewById(R.id.editName);
        //
        editTextPassword = findViewById(R.id.textPassword);
        textViewSignin = findViewById(R.id.textViewSignin);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        //
        //String name = editTextName.getText().toString().trim();
        //
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Enter valid email address");
            return;
        }
        //
        //if(TextUtils.isEmpty(name)){
        //  editTextName.setError("enter name....");
        //}
        //
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Enter valid password");
            return;
        }

        progressDialog.setMessage("Registering User ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(signUp.this,"User Registered",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(signUp.this, MapActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(signUp.this,"User Not Registered...",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
        if(v == textViewSignin){
            startActivity(new Intent(signUp.this,Login.class));
        }
    }
}

