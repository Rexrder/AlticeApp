package com.altice.alticeapp;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.VideoView;

import com.altice.alticeapp.databinding.ActivityVideoshowBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoShow extends AppCompatActivity {

    private ActivityVideoshowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVideoshowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.activity_videoshow);

        VideoView VideoInstance = (VideoView)findViewById(R.id.videoView);
        int resource = getIntent().getIntExtra("videolink",0);
        String UrlPath = "android.resource://com.altice.alticeapp/"+resource;
        Uri UriPath = Uri.parse(UrlPath);
        VideoInstance.setVideoURI(UriPath);
        VideoInstance.requestFocus();
        VideoInstance.start();
        VideoInstance.setOnCompletionListener(mp -> VideoShow.this.finish());
    }

}