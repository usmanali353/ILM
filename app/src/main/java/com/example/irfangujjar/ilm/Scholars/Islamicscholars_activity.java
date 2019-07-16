package com.example.irfangujjar.ilm.Scholars;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.irfangujjar.ilm.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class Islamicscholars_activity extends AppCompatActivity {


    public CircleImageView zakirNaik;
    public CircleImageView biliPhilips;
    public CircleImageView muftiMenk;
    public CircleImageView omerSuleman;
    public CircleImageView saqibRaza;
    public CircleImageView tariqJameel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.islamicscholars_activity);



        zakirNaik=(CircleImageView)findViewById(R.id.btnZakir);
        zakirNaik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Islamicscholars_activity.this, tariqmasood.class);
                startActivity(i);
            }
        });

        biliPhilips=(CircleImageView)findViewById(R.id.btnBiliphilips);
        biliPhilips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Islamicscholars_activity.this, BilliPhilips.class);
                startActivity(i);
            }
        });

        muftiMenk=(CircleImageView)findViewById(R.id.btnMuftimenk);
        muftiMenk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Islamicscholars_activity.this, MuftiMenk.class);
                startActivity(i);
            }
        });

        omerSuleman=(CircleImageView)findViewById(R.id.btnOmersuleiman);
        omerSuleman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Islamicscholars_activity.this, yusufestate.class);
                startActivity(i);
            }
        });

        saqibRaza=(CircleImageView)findViewById(R.id.btnSaqib);
        saqibRaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Islamicscholars_activity.this, SaqibRaza.class);
                startActivity(i);
            }
        });

        tariqJameel=(CircleImageView)findViewById(R.id.btnTariqJameel);
        tariqJameel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Islamicscholars_activity.this, TariqJameel.class);
                startActivity(i);
            }
        });



    }

}
