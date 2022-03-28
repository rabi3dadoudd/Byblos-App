package com.example.byblos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class FilterHour extends AppCompatActivity {
    Button submit;
    static EditText day;
    static EditText hour1,hour2;
    String customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_hour);
        Bundle extras = getIntent().getExtras();
        customer = extras.getString("username");
        submit = findViewById(R.id.button13);
        day = findViewById(R.id.editTextTextPersonName);
        hour1 = findViewById(R.id.editTextTextPersonName3);
        hour2 = findViewById(R.id.editTextTextPersonName4);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkday(day.getText().toString().toLowerCase(Locale.ROOT).trim())&&checkhour(hour1.getText().toString().trim())&&checkhour(hour2.getText().toString().trim())){
                    if(Integer.parseInt(hour1.getText().toString())>Integer.parseInt(hour2.getText().toString())){
                        hour1.setError("Closing time must be after opening time.");
                    }else{
                        String value = "hours";
                        Intent i = new Intent(FilterHour.this, FindBranch.class);
                        i.putExtra("type",value);
                        i.putExtra("username",customer);
                        i.putExtra("opening",hour1.getText().toString().trim());
                        i.putExtra("closing",hour2.getText().toString().trim());
                        i.putExtra("day",day.getText().toString().trim().toLowerCase(Locale.ROOT));
                        startActivity(i);
                    }
                }
            }
        });
    }

    public static boolean checkday(String number) {
        if(number.equals("mo")||number.equals("tu")||number.equals("we")||number.equals("th")||number.equals("fr")) {
            return true;
        }else{
            day.setError("Input must be 2 letters. Ex: Mo");
            return false;
        }
    }
    public static boolean checkhour(String number){
        if(number.equals("1")||number.equals("2")||number.equals("3")||number.equals("4")||number.equals("5")||number.equals("6")||number.equals("7")||number.equals("8")||number.equals("9")||number.equals("10")||number.equals("11")||number.equals("12")||number.equals("13")||number.equals("14")||number.equals("15")||number.equals("16")||number.equals("17")||number.equals("18")||number.equals("19")||number.equals("20")||number.equals("21")||number.equals("22")||number.equals("23")) {
            return true;
        }else{
            hour1.setError("Input must be 1-23");
            hour2.setError("Input must be 1-23");
            return false;
        }
    }

}