package com.example.irfangujjar.ilm.Scholars;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.irfangujjar.ilm.R;

public class SaqibRaza extends AppCompatActivity {
    Button saqibtn1;
    VideoView saqibvv1;
    MediaController saqibmediac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saqib_raza);

        saqibtn1=(Button)findViewById(R.id.saqibbtnplay);
        saqibvv1=(VideoView)findViewById(R.id.saqibvv1);
        saqibmediac=new MediaController(this);
    }
    public void videoplay (View v)
    {
        String videopath="android.resource://com.example.irfangujjar.ilm/"+R.raw.saqibrazahalal;
        Uri uri=Uri.parse(videopath);
        saqibvv1.setVideoURI(uri);
        saqibvv1.setMediaController(saqibmediac);
        saqibmediac.setAnchorView(saqibvv1);
        saqibvv1.start();
    }
}
