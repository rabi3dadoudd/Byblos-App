package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceRequest extends AppCompatActivity {
    TextView name, rate, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
    TextView s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    CheckBox approved;
    FirebaseDatabase mDatabase;
    String value2;
    String branch;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("username");
        branch = extras.getString("branch");
        value2 = value;
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("Users").child(branch).child("Service Requests").child(value);
        name = findViewById(R.id.textView17);
        rate = findViewById(R.id.textView18);
        l1 = findViewById(R.id.textView19);
        l2 = findViewById(R.id.textView20);
        l3 =  findViewById(R.id.textView21);
        l4 =  findViewById(R.id.textView22);
        l5 = findViewById(R.id.textView31);
        l6 = findViewById(R.id.textView32);
        l7 = findViewById(R.id.textView33);
        l8 = findViewById(R.id.textView34);
        l9 = findViewById(R.id.textView35);
        l10 = findViewById(R.id.textView36);
        s1 = findViewById(R.id.textView3);
        s2 = findViewById(R.id.textView23);
        s3 = findViewById(R.id.textView24);
        s4 = findViewById(R.id.textView25);
        s5 = findViewById(R.id.textView26);
        s6 = findViewById(R.id.textView27);
        s7 = findViewById(R.id.textView28);
        s8 = findViewById(R.id.textView29);
        s9 = findViewById(R.id.textView30);
        s10 = findViewById(R.id.textView13);
        approved = findViewById(R.id.approveRequest);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("first")){
                    s1.setVisibility(View.VISIBLE);
                    s1.setText("First Name");
                    l1.setVisibility(View.VISIBLE);
                    l1.setText(snapshot.child("first").getValue().toString());
                }
                if (snapshot.hasChild("last")){
                    s2.setVisibility(View.VISIBLE);
                    s2.setText("Last Name");
                    l2.setVisibility(View.VISIBLE);
                    l2.setText(snapshot.child("last").getValue().toString());
                }
                if (snapshot.hasChild("dob")){
                    s3.setVisibility(View.VISIBLE);
                    s3.setText("Birthday");
                    l3.setVisibility(View.VISIBLE);
                    l3.setText(snapshot.child("dob").getValue().toString());
                }
                if (snapshot.hasChild("address")){
                    s4.setVisibility(View.VISIBLE);
                    s4.setText("Address");
                    l4.setVisibility(View.VISIBLE);
                    l4.setText(snapshot.child("address").getValue().toString());
                }
                if (snapshot.hasChild("email")){
                    s5.setVisibility(View.VISIBLE);
                    s5.setText("Email");
                    l5.setVisibility(View.VISIBLE);
                    l5.setText(snapshot.child("email").getValue().toString());
                }
                if(snapshot.child("type").getValue().toString().equals("Truck Rental") || snapshot.child("type").getValue().toString().equals("Car Rental") ){

                    if (snapshot.hasChild("license")){
                        s6.setVisibility(View.VISIBLE);
                        s6.setText("License Type (G, G1, G2)");
                        l6.setVisibility(View.VISIBLE);
                        l6.setText(snapshot.child("license").getValue().toString());
                    }
                    if (snapshot.hasChild("pickup")){
                        s7.setVisibility(View.VISIBLE);
                        s7.setText("Pickup Date and Time");
                        l7.setVisibility(View.VISIBLE);
                        l7.setText(snapshot.child("pickup").getValue().toString());
                    }
                    if (snapshot.hasChild("returntime")){
                        s8.setVisibility(View.VISIBLE);
                        s8.setText("Return Date and Time");
                        l8.setVisibility(View.VISIBLE);
                        l8.setText(snapshot.child("returntime").getValue().toString());
                    }

                }
                if(snapshot.child("type").getValue().toString().equals("Car Rental")){
                    if (snapshot.hasChild("car")){
                        s9.setVisibility(View.VISIBLE);
                        s9.setText("Preferred Car Type (compact, intermediate, or SUV)");
                        l9.setVisibility(View.VISIBLE);
                        l9.setText(snapshot.child("car").getValue().toString());

                    }

                } if(snapshot.child("type").getValue().toString().equals("Truck Rental")){

                    if (snapshot.hasChild("kmdriven")){
                        s9.setVisibility(View.VISIBLE);
                        s9.setText("Max Number of Km to be driven");
                        l9.setVisibility(View.VISIBLE);
                        l9.setText(snapshot.child("kmdriven").getValue().toString());
                    }
                    if (snapshot.hasChild("areaused")){
                        s10.setVisibility(View.VISIBLE);
                        s10.setText("Area Used");
                        l10.setVisibility(View.VISIBLE);
                        l10.setText(snapshot.child("areaused").getValue().toString());
                    }

                }
                if(snapshot.child("type").getValue().toString().equals("Moving Assistance")){
                    if (snapshot.hasChild("startlocation")){
                        s6.setVisibility(View.VISIBLE);
                        s6.setText("Moving Start Location");
                        l6.setVisibility(View.VISIBLE);
                        l6.setText(snapshot.child("startlocation").getValue().toString());
                    }
                    if (snapshot.hasChild("endlocation")){
                        s7.setVisibility(View.VISIBLE);
                        s7.setText("Moving End Location");
                        l7.setVisibility(View.VISIBLE);
                        l7.setText(snapshot.child("endlocation").getValue().toString());
                    }
                    if (snapshot.hasChild("nummovers")){
                        s8.setVisibility(View.VISIBLE);
                        s8.setText("Number of Movers Needed");
                        l8.setVisibility(View.VISIBLE);
                        l8.setText(snapshot.child("nummovers").getValue().toString());
                    }
                    if (snapshot.hasChild("numboxes")){
                        s9.setVisibility(View.VISIBLE);
                        s9.setText("Number of Boxes Needed");
                        l9.setVisibility(View.VISIBLE);
                        l9.setText(snapshot.child("numboxes").getValue().toString());
                    }
                }
                name.setText(snapshot.child("type2").getValue().toString());
                if(snapshot.hasChild("approved")){
                    if(snapshot.child("approved").getValue().toString().equals("true")){
                        approved.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void backBttn(View view){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("Users").child(branch).child("Service Requests").child(value2);
        if(approved.isChecked()){
            ref.child("approved").setValue("true");
        }else{
            ref.child("approved").setValue("false");
        }
        Intent i = new Intent(ServiceRequest.this, ServiceRequests.class);
        i.putExtra("username",branch);
        startActivity(i);
    }
}