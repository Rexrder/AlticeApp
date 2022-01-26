package com.altice.alticeapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.robotemi.sdk.Robot;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mcon;
    private LayoutInflater mInflater;
    ArrayList<VideoInfo> videoinfos;

    public ItemAdapter(Context con, ArrayList<VideoInfo> videoinfos) {
        this.mInflater = LayoutInflater.from(con);
        this.mcon = con;
        this.videoinfos = videoinfos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton VidButton;
        TextView VidDescription;
        String descVideo;
        VideoInfo videoinfo;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View
            VidButton = (ImageButton)v.findViewById(R.id.videoButton);
            VidDescription = (TextView)v.findViewById(R.id.videoDesc);
            VidButton.setOnClickListener(view -> {
                Intent intent = new Intent(mcon,RobotActivity.class);
                intent.putExtra("videoinfo",videoinfo.urlVid);
                intent.putExtra("location",videoinfo.location);
                mcon.startActivity(intent);
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v =  mInflater.inflate(R.layout.item_column, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.videoinfo = videoinfos.get(position);
        holder.VidButton.setImageResource(holder.videoinfo.imgSrc);
        holder.VidDescription.setText(holder.videoinfo.descText);

    }

    @Override
    public int getItemCount() {
        return videoinfos.size();
    }
}