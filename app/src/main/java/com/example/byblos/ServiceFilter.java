package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceFilter extends AppCompatActivity {
    ListView listOfServices;
    DatabaseReference databaseServices;
    List<Service> services;
    String customer;

    @Override
    protected void onCreate(Bundle bundle ) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_service_filter);
        Bundle extras = getIntent().getExtras();
        customer = extras.getString("username");
        databaseServices = FirebaseDatabase.getInstance().getReference("Services");
        listOfServices = (ListView) findViewById(R.id.listOfServices5);
        services = new ArrayList<>();
        listOfServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service2 = services.get(i);
                Intent in = new Intent(ServiceFilter.this, FindBranch.class);
                in.putExtra("username",customer);
                in.putExtra("type","services");
                in.putExtra("services",service2.getName());
                startActivity(in);
            }
        });
    }
    protected void onStart() {
        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Service service3 = postSnapshot.getValue(Service.class);
                    services.add(service3);
                }
                ServiceList serviceAdapter = new ServiceList(ServiceFilter.this, services);
                listOfServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}