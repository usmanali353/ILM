package com.example.irfangujjar.ilm.Scholars;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.MediaController;

import com.example.irfangujjar.ilm.R;

public class yusufestate extends AppCompatActivity {
    Button yusufbtnplay1;
    VideoView yusufvv1;
    MediaController yusufmediac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yusufestate);

        yusufbtnplay1=(Button)findViewById(R.id.yusufestatbtnplay);
        yusufvv1=(VideoView)findViewById(R.id.yusufestatevv1);
        yusufmediac= new MediaController(this);
    }
     public void videoplay (View v)
     {
         String videopath="android.resource://com.example.irfangujjar.ilm/"+R.raw.yusufestathalal;
         Uri uri=Uri.parse(videopath);
         yusufvv1.setVideoURI(uri);
         yusufvv1.setMediaController(yusufmediac);
         yusufmediac.setAnchorView(yusufvv1);
         yusufvv1.start();
     }
}
