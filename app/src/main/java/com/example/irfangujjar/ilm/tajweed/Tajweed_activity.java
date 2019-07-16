package com.example.irfangujjar.ilm.tajweed;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.irfangujjar.ilm.R;
import com.example.irfangujjar.ilm.ramoozeAuqaf;

public class Tajweed_activity extends AppCompatActivity {
    Button ramuzeAqaf;
    Button btnThroat;
    Button btnWhistlingWords;
    Button btnProlongingWords;
    Button btnEchoWords;
    Button btnGhunnah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tajweed_activity);
        ramuzeAqaf = (Button) findViewById(R.id.btnramuzeauqaf);
        btnThroat = (Button) findViewById(R.id.btnthroatal);
        btnWhistlingWords = (Button) findViewById(R.id.btnwhistling);
        btnProlongingWords = (Button) findViewById(R.id.btnprolonging);
        btnEchoWords=(Button) findViewById(R.id.btnecho);
        btnGhunnah=(Button) findViewById(R.id.btnghunnah);


        final String[] ramuz_title = new String[]{getResources().getString(R.string.ramoz_title1),getResources().getString(R.string.ramoz_title2),getResources().getString(R.string.ramoz_title3),getResources().getString(R.string.ramoz_title4),getResources().getString(R.string.ramoz_title5),getResources().getString(R.string.ramoz_title6)};
        final String[] ramuz_description = {getResources().getString(R.string.ramooz_desc1),getResources().getString(R.string.ramoz_des2),getResources().getString(R.string.ramoz_des3),getResources().getString(R.string.ramoz_des4),getResources().getString(R.string.ramoz_des5),getResources().getString(R.string.ramoz_des6)};
        final String[] ramuz_example = {getResources().getString(R.string.ramooz_example1),getResources().getString(R.string.ramoz_example2),getResources().getString(R.string.ramoz_example3),getResources().getString(R.string.ramoz_example4),getResources().getString(R.string.ramoz_example5),getResources().getString(R.string.ramoz_example6)};
        final int[] ramuz_audio = {R.raw.surahkosar,R.raw.zalikalkitab,R.raw.hatamallahuala,R.raw.waizahaznamesaqa,R.raw.fequlubihimmarazun,R.raw.zalikalkitab};

        final String[] throatalTitle = {getResources().getString(R.string.trotal_title1)};
        final String[] throatalDescription = {getResources().getString(R.string.trotal_desc1)};
        final String[] throatalExample = {getResources().getString(R.string.trotal_example)};
        final int[] throatalAudio = {R.raw.qulaoozubrabil};

        final String[] whistlingTitle = {getResources().getString(R.string.whistling_title01),getResources().getString(R.string.whistling_title2),getResources().getString(R.string.whistling_title3),getResources().getString(R.string.whistling_title4)};
        final String[] whistlingDesc = {getResources().getString(R.string.whistling_desc01),getResources().getString(R.string.whistling_desc2),getResources().getString(R.string.whistling_desc3),getResources().getString(R.string.whistling_desc4)};
        final String[] whistlingExample = {getResources().getString(R.string.whistling_exampe01),getResources().getString(R.string.whistling_exampe01),getResources().getString(R.string.whistling_exampe2),getResources().getString(R.string.whistling_exampe3),getResources().getString(R.string.whistling_exampe4)};
        final int[] whistlingAudio = {R.raw.yaseen,R.raw.yaseen,R.raw.izazulzilatil,R.raw.ulaikaalazenashtrawuzla,R.raw.yaseen};


        final String[] prolongTitle = {getResources().getString(R.string.prolonging_title1)};
        final String[] prolongdesc = {getResources().getString(R.string.prolonging_desc1)};
        final String[] prolongexample = {getResources().getString(R.string.prolonging_exampleَ)};
        final int[] prolongAudio = {R.raw.qulyaayuhalkafiron};

        final String[] echoTitle1 = {getResources().getString(R.string.echo_title1),getResources().getString(R.string.echo_title2)};
        final String[] echodesc1= {getResources().getString(R.string.echo_desc1),getResources().getString(R.string.echo_desc2)};
        final String[] echoexample1 = {getResources().getString(R.string.echo_example1),getResources().getString(R.string.echo_example2)};
        final int[] echoAudio1 = {R.raw.yamashiruljinni,R.raw.qulyaayuhalkafiron};

        final String[] ghunnahTitle1={getResources().getString(R.string.ghunnah_title1),getResources().getString(R.string.ghunnah_title2)};
        final String[] ghunnahDesc1={getResources().getString(R.string.ghunnah_desc1),getResources().getString(R.string.ghunnah_desc2)};
        final String[] ghunnahExample1={getResources().getString(R.string.ghunnah_example1),getResources().getString(R.string.ghunnah_example2)};
        final int[] ghunnahAudio1={R.raw.ammayatasaloon,R.raw.innatainakalkosar};


        ramuzeAqaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title",ramuz_title);
                i.putExtra("desc", ramuz_description);
                i.putExtra("example", ramuz_example);
                i.putExtra("audio", ramuz_audio);
                startActivity(i);
            }
        });

        btnThroat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title", throatalTitle);
                i.putExtra("desc", throatalDescription);
                i.putExtra("example", throatalExample);
                i.putExtra("audio", whistlingAudio);
                startActivity(i);

            }
        });
        btnWhistlingWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title", whistlingTitle);
                i.putExtra("desc", whistlingDesc);
                i.putExtra("example", whistlingExample);
                i.putExtra("audio", whistlingAudio);
                startActivity(i);
            }
        });
        btnProlongingWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title", prolongTitle);
                i.putExtra("desc", prolongdesc);
                i.putExtra("example", prolongexample);
                i.putExtra("audio",prolongAudio);
                startActivity(i);
            }
        });
        btnEchoWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title", echoTitle1);
                i.putExtra("desc", echodesc1);
                i.putExtra("example", echoexample1);
                i.putExtra("audio", echoAudio1);
                startActivity(i);

            }
        });
        btnEchoWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title", echoTitle1);
                i.putExtra("desc", echodesc1);
                i.putExtra("example", echoexample1);
                i.putExtra("audio", echoAudio1);
                startActivity(i);

            }
        });
        btnGhunnah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tajweed_activity.this, ramoozeAuqaf.class);
                i.putExtra("title", ghunnahTitle1);
                i.putExtra("desc", ghunnahDesc1);
                i.putExtra("example", ghunnahExample1);
                i.putExtra("audio", ghunnahAudio1);
                startActivity(i);
            }
        });
    }
}
