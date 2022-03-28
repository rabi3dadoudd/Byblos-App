package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AccountManager extends AppCompatActivity {
    ListView listOfAccounts;
    DatabaseReference databaseAccounts;
    List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        databaseAccounts = FirebaseDatabase.getInstance().getReference("Users");
        listOfAccounts = (ListView) findViewById(R.id.listOfAccounts);
        accounts = new ArrayList<>();
        listOfAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Account account = accounts.get(i);
                showDeleteDialog(account.getUsername());
                return true;
            }
        });
    }
    protected void onStart() {
        super.onStart();
        databaseAccounts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Account account = postSnapshot.getValue(Account.class);
                    if(!(account.getUsername().equals("admin"))&&!(account.getUsername().equals("employee"))&&!(account.getUsername().equals("customer"))){
                        accounts.add(account);
                    }
                }
                AccountList accountAdapter = new AccountList(AccountManager.this, accounts);
                listOfAccounts.setAdapter(accountAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showDeleteDialog(final String username){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_deleteaccount, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteAccount);
        dialogBuilder.setTitle(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(username);
                b.dismiss();
            }
        });
    }
    private void deleteAccount(String username){
        String email = username.replace(".","+");
        email = email+"@hotmailcom";
        DatabaseReference aB = FirebaseDatabase.getInstance().getReference("Users").child(email);
        aB.removeValue();
        Toast.makeText(getApplicationContext(),"Account Deleted.", Toast.LENGTH_LONG);
    }
    public void back(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}