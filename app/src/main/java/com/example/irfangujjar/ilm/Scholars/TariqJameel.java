package com.example.irfangujjar.ilm.Scholars;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.irfangujjar.ilm.R;

public class TariqJameel extends AppCompatActivity {
    Button tjameelbtnplay1;
    VideoView tjameelvv1;
    MediaController tjameelmediac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariq_jameel);

        tjameelbtnplay1=(Button)findViewById(R.id.tariqjameelbtnplay);
        tjameelvv1=(VideoView)findViewById(R.id.tariqjameelvv1);
        tjameelmediac=new MediaController(this);
    }

    public void videoplay (View v)
    {
        String videopath="android.resource://com.example.irfangujjar.ilm/"+R.raw.tariqjameelhalal;
        Uri uri=Uri.parse(videopath);
        tjameelvv1.setVideoURI(uri);
        tjameelvv1.setMediaController(tjameelmediac);
        tjameelmediac.setAnchorView(tjameelvv1);
        tjameelvv1.start();
    }
}
