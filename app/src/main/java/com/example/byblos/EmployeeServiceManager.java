package com.example.byblos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceManager extends AppCompatActivity {
    ListView listOfServices;
    DatabaseReference databaseServices;
    List<Service> services;
    String username;

    @Override
    protected void onCreate(Bundle bundle ) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_employee_service_manager);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        databaseServices = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Services");
        listOfServices = (ListView) findViewById(R.id.manageListofServices);
        services = new ArrayList<>();
        listOfServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service2 = services.get(i);
                showDeletingDialog(service2.getName());
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
                ServiceList serviceAdapter = new ServiceList(EmployeeServiceManager.this, services);
                listOfServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDeletingDialog(final String name1){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_edit_or_delete_service, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button3);
        final Button buttonEdit = (Button) dialogView.findViewById(R.id.button4);
        buttonEdit.setVisibility(View.GONE);
        dialogBuilder.setTitle(name1);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(name1);
                b.dismiss();
            }
        });
    }

    private void deleteService(String name1){
        DatabaseReference aB = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Services").child(name1);
        aB.removeValue();
        Toast.makeText(getApplicationContext(),"Service Deleted.", Toast.LENGTH_LONG);
    }

    public void backToMain(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void addServiceBttn(View view){
        String value = username;
        Intent i = new Intent(EmployeeServiceManager.this, ServiceChooser.class);
        i.putExtra("username",value);
        startActivity(i);
    }


}
