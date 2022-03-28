package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PickServiceForm extends AppCompatActivity {
    ListView listOfServiceForms;
    DatabaseReference databaseServices;
    List<Service> services;
    String branch;
    String customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service_form);
        Bundle extras = getIntent().getExtras();
        branch = extras.getString("branch");
        customer = extras.getString("customer");


        databaseServices = FirebaseDatabase.getInstance().getReference("Users").child(branch).child("Services");
        listOfServiceForms = (ListView) findViewById(R.id.listOfForms);
        services = new ArrayList<>();
        listOfServiceForms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service2 = services.get(i);
               // showAddDialog(service2.getName());
                String serviceName= service2.getName();

                Intent intent = new Intent(PickServiceForm.this, FillOutServiceForm.class);

                intent.putExtra("branch",branch);
                intent.putExtra("customer",customer);
                intent.putExtra("serviceName",serviceName);
                startActivity(intent);
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
                ServiceList serviceAdapter = new ServiceList(PickServiceForm.this, services);
                listOfServiceForms.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
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

     */
    public void backToMain(View view) {
        String value = branch;
        Intent i = new Intent(PickServiceForm.this, FindBranch.class);
        i.putExtra("username",value);
        startActivity(i);
    }


}