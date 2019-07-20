package com.example.prodigians.models;



public class ActRecord {

    private String activity;
    private int imageID;
    private int time_len;
    private double distance;
    private String exact_time;
    private String date;

    public ActRecord(int imageID, String activity, int time_len, double distance,String date,String exact_time) {
        this.activity = activity;
        this.imageID = imageID;
        this.time_len = time_len;
        this.distance = distance;
        this.exact_time = exact_time;
        this.date = date;

    }

    public ActRecord() {
    }

//acticity type
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
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
    public int getTime() {
        return time_len;
    }

    public void setTime(int time_len) {
        this.time_len = time_len;
    }

//length of distance
    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getExactTime() {
        return exact_time;
    }
    public String getDate() {
        return date;
    }
}
