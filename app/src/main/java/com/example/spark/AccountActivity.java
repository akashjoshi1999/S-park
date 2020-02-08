package com.example.spark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.viewmodel.RequestCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {

    private TextView user_name,user_email,userphone,changePassword;
    private ImageView userImage;
    private Button dataChange,uploadImage;
    private static final int Imageback = 1;
    private StorageReference Folder;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        user_name = (TextView) findViewById(R.id.text_username);
        user_email = (TextView)findViewById(R.id.text_useremail);
        userphone = (TextView) findViewById(R.id.text_userphone);
        userImage = (ImageView) findViewById(R.id.imageProfile);
        dataChange = (Button) findViewById(R.id.buttonUpdate);
        uploadImage = (Button) findViewById(R.id.selectImage);
        changePassword = (TextView)(findViewById(R.id.text_userChangePassword));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                user_name.setText(userProfile.getUserName());
                user_email.setText(userProfile.getUserEmail());
                userphone.setText(userProfile.getUserPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AccountActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        dataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user_name.getText().toString();
                String email= user_email.getText().toString();
                String phone = userphone.getText().toString();

                UserProfile userProfile = new UserProfile(name, email, phone);
                databaseReference.setValue(userProfile);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,ActivityUserChangePassword.class);
                startActivity(intent);
            }
        });

    }

    public void addVehicle(View view) {
        Intent intent = new Intent(AccountActivity.this,Vehicle.class);
        startActivity(intent);
    }

    public void accountDeactivate(View view) {
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(intent,Imageback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Imageback && requestCode == RESULT_OK){
            Uri ImageData = data.getData();
            final StorageReference storageReference = Folder.child("image"+ImageData.getLastPathSegment());
            storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(AccountActivity.this,"Uploded",Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("image");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("imageurl",String.valueOf(uri));
                            databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AccountActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

}
