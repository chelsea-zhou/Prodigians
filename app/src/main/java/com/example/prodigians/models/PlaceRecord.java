package com.example.prodigians.models;

import java.util.Comparator;
import java.util.Objects;
import java.util.*;

public class PlaceRecord implements Comparable<PlaceRecord>{

    private String place;
    private int imageID;
    private int time;

    public PlaceRecord(int imageID, String place, int time){
        this.place = place;
        this.imageID = imageID;
        this.time = time;
    }

    public PlaceRecord(){
    }

    @Override
    public int compareTo(PlaceRecord o) {
        Integer p1, p2;
        p1 = new Integer(this.getTime());
        p2 = new Integer(o.getTime());
        return p1.compareTo(p2);
    }

    @Override
    public boolean equals(Object o){
        boolean result = false;
        if (o instanceof PlaceRecord){
            PlaceRecord ptr = (PlaceRecord) o;
            result = this.place.equalsIgnoreCase(ptr.place);
        }
        return result;
    }

    /*@Override
    public int hashcode(){
        int result = 13;

        result = 29 * result * place.hashCode();

        return result;

    }*/

    //place type
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    //image for place type
    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    //length of time in location
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }




}
