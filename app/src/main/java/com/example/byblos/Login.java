package com.example.byblos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText aUsername,aPassword;
    Button aSignIn_button;
    TextView aCreateText;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aUsername = findViewById(R.id.LoginUsername);
        aPassword = findViewById(R.id.LoginPassword);
        aSignIn_button = findViewById(R.id.SignIn_button);
        aCreateText = findViewById(R.id.RegisterText);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        aSignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get email and password from fields
                String username = aUsername.getText().toString().trim();
                String password = aPassword.getText().toString().trim();

                //Check empty email field
                if (TextUtils.isEmpty(username)){
                    aUsername.setError("Please provide your username.");
                    return;
                }

                //Check empty password field
                if (TextUtils.isEmpty(password)){
                    aPassword.setError("Please provide a password.");
                    return;
                }

                //Check invalid password field
                if (password.length()<8){
                    if (password.equals("admin")&&username.equals("admin")){
                    } else{
                        aPassword.setError("Your password must be at least 8 characters.");
                        return;
                    }
                }

                 //Show progress bar
                String user = username.replace(".","+") + "@hotmailcom";
                if(username.equals("admin") && password.equals("admin")){
                    username = "admin@admin.com";
                    password = "admin1";
                } else{
                    username = username + "@hotmail.com";
                }
                String finalUser = username;

                //Firebase Authentication
                fAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users");
                            users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.child(user).exists()&&!(finalUser.equals("admin@admin.com"))){
                                        Toast.makeText(Login.this,"Account was deleted.",Toast.LENGTH_LONG).show();
                                        FirebaseAuth.getInstance().signOut();
                                    } else{
                                        Toast.makeText(Login.this, "Account successfully signed in.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else{
                            Toast.makeText(Login.this, "Account unsuccessfully signed in. " + task.getException().getMessage().replace("email address","username"), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE); //remove progress bar
                        }

                    }
                });
            }
        });

        aCreateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}