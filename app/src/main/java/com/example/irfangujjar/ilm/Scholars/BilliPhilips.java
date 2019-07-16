package com.example.irfangujjar.ilm.Scholars;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.MediaController;

import com.example.irfangujjar.ilm.R;

public class BilliPhilips extends AppCompatActivity {
    Button billplay1;
    VideoView billlview1;
    MediaController billmediac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billi_philips);

        billplay1=(Button)findViewById(R.id.btnplaybilli);
        billlview1=(VideoView)findViewById(R.id.billivv1);
        billmediac=new MediaController(this);

    }
    public void videoplay (View v)
    {
        String videopath="android.resource://com.example.irfangujjar.ilm/"+ R.raw.billhalalfood;
        Uri uri= Uri.parse(videopath);
        billlview1.setVideoURI(uri);
        billlview1.setMediaController(billmediac);
        billmediac.setAnchorView(billlview1);
        billlview1.start();
    }
}
