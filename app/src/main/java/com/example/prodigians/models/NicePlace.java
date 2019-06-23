package com.example.prodigians.models;



public class NicePlace {

    private String activity;
    private int imageID;
    private int time_len;
    private int distance;

    public NicePlace(int imageID, String activity,int time_len,int distance) {
        this.activity = activity;
        this.imageID = imageID;
        this.time_len = time_len;
        this.distance = distance;
    }

    public NicePlace() {
    }

//acticity type
    public String getTitle() {
        return activity;
    }

    public void setTitle(String activity) {
        this.activity = activity;
    }

//image for activity
    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

//length of activity time
    public String getTime() {
        return String.valueOf(time_len) + " min";
    }

    public void setTime(int time_len) {
        this.time_len = time_len;
    }

//length of distance
    public String getDistance() {
        return String.valueOf(distance) + " miles";
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


}
