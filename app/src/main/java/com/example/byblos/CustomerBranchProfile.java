package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerBranchProfile extends AppCompatActivity {
    String branch;
    String customer;
    FirebaseDatabase mDatabase;
    TextView phoneNumber, address;
    TextView mondayOHour,tuesdayOHour,wednesdayOHour,thursdayOHour,fridayOHour; //Opening hours
    TextView mondayCHour,tuesdayCHour,wednesdayCHour,thursdayCHour,fridayCHour; //Closing hours
    Button rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_branch_profile);
        Bundle extras = getIntent().getExtras();
        rate = findViewById(R.id.button10);
        customer = extras.getString("customer");
        branch = extras.getString("branch");
        phoneNumber = findViewById(R.id.textView5);
        address = findViewById(R.id.textView9);
        //Opening Hours
        mondayOHour = findViewById(R.id.textView11);
        tuesdayOHour = findViewById(R.id.textView52);
        wednesdayOHour = findViewById(R.id.textView55);
        thursdayOHour = findViewById(R.id.textView59);
        fridayOHour = findViewById(R.id.textView61);

        //Closing hours
        mondayCHour = findViewById(R.id.textView16);
        tuesdayCHour = findViewById(R.id.textView53);
        wednesdayCHour = findViewById(R.id.textView56);
        thursdayCHour = findViewById(R.id.textView58);
        fridayCHour = findViewById(R.id.textView62);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(branch);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Branch Profile")) {
                    phoneNumber.setText(snapshot.child("Branch Profile").child("phoneNumber").getValue().toString());
                    address.setText(snapshot.child("Branch Profile").child("address").getValue().toString());
                    mondayOHour.setText(snapshot.child("Branch Profile").child("mondayOHour").getValue().toString());
                    tuesdayOHour.setText(snapshot.child("Branch Profile").child("tuesdayOHour").getValue().toString());
                    wednesdayOHour.setText(snapshot.child("Branch Profile").child("wednesdayOHour").getValue().toString());
                    thursdayOHour.setText(snapshot.child("Branch Profile").child("thursdayOHour").getValue().toString());
                    fridayOHour.setText(snapshot.child("Branch Profile").child("fridayOHour").getValue().toString());
                    mondayCHour.setText(snapshot.child("Branch Profile").child("mondayCHour").getValue().toString());
                    tuesdayCHour.setText(snapshot.child("Branch Profile").child("tuesdayCHour").getValue().toString());
                    wednesdayCHour.setText(snapshot.child("Branch Profile").child("wednesdayCHour").getValue().toString());
                    thursdayCHour.setText(snapshot.child("Branch Profile").child("thursdayCHour").getValue().toString());
                    fridayCHour.setText(snapshot.child("Branch Profile").child("fridayCHour").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(branch, customer);
            }
        });
    }
    public void backToMain(View view){
        String value = customer;
        Intent i = new Intent(CustomerBranchProfile.this, FindBranch.class);
        i.putExtra("username",value);
        startActivity(i);
    }
    private void dialog(final String name1, String name2){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_rating, null);
        dialogBuilder.setView(dialogView);
        final EditText buttonDelete = dialogView.findViewById(R.id.editTextTextPersonName2);
        final EditText buttonthis = dialogView.findViewById(R.id.editTextTextPersonName5);
        final Button buttonEdit = dialogView.findViewById(R.id.button12);
        dialogBuilder.setTitle("Rate Branch: " + name1.replace("@hotmailcom",""));
        final AlertDialog b = dialogBuilder.create();
        b.show();
        DatabaseReference ref2 = mDatabase.getInstance().getReference().child("Users").child(name1);
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Ratings")) {
                    if(snapshot.child("Ratings").hasChild(name2)){
                        buttonDelete.setText(snapshot.child("Ratings").child(name2).child("number").getValue().toString());
                        buttonthis.setText(snapshot.child("Ratings").child(name2).child("comment").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonDelete.getText().toString().trim().equals("1")||buttonDelete.getText().toString().trim().equals("2")||buttonDelete.getText().toString().trim().equals("3")||buttonDelete.getText().toString().trim().equals("4")||buttonDelete.getText().toString().trim().equals("5")){
                    ref2.child("Ratings").child(name2).child("number").setValue(buttonDelete.getText().toString().trim());
                } else{
                    buttonDelete.setError("Rate must be 1-5");
                }
                if(buttonthis.getText().toString().isEmpty()){
                    buttonthis.setError("Please write a comment");
                }else{
                    ref2.child("Ratings").child(name2).child("comment").setValue(buttonthis.getText().toString().trim());
                    b.dismiss();
                }

            }
        });
    }

}