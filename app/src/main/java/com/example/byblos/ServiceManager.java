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

public class ServiceManager extends AppCompatActivity {
    ListView listOfServices;
    DatabaseReference databaseServices;
    List<Service> services;

    @Override
    protected void onCreate(Bundle bundle ) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_service_manager);

        Button addServiceBtn  = (Button) findViewById(R.id.addService2);
        addServiceBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick (View view){
                startActivity(new Intent(getApplicationContext(), ChooseNewService.class));
            }
        });
        databaseServices = FirebaseDatabase.getInstance().getReference("Services");
        listOfServices = (ListView) findViewById(R.id.listOfServices);
        services = new ArrayList<>();
        listOfServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service2 = services.get(i);
                showDeleteDialog(service2.getName());
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
                ServiceList serviceAdapter = new ServiceList(ServiceManager.this, services);
                listOfServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showDeleteDialog(final String username){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_edit_or_delete_service, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button3);
        final Button buttonEdit = (Button) dialogView.findViewById(R.id.button4);
        dialogBuilder.setTitle(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(username);
                b.dismiss();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseServices = FirebaseDatabase.getInstance().getReference("Services").child(username);
                databaseServices.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("type").getValue().toString().equals("Car Rental")){
                            String value = username;
                            Intent i = new Intent(ServiceManager.this,CarRental.class);
                            i.putExtra("username",value);
                            startActivity(i);
                        } if(snapshot.child("type").getValue().toString().equals("Truck Rental")){
                            String value = username;
                            Intent i = new Intent(ServiceManager.this, TruckRental.class);
                            i.putExtra("username",value);
                            startActivity(i);
                        } if(snapshot.child("type").getValue().toString().equals("Moving Assistance")){
                            String value = username;
                            Intent i = new Intent(ServiceManager.this,MovingAssistance.class);
                            i.putExtra("username",value);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    private void deleteService(String username){
        DatabaseReference aB = FirebaseDatabase.getInstance().getReference("Services").child(username);
        aB.removeValue();
        Toast.makeText(getApplicationContext(),"Service Deleted.", Toast.LENGTH_LONG);
    }
    public void backToMain(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}