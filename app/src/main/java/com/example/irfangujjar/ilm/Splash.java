package com.example.irfangujjar.ilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class Splash extends AppCompatActivity {
Button user,admin;
SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user=findViewById(R.id.user_btn);
        admin=findViewById(R.id.admin_btn);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("user_type","User").apply();
                startActivity(new Intent(Splash.this,MainActivity.class));
                finish();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this,Login_page.class));
            }
        });
    }
}
