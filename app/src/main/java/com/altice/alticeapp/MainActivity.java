package com.altice.alticeapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
import com.robotemi.sdk.listeners.OnConversationStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        OnRobotReadyListener, Robot.AsrListener, OnUserInteractionChangedListener, OnDetectionStateChangedListener
        /*,OnConversationStatusChangedListener*/
{
    ItemAdapter adapter;
    Robot robot;
    RecyclerView recyclerview;
    private static Timer timer = new Timer();

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnRobotReadyListener(this);
        Robot.getInstance().addAsrListener(this);
        Robot.getInstance().addOnDetectionStateChangedListener(this);
        Robot.getInstance().addOnUserInteractionChangedListener(this);
        /*Robot.getInstance().addOnConversationStatusChangedListener(this);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnRobotReadyListener(this);
        Robot.getInstance().removeAsrListener(this);
        Robot.getInstance().removeOnDetectionStateChangedListener(this);
        Robot.getInstance().removeOnUserInteractionChangedListener(this);
        /*Robot.getInstance().removeOnConversationStatusChangedListener(this);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        robot = Robot.getInstance();
        robot.speak(TtsRequest.create("Welcome to the Showroom. Choose a project", false));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerview = findViewById(R.id.recycler_view_items);
        recyclerview.setLayoutManager(layoutManager);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerview);

        adapter = new ItemAdapter(this, getItems());
        recyclerview.setAdapter(adapter);
    }

    public ArrayList<VideoInfo> getItems(){
        return new ArrayList<VideoInfo>(){
                {
                    add(new VideoInfo(R.raw.smarthome_vision,"SmartHome Vision",R.drawable.smarthome,"smart home",new String[]{"smart-home promo", "smart home promo", "promo","smart home","Smart Home","smartphone"}));
                    add(new VideoInfo(R.raw.sfp_secret_promo_v2,"SFP",R.drawable.sfp,"sfp",new String[]{"sfp", "sfb", "SFP"}));
                    add(new VideoInfo(R.raw.olt2tx_en,"New OLT2Tx Family",R.drawable.olt2tx,"olt2tx family",new String[]{"olt2tx", "olt2t", "olt2tx family","olt family","olt"}));
                    add(new VideoInfo(R.raw.small_cells5g_en,"Small Cells 5G",R.drawable.small_cells5g,"small cells 5g", new String[]{"small cells", "small cells 5g", "5g","small sales","small cell","Smokehouse"}));
                }
            };
    }

    @Override
    public void onRobotReady(boolean b) {
        Log.d("DetMode", String.valueOf(robot.isDetectionModeOn()));
        if(b){
            refreshTemiUi();
        }
    }

    private void refreshTemiUi() {
        try {
            ActivityInfo activityInfo = getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Robot.getInstance().onStart(activityInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAsrResult(@NonNull String s) {
        Log.d("Response",s);
        for (int i=0; i < getItems().size(); i++){
            VideoInfo info = getItems().get(i);
            for(int j=0; j < info.asrText.length;j++){
                if (s.equals(info.asrText[j])){
                    Intent intent = new Intent(this,RobotActivity.class);
                    intent.putExtra("videoinfo",info.urlVid);
                    intent.putExtra("location",info.location);
                    startActivity(intent);
                }
            }
        }
    }


    @Override
    public void onUserInteraction(boolean b) {
        Log.d("state", String.valueOf(b));
    }

    @Override
    public void onDetectionStateChanged(int i) {
        Log.d("Det", String.valueOf(i));
        if (i==0){
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    Log.d("timer","working");
                    finishAffinity();
                }
            }, 60 * 1000);
        }else{
            timer.cancel();
            timer = new Timer();
        }
    }

    /*@Override
    public void onConversationStatusChanged(int i, @NonNull String s) {
        Log.d("state", String.valueOf(i));
        Log.d("text",s);
    }*/

}
