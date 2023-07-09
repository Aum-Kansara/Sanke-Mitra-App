package com.example.snakemitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snakemitra.R;
import com.example.snakemitra.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private EditText name,email,number,passwd;
    private Button btn,loginBtn;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.signUpBtn);
        name=findViewById(R.id.editTextText);
        number=findViewById(R.id.editTextNumberPassword);
        email=findViewById(R.id.editTextTextEmailAddress2);
        passwd=findViewById(R.id.editTextTextPassword2);
        progressDialog=new ProgressDialog(this);
        loginBtn=findViewById(R.id.button4);
        firebaseFirestore=FirebaseFirestore.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt=name.getText().toString();
                String numberTxt=number.getText().toString();
                String emailTxt=email.getText().toString();
                String passwdTxt=passwd.getText().toString();
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(emailTxt,passwdTxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        progressDialog.cancel();
//                        firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid()).set(new UserModel(nameTxt,numberTxt,emailTxt));
                        Map<String,String> user=new HashMap<>();
                        user.put("name",nameTxt);
                        user.put("email",emailTxt);
                        user.put("number",numberTxt);
                        firebaseFirestore.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("DocumentID", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Documenterror",e.getMessage(),e);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
}