package com.example.irfangujjar.ilm.Scholars;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.MediaController;

import com.example.irfangujjar.ilm.R;

public class tariqmasood extends AppCompatActivity {
    Button tmasodplay1;
    VideoView tmasoodview1;
    MediaController tmasoodmediac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariqmsood);
        tmasodplay1=(Button)findViewById(R.id.tmasoodbtnplay);
        tmasoodview1=(VideoView)findViewById(R.id.tmasoodvv1);
        tmasoodmediac=new MediaController(this);
    }
    public void videoplay (View v)
    {
        String videopath="android.resource://com.example.irfangujjar.ilm/"+R.raw.tariqmasoodhalal;
        Uri uri= Uri.parse(videopath);
        tmasoodview1.setVideoURI(uri);
        tmasoodview1.setMediaController(tmasoodmediac);
        tmasoodmediac.setAnchorView(tmasoodview1);
        tmasoodview1.start();
    }
}
