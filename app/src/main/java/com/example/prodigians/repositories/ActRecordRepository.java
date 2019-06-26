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
            instance.setRecords();
//            instance.dataSet.add(
//                    new ActRecord(
//                            R.drawable.walk,
//                            "WALK",
//                            12,
//                            0.57,
//                            "Wed June 26",
//                            "10:18"
//
//                    ));
        }
        return instance;
    }


    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<ActRecord>> getRecords(){
        //setRecords();
        MutableLiveData<List<ActRecord>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setRecords(){
        dataSet.add(
                    new ActRecord(
                            R.drawable.walk,
                            "Walk",
                            12,
                            0.57,
                            "Today",
                            "09:18 AM"

                    ));
        dataSet.add(
                new ActRecord(
                        R.drawable.still,
                        "Still",
                        53,
                        0.57,
                        "Today",
                        "09:37 AM"

                ));
        dataSet.add(
                new ActRecord(
                        R.drawable.cycle,
                        "Bike",
                        5,
                        0.6,
                        "Today",
                        "10:45 AM"

                ));
        dataSet.add(
                new ActRecord(
                        R.drawable.walk,
                        "Walk",
                        15,
                        0.65,
                        "Yesterday",
                        "10:37 PM"

                ));

        dataSet.add(
                new ActRecord(
                        R.drawable.walk,
                        "Walk",
                        17,
                        0.68,
                        "Yesterday",
                        "07:14 PM"

                ));


        dataSet.add(
                new ActRecord(
                        R.drawable.still,
                        "Still",
                        172,
                        0.57,
                        "Yesterday",
                        "04:02 PM"

                ));
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











