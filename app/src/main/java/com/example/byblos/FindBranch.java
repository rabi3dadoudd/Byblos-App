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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindBranch extends AppCompatActivity {
    ListView listOfBranches;
    List<Address> addresses;
    DatabaseReference databaseAddresses;
    String type;
    String customer;
    List<Account> users;
    DatabaseReference database;
    String day;
    String opening;
    String closing;
    String servicename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_branch);
        type = null;
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            type = extras.getString("type");
            customer = extras.getString("username");
            day = extras.getString("day");
            opening = extras.getString("opening");
            closing = extras.getString("closing");
            servicename = extras.getString("services");
        }
        databaseAddresses = FirebaseDatabase.getInstance().getReference("Users");
        database = FirebaseDatabase.getInstance().getReference("Users");
        listOfBranches = findViewById(R.id.listOfBranches);
        addresses = new ArrayList<>();
        users = new ArrayList<>();
        listOfBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Account address2 = users.get(i);
                dialog(address2.getUsername());
                return true;
            }
        });
    }
    protected void onStart() {
        super.onStart();
        if(type!=null) {
            if(type.equals("address")) {
                databaseAddresses.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        addresses.clear();
                        for(DataSnapshot postSnapshot: snapshot.getChildren()){
                            if(postSnapshot.hasChild("Branch Profile")) {
                                Address service3 = postSnapshot.child("Branch Profile").getValue(Address.class);
                                addresses.add(service3);
                                users.add(postSnapshot.getValue(Account.class));
                            }
                        }
                        AddressList serviceAdapter = new AddressList(FindBranch.this, addresses);
                        listOfBranches.setAdapter(serviceAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            if(type.equals("hours")){
                    databaseAddresses.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            users.clear();
                            for(DataSnapshot postSnapshot: snapshot.getChildren()){
                                if(postSnapshot.hasChild("Branch Profile")) {
                                    if(day.equals("mo")){
                                        String mondayOHour = postSnapshot.child("Branch Profile").child("mondayOHour").getValue().toString();
                                        String mondayCHour = postSnapshot.child("Branch Profile").child("mondayCHour").getValue().toString();
                                        if(!(mondayOHour.equals("00:00")&&mondayCHour.equals("00:00"))){
                                            if(Integer.parseInt(mondayOHour.substring(0,mondayOHour.lastIndexOf(":")))<=Integer.parseInt(opening)&&Integer.parseInt(mondayCHour.substring(0,mondayOHour.lastIndexOf(":")))>=Integer.parseInt(closing)){
                                                Account service3 = postSnapshot.getValue(Account.class);
                                                users.add(service3);
                                            }
                                        }
                                    }
                                    if(day.equals("tu")){
                                        String tuesdayOHour = postSnapshot.child("Branch Profile").child("tuesdayOHour").getValue().toString();
                                        String tuesdayCHour = postSnapshot.child("Branch Profile").child("tuesdayCHour").getValue().toString();
                                        if(!(tuesdayOHour.equals("00:00")&&tuesdayCHour.equals("00:00"))){
                                            if(Integer.parseInt(tuesdayOHour.substring(0,tuesdayOHour.lastIndexOf(":")))<=Integer.parseInt(opening)&&Integer.parseInt(tuesdayCHour.substring(0,tuesdayOHour.lastIndexOf(":")))>=Integer.parseInt(closing)){
                                                Account service3 = postSnapshot.getValue(Account.class);
                                                users.add(service3);
                                            }
                                        }
                                    }
                                    if(day.equals("we")){
                                        String wednesdayOHour = postSnapshot.child("Branch Profile").child("wednesdayOHour").getValue().toString();
                                        String wednesdayCHour = postSnapshot.child("Branch Profile").child("wednesdayCHour").getValue().toString();
                                        if(!(wednesdayOHour.equals("00:00")&&wednesdayCHour.equals("00:00"))){
                                            if(Integer.parseInt(wednesdayOHour.substring(0,wednesdayOHour.lastIndexOf(":")))<=Integer.parseInt(opening)&&Integer.parseInt(wednesdayCHour.substring(0,wednesdayOHour.lastIndexOf(":")))>=Integer.parseInt(closing)){
                                                Account service3 = postSnapshot.getValue(Account.class);
                                                users.add(service3);
                                            }
                                        }

                                    }
                                    if(day.equals("th")){
                                        String thursdayOHour = postSnapshot.child("Branch Profile").child("thursdayOHour").getValue().toString();
                                        String thursdayCHour = postSnapshot.child("Branch Profile").child("thursdayCHour").getValue().toString();
                                        if(!(thursdayOHour.equals("00:00")&&thursdayCHour.equals("00:00"))){
                                            if(Integer.parseInt(thursdayOHour.substring(0,thursdayOHour.lastIndexOf(":")))<=Integer.parseInt(opening)&&Integer.parseInt(thursdayCHour.substring(0,thursdayOHour.lastIndexOf(":")))>=Integer.parseInt(closing)){
                                                Account service3 = postSnapshot.getValue(Account.class);
                                                users.add(service3);
                                            }
                                        }

                                    }
                                    if(day.equals("fr")){
                                        String fridayOHour = postSnapshot.child("Branch Profile").child("fridayOHour").getValue().toString();
                                        String fridayCHour = postSnapshot.child("Branch Profile").child("fridayCHour").getValue().toString();
                                        if(!(fridayOHour.equals("00:00")&&fridayCHour.equals("00:00"))){
                                            if(Integer.parseInt(fridayOHour.substring(0,fridayOHour.lastIndexOf(":")))<=Integer.parseInt(opening)&&Integer.parseInt(fridayCHour.substring(0,fridayOHour.lastIndexOf(":")))>=Integer.parseInt(closing)){
                                                Account service3 = postSnapshot.getValue(Account.class);
                                                users.add(service3);
                                            }
                                        }

                                    }
                                }
                            }
                            AccountList serviceAdapter = new AccountList(FindBranch.this, users);
                            listOfBranches.setAdapter(serviceAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            if(type.equals("services")){
                databaseAddresses.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for(DataSnapshot postSnapshot: snapshot.getChildren()){
                            if(postSnapshot.hasChild("Branch Profile")) {
                                if(postSnapshot.child("Services").hasChild(servicename)){
                                    users.add(postSnapshot.getValue(Account.class));
                                }
                            }
                        }
                        AccountList serviceAdapter = new AccountList(FindBranch.this, users);
                        listOfBranches.setAdapter(serviceAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        } else{
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    users.clear();
                    for(DataSnapshot postSnapshot: snapshot.getChildren()){
                        if(postSnapshot.child("role").getValue().toString().equals("Employee")){
                            Account service3 = postSnapshot.getValue(Account.class);
                            users.add(service3);
                        }
                    }
                    AccountList serviceAdapter = new AccountList(FindBranch.this, users);
                    listOfBranches.setAdapter(serviceAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void dialog(final String name1){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_edit_or_delete_service, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button3);
        final Button buttonEdit = (Button) dialogView.findViewById(R.id.button4);
        buttonDelete.setText("Branch Profile");
        buttonEdit.setText("Submit form");
        dialogBuilder.setTitle(name1);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = name1+"@hotmailcom";
                Intent i = new Intent(FindBranch.this, CustomerBranchProfile.class);
                i.putExtra("branch",value);
                i.putExtra("customer",customer);
                startActivity(i);
                b.dismiss();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = name1+"@hotmailcom";
                Intent i = new Intent(FindBranch.this, PickServiceForm.class);

                i.putExtra("branch",value);
                i.putExtra("customer",customer);
                startActivity(i);


                b.dismiss();
            }
        });
    }
    public void backToMain(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void filter(View view){
        String value = customer;
        Intent i = new Intent(FindBranch.this, FilterBasedOn2.class);
        i.putExtra("username",value);
        startActivity(i);
    }
}