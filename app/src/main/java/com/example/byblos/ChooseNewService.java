package com.example.byblos;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseNewService extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate( bundle );

        setContentView (R.layout.activity_choose_new_service );

        //get screen size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        getWindow().setLayout((int) (width*0.7) , (int) (height *0.5) );

    }
    public void carRental(View view){
        startActivity(new Intent(getApplicationContext(), CarRental.class));
    }
    public void truckRental(View view){
        startActivity(new Intent(getApplicationContext(), TruckRental.class));
    }
    public void movingAssistance(View view){
        startActivity(new Intent(getApplicationContext(), MovingAssistance.class));
    }


}
