package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FillOutServiceForm extends AppCompatActivity {

    DatabaseReference ref;
    String branch;
    String customer;
    String serviceName;
    String formType;
    TextView l1,l2,l3,l4, l5, l6, l7, l8, l9, l10;
    EditText f1, f2, f3, f4, f5, f6, f7, f8, f9, f10;
    String sf1, sf2, sf3, sf4, sf5, sf6, sf7, sf8, sf9, sf10;
    String rate;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_out_service_form);
        Bundle extras = getIntent().getExtras();
        branch = extras.getString("branch");
        customer = extras.getString("customer");
        serviceName = extras.getString("serviceName");

        TextView titleOfActivity = findViewById(R.id.titleFillOutForm);



        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);
        l6 = findViewById(R.id.l6);
        l7 = findViewById(R.id.l7);
        l8 = findViewById(R.id.l8);
        l9 = findViewById(R.id.l9);
        l10 = findViewById(R.id.l10);

        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        f4 = findViewById(R.id.f4);
        f5 = findViewById(R.id.f5);
        f6 = findViewById(R.id.f6);
        f7 = findViewById(R.id.f7);
        f8 = findViewById(R.id.f8);
        f9 = findViewById(R.id.f9);
        f10 = findViewById(R.id.f10);

        submitButton = findViewById(R.id.submitBtnFillOutServiceForms);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services").child(serviceName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("hide first").getValue().toString().equals("false")){
                    l1.setText("First Name");
                    f1.setVisibility(View.VISIBLE);
                }
                if (snapshot.child("hide last").getValue().toString().equals("false")){
                    l2.setText("Last Name");
                    f2.setVisibility(View.VISIBLE);
                }
                if (snapshot.child("hide dob").getValue().toString().equals("false")){
                    l3.setText("Birthday");
                    f3.setVisibility(View.VISIBLE);
                }
                if (snapshot.child("hide address").getValue().toString().equals("false")){
                    l4.setText("Address");
                    f4.setVisibility(View.VISIBLE);
                }
                if (snapshot.child("hide email").getValue().toString().equals("false")){
                    l5.setText("Email");
                    f5.setVisibility(View.VISIBLE);
                }
                rate = snapshot.child("rate").getValue().toString();
                titleOfActivity.setText(serviceName+" $"+rate+"/hr");
                formType =snapshot.child("type").getValue().toString();
                if(snapshot.child("type").getValue().toString().equals("Truck Rental") || snapshot.child("type").getValue().toString().equals("Car Rental") ){

                    if (snapshot.child("hide license").getValue().toString().equals("false")){
                        l6.setText("License Type (G, G1, G2)");
                        f6.setVisibility(View.VISIBLE);
                        f6.setHint("G, G1, or G2");
                    }
                    if (snapshot.child("hide pickup").getValue().toString().equals("false")){
                        l7.setText("Pickup Date and Time");
                        f7.setVisibility(View.VISIBLE);
                        f7.setHint("dd-mm-yyyy hh:mm");
                    }
                    if (snapshot.child("hide returntime").getValue().toString().equals("false")){
                        l8.setText("Return Date and Time");
                        f8.setVisibility(View.VISIBLE);
                        f8.setHint("dd-mm-yyyy hh:mm");
                    }

                    /*
                    l6.setText("License Type (G, G1, G2)");
                    l7.setText("Pickup Date and Time");
                    l8.setText("Return Date and Time");

                     */


                }
                if(snapshot.child("type").getValue().toString().equals("Car Rental")){
                    if (snapshot.child("hide car").getValue().toString().equals("false")){
                        l9.setText("Preferred Car Type (compact, intermediate, or SUV)");
                        f9.setVisibility(View.VISIBLE);
                        f9.setHint("compact, intermediate, or SUV");

                    }

                   // l9.setText("Preferred Car Type (compact, intermediate, or SUV)");
                    l10.setVisibility(View.INVISIBLE);

                } if(snapshot.child("type").getValue().toString().equals("Truck Rental")){

                    if (snapshot.child("hide kmdriven").getValue().toString().equals("false")){
                        l9.setText("Max Number of Km to be driven");
                        f9.setVisibility(View.VISIBLE);
                    }
                    if (snapshot.child("hide areaused").getValue().toString().equals("false")){
                        l10.setText("Area Used");
                        f10.setVisibility(View.VISIBLE);
                    }


                    /*
                    l9.setText("Max Number of Km to be driven");
                    l10.setText("Area Used");

                     */


                } if(snapshot.child("type").getValue().toString().equals("Moving Assistance")){
                    if (snapshot.child("hide startlocation").getValue().toString().equals("false")){
                        l6.setText("Moving Start Location");
                        f6.setVisibility(View.VISIBLE);
                    }
                    if (snapshot.child("hide endlocation").getValue().toString().equals("false")){
                        l7.setText("Moving End Location");
                        f7.setVisibility(View.VISIBLE);
                    }
                    if (snapshot.child("hide nummovers").getValue().toString().equals("false")){
                        l8.setText("Number of Movers Needed");
                        f8.setVisibility(View.VISIBLE);
                    }
                    if (snapshot.child("hide numboxes").getValue().toString().equals("false")){
                        l9.setText("Number of Boxes Needed");
                        f9.setVisibility(View.VISIBLE);
                    }
                    /*
                    l6.setText("Moving Start Location");
                    l7.setText("Moving End Location");
                    l8.setText("Number of Movers Needed");
                    l9.setText("Number of Boxes Needed");
                    l10.setVisibility(View.INVISIBLE);

                     */
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });//ref.addValueEventListener


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sf1 = f1.getText().toString().trim();
                sf2 = f2.getText().toString().trim();
                sf3 = f3.getText().toString().trim();
                sf4 = f4.getText().toString().trim();
                sf5 = f5.getText().toString().trim();
                sf6 = f6.getText().toString().trim();
                sf7 = f7.getText().toString().trim();
                sf8 = f8.getText().toString().trim();
                sf9 = f9.getText().toString().trim();
                sf10 = f10.getText().toString().trim();

                EditText [] inputFieldArray = new EditText[] { f1, f2, f3, f4, f5, f6, f7, f8, f9, f10 };
                String[] inputArray = new String[] { sf1, sf2, sf3, sf4, sf5, sf6, sf7, sf8, sf9, sf10 };

                //check if any visible fields are empty
                /*
                for (int i = 0; i < inputFieldArray.length; i++) {
                    if (inputFieldArray[i].getVisibility() == View.VISIBLE){
                        if (TextUtils.isEmpty(inputArray[i])) {
                            inputFieldArray[i].setError("You can't leave this field empty.");
                            return;
                        }
                    }
                }

                 */

                if (!isAllLetters(sf1)&&f1.getVisibility()==View.VISIBLE){
                    f1.setError("First name can only contain letters");
                    return;
                }
                if (!isAllLetters(sf2)&&f2.getVisibility()==View.VISIBLE){
                    f2.setError("Last name can only contain letters");
                    return;
                }

                if ( !isValidDate(sf3)&&f3.getVisibility()==View.VISIBLE){
                    f3.setError("Please enter a valid date. Please use dd-mm-yyyy");
                    return;
                }


                if (TextUtils.isEmpty(sf4) && (f4.getVisibility() == View.VISIBLE)){
                    f4.setError("Please provide your address.");
                    return;
                }
                if( !isValidEmail(sf5)&&f5.getVisibility()==View.VISIBLE){
                    f5.setError("Please enter a valid email.");
                    return;
                }

                if ( formType.equals("Truck Rental")  || formType.equals("Car Rental")){
                    if( TextUtils.isEmpty(sf6)  && (f6.getVisibility() == View.VISIBLE)){
                        f6.setError("Please enter you licence. Either G1, G2, or G.");
                        return;
                    }
                    sf6=sf6.trim();
                    if ( !isValidLiscence(sf6)&&f6.getVisibility()==View.VISIBLE){
                        f6.setError("Please enter you licence. Enter 'G1', 'G2', or 'G'.");
                        return;
                    }

                    //pik up date and time
                    if ( !isValidDateTime(sf7)&&f7.getVisibility()==View.VISIBLE){
                        f7.setError("Please enter a valid date and time. Please use dd-mm-yyyy hh:mm format.");
                        return;
                    }
                    if ( !isValidDateTime(sf8)&&f8.getVisibility()==View.VISIBLE){
                        f8.setError("Please enter a valid date and time. Please use dd-mm-yyyy hh:mm format.");
                        return;
                    }

                }
                if ( formType.equals("Truck Rental")){
                   if (! isAllNumbers(sf9)&&f9.getVisibility()==View.VISIBLE){
                        f9.setError("Please enter a number to indicate the number of km that will be driven.");
                       return;
                   }
                    if( TextUtils.isEmpty(sf10)  && (f10.getVisibility() == View.VISIBLE)){
                        f10.setError("Please enter the area in which you will be using the truck.");
                        return;
                    }
                }
                if ( formType.equals("Car Rental")){
                    sf9=sf9.trim();
                    if ( !isValidCarType(sf9)&&f9.getVisibility()==View.VISIBLE){
                        f9.setError("Please enter you preference. Enter 'compact', 'intermediate', or 'SUV'.");
                        return;

                    }
                }
                if ( formType.equals("Moving Assistance")){
                    if( TextUtils.isEmpty(sf6)  && (f6.getVisibility() == View.VISIBLE)){
                        f6.setError("Please enter your Start Location");
                        return;
                    }
                    if( TextUtils.isEmpty(sf7)  && (f7.getVisibility() == View.VISIBLE)){
                        f7.setError("Please enter your End Location");
                        return;
                    }
                    if (! isAllNumbers(sf8)&&f8.getVisibility()==View.VISIBLE){
                        f8.setError("Please enter a number to indicate the number of movers needed.");
                        return;
                    }
                    if (! isAllNumbers(sf9)&&f9.getVisibility()==View.VISIBLE){
                        f9.setError("Please enter a number to indicate the number of boxes.");
                        return;
                    }


                    /*
                    String nameOfRequest = "Request from + " +sf1;
                    ref2.child( nameOfRequest);
                    ref2.child(nameOfRequest).child("first").setValue(sf1) ;

                     */
                    //ref2.child("Branch Profile").child("phoneNumber").setValue(phoneNumberString);

                }
                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users").child(branch).child("Service Requests");
                if(f1.getVisibility()==View.VISIBLE){
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    ref2 = ref2.child("Request from " + sf1 +" on "+timeStamp);
                    ref2.child("name").setValue("Request from " + sf1 +" on "+timeStamp);
                    ref2.child("type2").setValue(serviceName);
                    ref2.child("type").setValue(formType);
                }else{
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    ref2 = ref2.child("Request for "+serviceName + " on "+timeStamp);
                    ref2.child("name").setValue("Request for "+serviceName + " on "+timeStamp);
                    ref2.child("type2").setValue(serviceName);
                    ref2.child("type").setValue(formType);
                }
                if(f1.getVisibility()==View.VISIBLE){
                    ref2.child("first").setValue(sf1);
                }
                if(f2.getVisibility()==View.VISIBLE){
                    ref2.child("last").setValue(sf2);
                }
                if(f3.getVisibility()==View.VISIBLE){
                    ref2.child("dob").setValue(sf3);
                }
                if(f4.getVisibility()==View.VISIBLE){
                    ref2.child("address").setValue(sf4);
                }
                if(f5.getVisibility()==View.VISIBLE){
                    ref2.child("email").setValue(sf5);
                }
                if(f6.getVisibility()==View.VISIBLE){
                    if(l6.getText().toString().equals("License Type (G, G1, G2)")){
                        ref2.child("license").setValue(sf6);
                    }else{
                        ref2.child("startlocation").setValue(sf6);
                    }
                }
                if(f7.getVisibility()==View.VISIBLE){
                    if(l7.getText().toString().equals("Pickup Date and Time")){
                        ref2.child("pickup").setValue(sf7);
                    }else{
                        ref2.child("endlocation").setValue(sf7);
                    }
                }
                if(f8.getVisibility()==View.VISIBLE){
                    if(l8.getText().toString().equals("Return Date and Time")){
                        ref2.child("returntime").setValue(sf8);
                    }else{
                        ref2.child("nummovers").setValue(sf8);
                    }
                }
                if(f9.getVisibility()==View.VISIBLE){
                    if(l9.getText().toString().equals("Preferred Car Type (compact, intermediate, or SUV)")){
                        ref2.child("car").setValue(sf9);
                    }
                    if(l9.getText().toString().equals("Max Number of Km to be driven")){
                        ref2.child("kmdriven").setValue(sf9);
                    }
                    if(l9.getText().toString().equals("Number of Boxes Needed")){
                        ref2.child("numboxes").setValue(sf9);
                    }
                }
                if(f10.getVisibility()==View.VISIBLE){
                    ref2.child("areaused").setValue(sf10);
                }
                Intent intent = new Intent(FillOutServiceForm.this, FindBranch.class);
                intent.putExtra("username",customer);
                startActivity(intent);
            }
        });



        /*
        String type = ref.child("type").toString();
        l4.setText(type);
        if ( type.equals("Truck Rental")){
            l1.setText("Address");
            l2.setText("Area Used");
            l3.setText("DOB");
            //l4.setText("Email");

        }

         */


    }

    public boolean isAllLetters(String str) {
        return str.matches("[a-zA-Z]+");
    }
    //regex from: https://stackoverflow.com/questions/15491894/regex-to-validate-date-formats-dd-mm-yyyy-dd-mm-yyyy-dd-mm-yyyy-dd-mmm-yyyy
    public boolean isValidDate(String date){
        return date.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$" );
    }
    //regex from https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
    public static boolean isValidEmail(String str) {
        return str.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$");
    }

    public boolean isValidLiscence(String str) {
        return ( str.equals("G1") || str.equals("G2") || str.equals("G") );
    }

    public boolean isAllNumbers(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isValidCarType(String str) {
        return ( str.equals("compact") || str.equals("intermediate") || str.equals("SUV")  );
    }

    //regex from: https://stackoverflow.com/questions/23360599/regular-expression-for-dd-mm-yyyy-hhmm
    public boolean isValidDateTime(String str) {
        return str.matches("^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$");
    }



}