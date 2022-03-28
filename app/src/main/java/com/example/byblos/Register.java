package com.example.byblos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText aFullName, aPassword, aUsername, apassword_conf;
    Button aSignUp_button;
    TextView aLoginText;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    private FirebaseDatabase mDatabase;
    String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance();

        aFullName = findViewById(R.id.FullName);
        aPassword = findViewById(R.id.RegisterPassword);
        aUsername = findViewById(R.id.RegisterUsername);
        apassword_conf = findViewById(R.id.password_conf);
        aSignUp_button = findViewById(R.id.SignUp_button);
        aLoginText = findViewById(R.id.Login_Text);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarRegister);

        if (fAuth.getCurrentUser() != null){ //check if user is already logged in
            startActivity(new Intent(getApplicationContext(), MainActivity.class)); // send the user to the main screen
            finish();
        }

        aSignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = aUsername.getText().toString().trim();
                String password = aPassword.getText().toString().trim();
                String username1 = aUsername.getText().toString().trim();
                String passwordconf = apassword_conf.getText().toString().trim();
                String fullName = aFullName.getText().toString().trim();

                //checks for empty full name field
                if (TextUtils.isEmpty(fullName)){
                    aFullName.setError("Please provide your name.");
                    return;
                }

                //Check empty email field
                if (TextUtils.isEmpty(username)){
                    aUsername.setError("Please provide your email.");
                    return;
                }

                //Check empty password field
                if (TextUtils.isEmpty(password)){
                    aPassword.setError("Please provide a password.");
                    return;
                }

                //checks for valid email
                if (username.contains("@")||username.equals("admin")||username.contains("+")){
                    aUsername.setError("Please provide a valid username.");
                    return;
                }
                //Check invalid password field
                if (password.length()<8){
                    aPassword.setError("Your password must be at least 8 characters.");
                    return;
                }

                //Check mismatching passwords
                if (!(password.equals(passwordconf))){
                    aPassword.setError("Passwords must match.");
                    apassword_conf.setError("Passwords must match.");
                    return;
                }

                DatabaseReference email2 = mDatabase.getReference("Users");
                username = username + "@hotmail.com";
                String email3 = username.replace("@hotmail.com","@hotmailcom").replace(".","+");
                //email3 = email3.replace(".","+");


                progressBar.setVisibility(View.VISIBLE);

                //Create the user in Firebase using credentials
                fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Account successfully registered.", Toast.LENGTH_SHORT).show();
                            CheckBox checkBox = (CheckBox) findViewById(R.id.role2);
                            if (checkBox.isChecked()) {
                                role = "Employee";
                                email2.child(email3).child("role").setValue(role);
                                email2.child(email3).child("username").setValue(username1);
                            } else{
                                role = "Customer";
                                email2.child(email3).child("role").setValue(role);
                                email2.child(email3).child("username").setValue(username1);
                            }
                            email2.child(email3).child("fullname").setValue(fullName);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else{
                            Toast.makeText(Register.this, "Account unsuccessfully registered. " + task.getException().getMessage().replace("email address","username"), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        aLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}