package com.example.snakemitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email,passwd;
    private Button loginBtn,resetBtn,signUpBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editTextTextEmailAddress);
        passwd=findViewById(R.id.editTextTextPassword);
        loginBtn=findViewById(R.id.button);
        resetBtn=findViewById(R.id.resetBtn);
        signUpBtn=findViewById(R.id.button3);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt=email.getText().toString();
                String passwdTxt=passwd.getText().toString();
                if(emailTxt.trim().isEmpty()) email.setError("Email is required");
//                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                else if(passwdTxt.isEmpty()) passwd.setError("Password is required");
//                    Toast.makeText(LoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                else {
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(emailTxt, passwdTxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this, "Successs", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt=email.getText().toString();
                progressDialog.setTitle("Sending Mail");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(emailTxt).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.cancel();
                        Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }
}