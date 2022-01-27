package com.altice.alticeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.util.List;
import java.util.Objects;

public class RobotActivity extends AppCompatActivity implements
        OnBeWithMeStatusChangedListener, OnGoToLocationStatusChangedListener{

    Robot robot;
    boolean end = false;
    boolean userchoice = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        robot = Robot.getInstance();
        robot.speak(TtsRequest.create("Follow me", false));
        robot.goTo(getIntent().getExtras().getString("location"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().addOnGoToLocationStatusChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().removeOnGoToLocationStatusChangedListener(this);
    }

    @Override
    public void onBeWithMeStatusChanged(@NonNull String s) {
        Log.d("Detection", s);
        if(s.equals("track")){
            robot.constraintBeWith();
            Intent intent = new Intent(RobotActivity.this,VideoShow.class);
            intent.putExtra("videoinfo",getIntent().getIntExtra("videoinfo",0));
            startActivity(intent);
            userchoice = false;
            finish();
        }
        if(s.equals("abort") && userchoice)
        if (end){
            finish();
        }
        else{
            robot.beWithMe();
            end = true;
        }
    }

    @Override
    public void onGoToLocationStatusChanged(@NonNull String s, @NonNull String s1, int i, @NonNull String s2) {
        Log.d("GoToLoc",s1);
        if(s1.equals(COMPLETE)){
            robot.beWithMe();
        }
        if(s1.equals(OnBeWithMeStatusChangedListener.ABORT)){
            finish();
        }
    }
}