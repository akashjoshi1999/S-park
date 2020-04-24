package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class PaymentActivity extends AppCompatActivity {

    private EditText editTextOwnerName,editTextTotalAmount;
    private TextView textViewPayment;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String UserName,userUPIID;
    private ProgressDialog progressDialog;
    private int accept,accept_1;
    private String PaymentGooglePayID,PaymentName,GOOGLE_PAY_PACKAGE_NAME,OwnerName;
    private int GOOGLE_PAY_REQUEST_CODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Objects.requireNonNull(getSupportActionBar()).hide();

        GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        GOOGLE_PAY_REQUEST_CODE = 123;

        editTextOwnerName = (EditText) findViewById(R.id.editTextOwnerName);
        editTextTotalAmount = (EditText) findViewById(R.id.editTextAmount);
        textViewPayment = (TextView) findViewById(R.id.textViewPaymentButton);
        PaymentName = "Parking Spot";

        Bundle bundle = getIntent().getExtras();
        final int Amount= bundle.getInt("Amount");
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OwnerProfile ownerProfile = dataSnapshot.getValue(OwnerProfile.class);
                editTextOwnerName.setText(ownerProfile.getName());
                OwnerName = ownerProfile.getName();
                PaymentGooglePayID = ownerProfile.getGid();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseDatabase.getInstance().getReference("AccountDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PaymentProfile paymentProfile = dataSnapshot.getValue(PaymentProfile.class);
                PaymentProfile paymentProfile1 = dataSnapshot.child("googleid").getValue(PaymentProfile.class);
                UserName = paymentProfile.getName();
                userUPIID = paymentProfile1.getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        FirebaseDatabase.getInstance().getReference("AccountDetails")
//            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("googleid")
//            .addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    PaymentProfile paymentProfile = dataSnapshot.getValue(PaymentProfile.class);
//                    userUPIID = paymentProfile.getId();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        editTextTotalAmount.setText(Integer.toString(Amount));
        textViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayUsingUPI( OwnerName,PaymentGooglePayID,PaymentName,Amount);
                progressDialog.setMessage("Transaction Pending....");
                progressDialog.show();
                UserPayment userPayment = new UserPayment(
                        OwnerName,PaymentGooglePayID,Amount
                );
                OwnerPayment ownerPayment = new OwnerPayment(
                        UserName,userUPIID,Amount
                );
                FirebaseDatabase.getInstance().getReference("data")
                        .child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").child("history")
                        .setValue(userPayment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            accept=1;
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference("AccountDetails")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("history")
                        .setValue(ownerPayment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            accept_1 =1;
                        }
                    }
                });
                if(accept == accept_1){
                    Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(PaymentActivity.this, carBooking.class);
                    startActivity(intent);
                }
            }

            private void PayUsingUPI(String name, String upid, String note, int amount) {
                Uri uri =
                        new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", upid)
                                .appendQueryParameter("pn", name)
//                                .appendQueryParameter("mc", "your-merchant-code")
//                                .appendQueryParameter("tr", "your-transaction-ref-id")
                                .appendQueryParameter("tn", note)
                                .appendQueryParameter("am", String.valueOf(amount))
                                .appendQueryParameter("cu", "INR")
//                                .appendQueryParameter("url", "your-transaction-url")
                                .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
            }
        });
    }

}
