package com.altice.alticeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.constants.CliffSensorMode;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity implements
        OnBeWithMeStatusChangedListener, OnRobotReadyListener {

    Robot robot;

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().addOnRobotReadyListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().removeOnRobotReadyListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        robot = Robot.getInstance();
    }

    @Override
    public void onBeWithMeStatusChanged(@NonNull String s) {
        if(s.equals("track")){
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            robot.stopMovement();
            finish();
        }
    }

    @Override
    public void onRobotReady(boolean b) {
        robot.requestToBeKioskApp();
        robot.setTrackUserOn(false);
        robot.setDetectionModeOn(true);
        Log.d("DetMode", String.valueOf(robot.isSelectedKioskApp()));
        Log.d("kiosk", String.valueOf(robot.isDetectionModeOn()));
        robot.goTo("start");
    }
}