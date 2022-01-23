package com.altice.alticeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ItemAdapter adapter;

    RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerview = findViewById(R.id.recycler_view_items);
        recyclerview.setLayoutManager(layoutManager);

        adapter = new ItemAdapter(this, getItems());
        recyclerview.setAdapter(adapter);
    }

    public ArrayList<VideoInfo> getItems(){
        return new ArrayList<VideoInfo>(){
                {
                    add(new VideoInfo(R.raw.smarthome_vision,"SmartHome Vision",R.drawable.smarthome_vision));
                    add(new VideoInfo(R.raw.smarthome_promo,"SmartHome Promo",R.drawable.smarthome_promo));
                    add(new VideoInfo(R.raw.sfp_secret_promo_v2,"SFP",R.drawable.sfp_secret_promo_v2_));
                    add(new VideoInfo(R.raw.olt2tx_en,"New OLT2Tx Family",R.drawable.olt2tx_en));
                    add(new VideoInfo(R.raw.small_cells5g_en,"Small Cells 5G",R.drawable.small_cells5g));
                }
            };
    }
}