package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequests extends AppCompatActivity {
    ListView listOfServices;
    DatabaseReference databaseServices;
    List<Service> services;
    String branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requests);
        Bundle extras = getIntent().getExtras();
        branch = extras.getString("username");
        databaseServices = FirebaseDatabase.getInstance().getReference("Users").child(branch).child("Service Requests");
        listOfServices = (ListView) findViewById(R.id.serviceRequestList);
        services = new ArrayList<>();
        listOfServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service2 = services.get(i);
                String value = service2.getName();
                String value2 = branch;
                Intent in = new Intent(ServiceRequests.this, ServiceRequest.class);
                in.putExtra("username",value);
                in.putExtra("branch",branch);
                startActivity(in);
                return true;
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
                ServiceList serviceAdapter = new ServiceList(ServiceRequests.this, services);
                listOfServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void backToMain2(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}