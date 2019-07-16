package com.example.irfangujjar.ilm.Scholars;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.irfangujjar.ilm.R;

public class MuftiMenk extends AppCompatActivity {
    Button menkbtn1;
    VideoView menkvv1;
    MediaController menkmediac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mufti_menk);
        menkbtn1=(Button)findViewById(R.id.menkbtnplay);
        menkvv1=(VideoView)findViewById(R.id.menkvv1);
        menkmediac=new MediaController(this);
    }
    public void videoplay (View v)
    {
        String videopath="android.resource://com.example.irfangujjar.ilm/"+R.raw.muftimenkharamfood;
        Uri uri=Uri.parse(videopath);
        menkvv1.setVideoURI(uri);
        menkvv1.setMediaController(menkmediac);
        menkmediac.setAnchorView(menkvv1);
        menkvv1.start();
    }
}
