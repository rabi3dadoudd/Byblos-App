package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    String name;
    String role;
    String username;
    String adminchecker;
    Button button2;
    Button button3;
    Button button4;
    Button button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = findViewById(R.id.manageAccounts);
        button3 = findViewById(R.id.manageServicesBtn);
        username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(username.equals("admin@admin.com")){
            username = "admin";
            adminchecker = "admin@admincom";
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
        } else{
            adminchecker = username.replace("@hotmail.com","@hotmailcom").replace(".","+");
        }

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(adminchecker);


        TextView logininfo = findViewById(R.id.logininfo);

        //if(email2.equals("admin@admincom")){
            //username = "admin";
       // }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("fullname").getValue().toString();
                role = snapshot.child("role").getValue().toString();
                logininfo.setText("Name: "+ name + "\n" + "Username: " + username.replace("@hotmail.com","") + "\n" +"Role: "+ role);

                if (role.equals("Employee")){
                    button2 = findViewById(R.id.employeeManageServiceBtn);
                    button3 = findViewById(R.id.employeeBranchProfileBtn);
                    button4 = findViewById(R.id.serviceRequestsScreen);
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    button4.setVisibility(View.VISIBLE);
                }
                if (role.equals("Customer")){
                    button5 = findViewById(R.id.submitForm);
                    button5.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
    public void manageAccountsScreen(View view){
        startActivity(new Intent(getApplicationContext(), AccountManager.class));
    }
    public void manageServicesScreen(View view){
        startActivity(new Intent(getApplicationContext(), ServiceManager.class));
    }

    public void branchProfileScreen(View view){
        String value = adminchecker;
        Intent i = new Intent(MainActivity.this, BranchProfile.class);
        i.putExtra("username",value);
        startActivity(i);
    }
    public void employeeManageServicesScreen(View view){
        String value = adminchecker;
        Intent i = new Intent(MainActivity.this, EmployeeServiceManager.class);
        i.putExtra("username",value);
        startActivity(i);
    }
    public void serviceRequestsScreen(View view){
        String value = adminchecker;
        Intent i = new Intent(MainActivity.this, ServiceRequests.class);
        i.putExtra("username",value);
        startActivity(i);
    }
    public void findBranchScreen(View view){
        String value = adminchecker;
        Intent i = new Intent(MainActivity.this, FindBranch.class);
        i.putExtra("username",value);
        startActivity(i);
    }
}