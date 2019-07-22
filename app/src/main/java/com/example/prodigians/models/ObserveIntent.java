package com.example.prodigians.models;

import android.util.Log;

import java.util.Observable;

public  class ObserveIntent extends Observable {
    private static final String TAG = ObserveIntent.class.getSimpleName();

    private static ObserveIntent instance;

    public void notice(RawData rawData){
        Log.i("observable", " noticing observers!!!!");
        setChanged();
        countObservers( );
        notifyObservers(rawData);
    }
    public static ObserveIntent getInstance(){
        if(instance == null){
            Log.i(TAG," service created");
            instance = new ObserveIntent();
        }
        return instance;
    }

}