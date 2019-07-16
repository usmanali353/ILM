package com.example.irfangujjar.ilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_page extends AppCompatActivity {
TextInputEditText email;
TextInputEditText password;
FirebaseAuth auth;
SharedPreferences prefs;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email=findViewById(R.id.email_txt);
        password=findViewById(R.id.password_txt);
        btn=findViewById(R.id.btn);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        auth=FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog waiting_dialog=new SpotsDialog.Builder().setContext(Login_page.this).build();
                waiting_dialog.setMessage("Authenticating User");
                waiting_dialog.setCancelable(false);

                if (email.getText().toString() == null || email.getText().toString().isEmpty()) {
                    email.setError("Email is required");
                } else if (password.getText().toString().isEmpty() || password.getText().toString() == null) {
                    password.setError("Password is Required");
                } else {
                    waiting_dialog.show();
                    auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                waiting_dialog.dismiss();
                                prefs.edit().putString("user_type", "Admin").apply();
                                startActivity(new Intent(Login_page.this, MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            waiting_dialog.dismiss();
                            Toast.makeText(Login_page.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });


    }
}
