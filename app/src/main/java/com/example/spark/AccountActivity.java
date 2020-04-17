package com.example.spark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AccountActivity extends AppCompatActivity {

    private EditText user_name,user_email,userphone;
    private static final int PICK_IMAGE_REQUEST = 234;
    private TextView changePassword,userDeactivate,dataChange;
    private ImageView userImage;
    private Button uploadImage;
    public String account,name,email,phone,url,setUpUrl;
    private static final int Imageback = 1;
    private StorageReference Folder;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase,mDatabase_url;
    private Uri filePath;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        user_name = (EditText) findViewById(R.id.text_username);
        user_email = (EditText) findViewById(R.id.text_useremail);
        userphone = (EditText) findViewById(R.id.text_userphone);
        userImage = (ImageView) findViewById(R.id.imageProfile);
        dataChange = (TextView) findViewById(R.id.buttonUpdate);
        uploadImage = (Button) findViewById(R.id.selectImage);
        changePassword = (TextView)(findViewById(R.id.text_userChangePassword));
        userDeactivate = (TextView)findViewById(R.id.text_userDeactivate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        final DatabaseReference databaseReference = firebaseDatabase.getReference("AccountDetails").child(Objects.requireNonNull(firebaseAuth.getUid()));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                setUpUrl = userProfile.getUrl();
//                Glide.with(AccountActivity.this).load(setUpUrl).into(userImage);
                account = userProfile.getAccount();
                user_name.setText(userProfile.getName());
                user_email.setText(userProfile.getEmail());
                userphone.setText(userProfile.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AccountActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

//        userImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showFileChooser();
//            }
//        });
//
//
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //checking if file is available
//                if (filePath != null) {
//                    //displaying progress dialog while image is uploading
//                    final ProgressDialog progressDialog = new ProgressDialog(AccountActivity.this);
//                    progressDialog.setTitle("Uploading");
//                    progressDialog.show();
//
//                    //getting the storage reference
//                    StorageReference sRef = storageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));
//
//                    //adding the file to reference
//                    sRef.putFile(filePath)
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    //dismissing the progress dialog
//                                    progressDialog.dismiss();
//
//                                    //displaying success toast
//                                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
//
//                                    //creating the upload object to store uploaded image details
//                                    UserProfile upload = new UserProfile(account,name, email, phone, taskSnapshot.getMetadata().getReference().getDownloadUrl());
//
//                                    //adding an upload to firebase database
//                                    String uploadId = mDatabase.push().getKey();
//                                    mDatabase.child(uploadId).setValue(upload);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            })
//                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                    //displaying the upload progress
//                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
//                                }
//                            });
//                } else {
//                    //display an error if no file is selected
//                }
//            }
//        });


        userDeactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AccountActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will results in completely removing your account from the system and you won't be able to access the app.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AccountActivity.this,"Account deleted",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AccountActivity.this,Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(AccountActivity.this, (CharSequence) task.getException(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        dataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = user_name.getText().toString();
                email= user_email.getText().toString();
                phone = userphone.getText().toString();

//                mDatabase_url = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
//                mDatabase_url.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        UserProfile user = dataSnapshot.getValue(UserProfile.class);
//                        url = user.getUrl().;
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                UserProfile userProfile = new UserProfile(account,name, email, phone);
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

//    private Object getFileExtension(Uri filePath) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(filePath));
//    }
//
//    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }


    public void addVehicle(View view) {
        Intent intent = new Intent(AccountActivity.this,Vehicle.class);
        startActivity(intent);
    }



    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(intent,Imageback);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == Imageback && requestCode == RESULT_OK){
//            Uri ImageData = data.getData();
//            final StorageReference storageReference = Folder.child("Images").child(ImageData.getLastPathSegment());
//
////            storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                @Override
////                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Uri downloadUrl = taskSnapshot;
////                    newStudent.child("image").setValue(downloadUrl);
////                }
////            });
//
//
//            storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Toast.makeText(AccountActivity.this,"Uploded",Toast.LENGTH_SHORT).show();
//                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("image");
//                            HashMap<String, String> hashMap = new HashMap<>();
//                            hashMap.put("imageurl",String.valueOf(uri));
//                            databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(AccountActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    });
//                }
//            });
//        }
//    }

}
