package com.example.byblos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FilterBasedOn2 extends AppCompatActivity {
    String customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_based_on2);
        Bundle extras = getIntent().getExtras();
        customer = extras.getString("username");
    }
    public void address(View view){
        String value = "address";
        Intent i = new Intent(FilterBasedOn2.this, FindBranch.class);
        i.putExtra("type",value);
        i.putExtra("username",customer);
        startActivity(i);
    }
    public void hours(View view){
        Intent i = new Intent(FilterBasedOn2.this, FilterHour.class);
        i.putExtra("username",customer);
        startActivity(i);
    }
    public void services(View view){
        Intent i = new Intent(FilterBasedOn2.this, ServiceFilter.class);
        i.putExtra("username",customer);
        startActivity(i);
    }
}