package com.example.spark;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {

    private TextView user_name,user_email,userphone;
    private ImageView userImage;
    private Button nameButton,emailButton,phoneButton,uploadImage;
    private static final int Imageback = 1;
    private StorageReference Folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        user_name = (TextView) findViewById(R.id.text_username);
        user_email = (TextView)findViewById(R.id.text_useremail);
        userphone = (TextView) findViewById(R.id.text_userphone);
        userImage = (ImageView) findViewById(R.id.imageProfile);
        nameButton = (Button) findViewById(R.id.buttonName);
        emailButton = (Button) findViewById(R.id.buttoneEmail);
        phoneButton = (Button) findViewById(R.id.buttonPhone);
        uploadImage = (Button) findViewById(R.id.selectImage);

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

    public void nameEdit(View view) {
        Intent intent = new Intent(AccountActivity.this,nameEditAcivity.class);
        startActivity(intent);

    }

    public void emailEdit(View view) {
        Intent intent = new Intent(AccountActivity.this,emailEditActivity.class);
        startActivity(intent);
    }

    public void phoneEdit(View view) {
        Intent intent = new Intent(AccountActivity.this,phoneEditAvtivity.class);
        startActivity(intent);
    }
}
