package com.example.byblos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.regex.*;

public class BranchProfile extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    EditText phoneNumber, address;
    EditText mondayOHour,tuesdayOHour,wednesdayOHour,thursdayOHour,fridayOHour; //Opening hours
    EditText mondayCHour,tuesdayCHour,wednesdayCHour,thursdayCHour,fridayCHour; //Closing hours
    Button submitButton;
    String phoneNumberString,addressString,mondayOHourString,tuesdayOHourString,wednesdayOHourString,thursdayOHourString,fridayOHourString,mondayCHourString,tuesdayCHourString,wednesdayCHourString,thursdayCHourString,fridayCHourString;



    @Override
    protected void onCreate(Bundle bundle ) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_branch_profile);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("username");

        phoneNumber = findViewById(R.id.branchPhoneNumber);
        address = findViewById(R.id.branchAddress);
        submitButton = findViewById(R.id.submitBtnBranchProfile);

        //Opening Hours
        mondayOHour = findViewById(R.id.moh);
        tuesdayOHour = findViewById(R.id.toh);
        wednesdayOHour = findViewById(R.id.woh);
        thursdayOHour = findViewById(R.id.thoh);
        fridayOHour = findViewById(R.id.foh);

        //Closing hours
        mondayCHour = findViewById(R.id.mch);
        tuesdayCHour = findViewById(R.id.tch);
        wednesdayCHour = findViewById(R.id.wch);
        thursdayCHour = findViewById(R.id.thch);
        fridayCHour = findViewById(R.id.fch);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(value);
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumberString = phoneNumber.getText().toString().trim();
                addressString = address.getText().toString().trim();
                mondayOHourString = mondayOHour.getText().toString().trim();
                tuesdayOHourString = tuesdayOHour.getText().toString().trim();
                wednesdayOHourString = wednesdayOHour.getText().toString().trim();
                thursdayOHourString = thursdayOHour.getText().toString().trim();
                fridayOHourString = fridayOHour.getText().toString().trim();
                mondayCHourString = mondayCHour.getText().toString().trim();
                tuesdayCHourString = tuesdayCHour.getText().toString().trim();
                wednesdayCHourString = wednesdayCHour.getText().toString().trim();
                thursdayCHourString = thursdayCHour.getText().toString().trim();
                fridayCHourString = fridayCHour.getText().toString().trim();

                //Arrays to store EditText fields and hours strings
                EditText[] hoursArray = new EditText[] {mondayOHour, mondayCHour, tuesdayOHour, tuesdayCHour, wednesdayOHour, wednesdayCHour, thursdayOHour, thursdayCHour, fridayOHour, fridayCHour};
                String[] hoursArrayStrings = new String[] {mondayOHourString, mondayCHourString, tuesdayOHourString, tuesdayCHourString, wednesdayOHourString, wednesdayCHourString, thursdayOHourString, thursdayCHourString, fridayOHourString, fridayCHourString};



                //Check empty email field
                if (TextUtils.isEmpty(addressString)){
                    address.setError("Please provide your branch's address.");
                    return;
                }

                //checks for empty phone number field
                if (TextUtils.isEmpty(phoneNumberString)){
                    phoneNumber.setError("Please provide phone number.");
                    return;
                }
                else if ( ! isValidPhoneNumber(phoneNumberString) ){
                    phoneNumber.setError("Please provide a valid phone number. Ex: 123-123-1234");
                    return;
                }

                //Check invalid hours fields
                for (int i = 0; i < hoursArray.length; i++) {

                    //Check for empty timeslots
                    if (TextUtils.isEmpty(hoursArrayStrings[i])) {
                        hoursArray[i].setError("You are missing a timeslot field. Please review your inputs.");
                        return;
                    }

                    else {
                        //Check for invalid 24-hour format
                        if (isValidTime(hoursArrayStrings[i]) != true) {
                            hoursArray[i].setError("Please ensure that all times are given in 24-hour time format. Ex: 00:00");
                            return;
                        }
                    }
                }
                if(Integer.parseInt(mondayOHourString.substring(0,mondayOHourString.lastIndexOf(":")))>Integer.parseInt(mondayCHourString.substring(0,mondayCHourString.lastIndexOf(":")))){
                    mondayCHour.setError("Closing time must be after opening time.");
                    return;
                }
                if(Integer.parseInt(tuesdayOHourString.substring(0,tuesdayOHourString.lastIndexOf(":")))>Integer.parseInt(tuesdayCHourString.substring(0,tuesdayCHourString.lastIndexOf(":")))){
                    tuesdayCHour.setError("Closing time must be after opening time.");
                    return;
                }
                if(Integer.parseInt(wednesdayOHourString.substring(0,wednesdayOHourString.lastIndexOf(":")))>Integer.parseInt(wednesdayCHourString.substring(0,wednesdayCHourString.lastIndexOf(":")))){
                    wednesdayCHour.setError("Closing time must be after opening time.");
                    return;
                }
                if(Integer.parseInt(thursdayOHourString.substring(0,thursdayOHourString.lastIndexOf(":")))>Integer.parseInt(thursdayCHourString.substring(0,thursdayCHourString.lastIndexOf(":")))){
                    thursdayCHour.setError("Closing time must be after opening time.");
                    return;
                }
                if(Integer.parseInt(fridayOHourString.substring(0,fridayOHourString.lastIndexOf(":")))>Integer.parseInt(fridayCHourString.substring(0,fridayCHourString.lastIndexOf(":")))){
                    fridayCHour.setError("Closing time must be after opening time.");
                    return;
                }

                DatabaseReference ref2 = mDatabase.getInstance().getReference().child("Users").child(value);
                ref2.child("Branch Profile").child("phoneNumber").setValue(phoneNumberString);
                ref2.child("Branch Profile").child("address").setValue(addressString);
                ref2.child("Branch Profile").child("mondayOHour").setValue(mondayOHourString);
                ref2.child("Branch Profile").child("tuesdayOHour").setValue(tuesdayOHourString);
                ref2.child("Branch Profile").child("wednesdayOHour").setValue(wednesdayOHourString);
                ref2.child("Branch Profile").child("thursdayOHour").setValue(thursdayOHourString);
                ref2.child("Branch Profile").child("fridayOHour").setValue(fridayOHourString);
                ref2.child("Branch Profile").child("mondayCHour").setValue(mondayCHourString);
                ref2.child("Branch Profile").child("tuesdayCHour").setValue(tuesdayCHourString);
                ref2.child("Branch Profile").child("wednesdayCHour").setValue(wednesdayCHourString);
                ref2.child("Branch Profile").child("thursdayCHour").setValue(thursdayCHourString);
                ref2.child("Branch Profile").child("fridayCHour").setValue(fridayCHourString);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    protected void onStart() {
        super.onStart();

    }

    //Regex taken from https://www.geeksforgeeks.org/how-to-validate-time-in-24-hour-format-using-regular-expression/
    public static boolean isValidTime(String time) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern p = Pattern.compile(regex);
        if (time == null) {
            return false;
        }
        Matcher m = p.matcher(time);
        return m.matches();
    }

    //Regex taken from https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    public static boolean isValidPhoneNumber(String number) {
        String regex = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
        Pattern p = Pattern.compile(regex);
        if (number == null) {
            return false;
        }

        Matcher matcher = p.matcher(number);
        return matcher.matches();
    }


}
