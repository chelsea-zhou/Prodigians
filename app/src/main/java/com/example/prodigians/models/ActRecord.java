package com.example.prodigians.models;



public class ActRecord {

    private String activity;
    private int imageID;
    private int time_len;
    private int distance;

    public ActRecord(int imageID, String activity, int time_len, int distance) {
        this.activity = activity;
        this.imageID = imageID;
        this.time_len = time_len;
        this.distance = distance;
        //this.date = date;
        //LocalDate today = LocalDate.now(); // only year, month, date
        //int month = today.getMonthValue();
        //get day of week

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
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }





}
