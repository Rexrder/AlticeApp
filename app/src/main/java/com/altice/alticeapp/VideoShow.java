package com.altice.alticeapp;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.altice.alticeapp.databinding.ActivityVideoshowBinding;
import com.robotemi.sdk.Robot;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoShow extends AppCompatActivity {
    Robot robot;

    private ActivityVideoshowBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        robot = Robot.getInstance();

        binding = ActivityVideoshowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.activity_videoshow);

        VideoView videoInstance = (VideoView)findViewById(R.id.videoView);
        ImageButton skipImage = (ImageButton)findViewById(R.id.skipButton);
        skipImage.setVisibility(View.GONE);
        int resource = getIntent().getIntExtra("videoinfo",0);
        Log.d("resource", String.valueOf(resource));
        String UrlPath = "android.resource://com.altice.alticeapp/"+resource;
        Uri UriPath = Uri.parse(UrlPath);
        videoInstance.setVideoURI(UriPath);
        videoInstance.setBackgroundColor(Color.TRANSPARENT);
        videoInstance.requestFocus();
        videoInstance.start();
        skipImage.setOnClickListener(mp ->{
            videoInstance.stopPlayback();
            robot.stopMovement();
            finish();
        });
        videoInstance.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                if (videoInstance.isPlaying()) {
                    videoInstance.pause();
                    skipImage.setVisibility(View.VISIBLE);
                } else {
                    videoInstance.start();
                    skipImage.setVisibility(View.GONE);
                }
                return true;
            }
            return false;
        });

        videoInstance.setOnCompletionListener(mp -> {
            robot.stopMovement();
            finish();
        });
    }

}