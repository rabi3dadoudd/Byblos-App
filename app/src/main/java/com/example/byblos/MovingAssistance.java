package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MovingAssistance extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    Button addService;
    EditText name;
    EditText rate;
    CheckBox first, last, dob, address, startlocation, endlocation, nummovers, email, numboxes;

    public void alreadyAvail(String servicename){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("Services").child(servicename);
        name = findViewById(R.id.serviceNameField);
        rate = findViewById(R.id.hourlyRate);
        first = findViewById(R.id.checkBox12);
        last = findViewById(R.id.checkBox13);
        dob =  findViewById(R.id.checkBox14);
        address =  findViewById(R.id.checkBox15);
        email = findViewById(R.id.checkBox16);
        startlocation = findViewById(R.id.checkBox17);
        endlocation = findViewById(R.id.checkBox18);
        nummovers = findViewById(R.id.checkBox19);
        numboxes = findViewById(R.id.checkBox20);
        addService = findViewById(R.id.addButton);
        addService.setText("Edit");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue().toString());
                rate.setText(snapshot.child("rate").getValue().toString());
                if(snapshot.child("hide first").getValue().toString().equals("true")){
                    first.setChecked(true);
                }

                if(snapshot.child("hide last").getValue().toString().equals("true")){
                    last.setChecked(true);
                }
                if(snapshot.child("hide dob").getValue().toString().equals("true")){
                    dob.setChecked(true);
                }
                if(snapshot.child("hide address").getValue().toString().equals("true")){
                    address.setChecked(true);
                }
                if(snapshot.child("hide email").getValue().toString().equals("true")){
                    email.setChecked(true);
                }
                if(snapshot.child("hide startlocation").getValue().toString().equals("true")){
                    startlocation.setChecked(true);
                }
                if(snapshot.child("hide endlocation").getValue().toString().equals("true")){
                    endlocation.setChecked(true);
                }
                if(snapshot.child("hide nummovers").getValue().toString().equals("true")){
                    nummovers.setChecked(true);
                }
                if(snapshot.child("hide numboxes").getValue().toString().equals("true")){
                    numboxes.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_assistance);
        Bundle extras = getIntent().getExtras();
        String value = null;
        if(extras!=null){
            value = extras.getString("username");
        }
        if(value!=null){
            alreadyAvail(value);
        }
        addService = findViewById(R.id.addButton);
        name = findViewById(R.id.serviceNameField);
        rate = findViewById(R.id.hourlyRate);
        first = findViewById(R.id.checkBox12);
        last = findViewById(R.id.checkBox13);
        dob =  findViewById(R.id.checkBox14);
        address =  findViewById(R.id.checkBox15);
        email = findViewById(R.id.checkBox16);
        startlocation = findViewById(R.id.checkBox17);
        endlocation = findViewById(R.id.checkBox18);
        nummovers = findViewById(R.id.checkBox19);
        numboxes = findViewById(R.id.checkBox20);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aName = name.getText().toString().trim();
                String aRate = rate.getText().toString().trim();
                if(TextUtils.isEmpty(aName)){
                    name.setError("Please provide service name.");
                    return;
                }
                if(TextUtils.isEmpty(aRate)) {
                    rate.setError("Please provide rate.");
                    return;
                }
                if(!(TextUtils.isDigitsOnly(aRate))){
                    rate.setError("Must be a number.");
                    return;
                }
                mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference info = mDatabase.getReference("Services");
                info.child(aName).child("rate").setValue(aRate);
                info.child(aName).child("name").setValue(aName);
                if (first.isChecked()){
                    info.child(aName).child("hide first").setValue("true");
                } else{
                    info.child(aName).child("hide first").setValue("false");
                }
                if (last.isChecked()){
                    info.child(aName).child("hide last").setValue("true");
                } else{
                    info.child(aName).child("hide last").setValue("false");
                }
                if (dob.isChecked()){
                    info.child(aName).child("hide dob").setValue("true");
                } else{
                    info.child(aName).child("hide dob").setValue("false");
                }
                if (address.isChecked()){
                    info.child(aName).child("hide address").setValue("true");
                } else{
                    info.child(aName).child("hide address").setValue("false");
                }
                if (email.isChecked()){
                    info.child(aName).child("hide email").setValue("true");
                } else{
                    info.child(aName).child("hide email").setValue("false");
                }
                if (startlocation.isChecked()){
                    info.child(aName).child("hide startlocation").setValue("true");
                } else{
                    info.child(aName).child("hide startlocation").setValue("false");
                }
                if (endlocation.isChecked()){
                    info.child(aName).child("hide endlocation").setValue("true");
                } else{
                    info.child(aName).child("hide endlocation").setValue("false");
                }
                if (nummovers.isChecked()){
                    info.child(aName).child("hide nummovers").setValue("true");
                } else{
                    info.child(aName).child("hide nummovers").setValue("false");
                }
                if (numboxes.isChecked()){
                    info.child(aName).child("hide numboxes").setValue("true");
                } else{
                    info.child(aName).child("hide numboxes").setValue("false");
                }
                info.child(aName).child("type").setValue("Moving Assistance");
                startActivity(new Intent(getApplicationContext(), ServiceManager.class));
            }
        });
    }
    public void back(View view){
        startActivity(new Intent(getApplicationContext(), ServiceManager.class));
    }
}