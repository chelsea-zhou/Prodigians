package com.example.prodigians.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.prodigians.models.ActRecord;

import com.example.prodigians.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Singleton pattern
 */
public class ActRecordRepository {

    private static ActRecordRepository instance;
    private ArrayList<ActRecord> dataSet = new ArrayList<>();


    public static ActRecordRepository getInstance(){
        if(instance == null){
            instance = new ActRecordRepository();
            instance.dataSet.add(
                    new ActRecord(R.drawable.cycle,
                            "Cycling",
                            12,
                            13));
        }
        return instance;
    }


    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<ActRecord>> getNicePlaces(){
        setNicePlaces();
        MutableLiveData<List<ActRecord>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setNicePlaces(){
//        dataSet.add(
//                new ActRecord(R.drawable.cycle,
//                        "Cycling",
//                        12,
//                        13)
////        );
//        dataSet.add(
//                new ActRecord("https://i.redd.it/tpsnoz5bzo501.jpg",
//                        "Trondheim")
//        );
//        dataSet.add(
//                new ActRecord("https://i.redd.it/qn7f9oqu7o501.jpg",
//                        "Portugal")
//        );
//        dataSet.add(
//                new ActRecord("https://i.redd.it/j6myfqglup501.jpg",
//                        "Rocky Mountain National Park")
//        );
//        dataSet.add(
//                new ActRecord("https://i.redd.it/0h2gm1ix6p501.jpg",
//                        "Havasu Falls")
//        );
//        dataSet.add(
//                new ActRecord("https://i.redd.it/k98uzl68eh501.jpg",
//                        "Mahahual")
//        );
//        dataSet.add(
//                new ActRecord("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
//                        "Frozen Lake")
//        );
//        dataSet.add(
//                new ActRecord("https://i.redd.it/obx4zydshg601.jpg",
//                        "Austrailia")
//        );
    }
}











