package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class NewWord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        VideoView videoview = findViewById(R.id.videoView);
        videoview.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.sign);
        MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoview);
        videoview.setMediaController(mediacontroller);
        videoview.start();
    }

}