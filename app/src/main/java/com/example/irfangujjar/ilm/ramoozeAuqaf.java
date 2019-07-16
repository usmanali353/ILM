package com.example.irfangujjar.ilm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ramoozeAuqaf extends AppCompatActivity {
    ListView lstvewramz;
    String[] title;
    String[] description;
    String [] example;
    int[] audio;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramooze_auqaf);
        lstvewramz=(ListView)findViewById(R.id.lvRamuz);
        Intent n=getIntent();
        title=n.getStringArrayExtra("title");
        description=n.getStringArrayExtra("desc");
        example=n.getStringArrayExtra("example");
        audio=n.getIntArrayExtra("audio");
        lstvewramz.setAdapter(new customAdapter(title,description,example,audio,ramoozeAuqaf.this));
        lstvewramz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mediaPlayer=MediaPlayer.create(getApplicationContext(),audio[position]);
                    mediaPlayer.start();
              //  Toast.makeText(getApplicationContext(),"hello"+audio[position],Toast.LENGTH_LONG).show();

            }
        });

    }
}
