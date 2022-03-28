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

public class ServiceChooser extends AppCompatActivity {
    ListView listOfServices;
    DatabaseReference databaseServices;
    List<Service> services;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_chooser);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        databaseServices = FirebaseDatabase.getInstance().getReference("Services");
        listOfServices = (ListView) findViewById(R.id.listOfServices2);
        services = new ArrayList<>();
        listOfServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service2 = services.get(i);
                showAddDialog(service2.getName());
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
                ServiceList serviceAdapter = new ServiceList(ServiceChooser.this, services);
                listOfServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showAddDialog(final String name1){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_edit_or_delete_service, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button3);
        final Button buttonEdit = (Button) dialogView.findViewById(R.id.button4);
        buttonDelete.setText("Add");
        buttonEdit.setVisibility(View.GONE);
        dialogBuilder.setTitle(name1);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService(name1);
                b.dismiss();
            }
        });
    }
    private void addService(String name1){
        DatabaseReference aB = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Services").child(name1);
        aB.child("name").setValue(name1);
        Toast.makeText(getApplicationContext(),"Service Added.", Toast.LENGTH_LONG);
    }
    public void backToMain(View view) {
        String value = username;
        Intent i = new Intent(ServiceChooser.this, EmployeeServiceManager.class);
        i.putExtra("username",value);
        startActivity(i);
    }
}