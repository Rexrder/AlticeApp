package com.altice.alticeapp;

public class VideoInfo {
    int urlVid;
    String descText;
    int imgSrc;
    String location;
    String[] asrText;
    public VideoInfo(int urlVid, String descText, int imgSrc, String location, String[] asrText){
        this.urlVid = urlVid;
        this.descText = descText;
        this.imgSrc = imgSrc;
        this.location = location;
        this.asrText = asrText;
    }
}
