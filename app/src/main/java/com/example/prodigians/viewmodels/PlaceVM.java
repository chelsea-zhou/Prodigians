package com.example.prodigians.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.prodigians.models.PlaceRecord;
import com.example.prodigians.repositories.PlaceRecordRepository;

import java.util.List;
import java.util.*;
public class PlaceVM extends ViewModel{

    private MutableLiveData<List<PlaceRecord>> mRecords;
    private PlaceRecordRepository mRepo;

    public void init(){
        if(mRecords != null){
            return;
        }
        mRepo = PlaceRecordRepository.getInstance();
        mRecords = mRepo.getRecords();
    }

    public LiveData<List<PlaceRecord>> getRecords(){
        return mRecords;
    }

    public List<PlaceRecord> topThree(){
        List <PlaceRecord> places = mRepo.getRecords().getValue();
        if (places.size()<=3){
            return places;
        }else{
            Collections.sort(places);
            List<PlaceRecord> topPlaces = places.subList(0,3);
            return topPlaces;
        }
    }
}
