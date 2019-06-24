package com.example.prodigians.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.prodigians.models.PlaceRecord;

import com.example.prodigians.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Singleton pattern
 */
public class PlaceRecordRepository {

    private static PlaceRecordRepository instance;
    private ArrayList<PlaceRecord> dataSet = new ArrayList<>();


    public static PlaceRecordRepository getInstance() {
        if (instance == null) {
            instance = new PlaceRecordRepository();
            instance.dataSet.add(
                    new PlaceRecord(R.drawable.study,
                            "study",
                            75));
            instance.dataSet.add(
                    new PlaceRecord(R.drawable.food,
                            "food",
                            45));
            instance.dataSet.add(
                    new PlaceRecord(R.drawable.finance,
                            "finance",
                            15));
        }
        return instance;
    }

    public MutableLiveData<List<PlaceRecord>> getRecords(){
        MutableLiveData<List<PlaceRecord>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

}











