package com.example.prodigians.models;

import android.app.IntentService;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.prodigians.R;
import com.example.prodigians.database.ActRecordEntity;
import com.example.prodigians.repositories.ActRecordRepository;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.google.android.gms.location.ActivityTransition.ACTIVITY_TRANSITION_ENTER;
import static com.google.android.gms.location.DetectedActivity.IN_VEHICLE;
import static com.google.android.gms.location.DetectedActivity.ON_BICYCLE;
import static com.google.android.gms.location.DetectedActivity.ON_FOOT;
import static com.google.android.gms.location.DetectedActivity.RUNNING;
import static com.google.android.gms.location.DetectedActivity.STILL;
import static com.google.android.gms.location.DetectedActivity.WALKING;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;



public class TransitionIntentService extends IntentService {

    private static final String TAG = TransitionIntentService.class.getSimpleName();


    // activity, start time, end time, date, distance using gps
    private static TransitionIntentService instance;
    private ArrayList<ActRecord> dataSet = new ArrayList<>();
    public ObserveIntent observable;
    private ActRecordRepository datarepo;
    private List<ActRecordEntity> data;

    public TransitionIntentService() {
        super("TransitionIntentService");
        //data = new ArrayList<>();
        setRecords();
        observable = ObserveIntent.getInstance();

    }

    public static TransitionIntentService getInstance(){
        if(instance == null){
            Log.i(TAG," service created");
            instance = new TransitionIntentService();

        }
        return instance;
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (ActivityTransitionResult.hasResult(intent)) {
                ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

                for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                    System.out.println("detected event!");
                    int act_type = event.getActivityType();
                    int trans_type = event.getTransitionType();
                    long current_time =event.getElapsedRealTimeNanos();
                    Log.i(TAG, "Activity Type " + act_type);
                    Log.i(TAG, "Transition Type " + trans_type);
                    Log.i(TAG,"TIME" + current_time);
                    System.out.println("number of observers" + observable.countObservers());
                    observable.notice(new RawData(act_type,trans_type,current_time));

                }
            }
        }
    }

    public String getDate(){
        String pattern = "yyyy/MM/dd";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }

//    public ActRecordEntity wrapper(String activity_type, String start_time, String end_time, int duration, String date){
//        return new ActRecordEntity();
//    }

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
                        15,
                        0.57,
                        "Today",
                        "09:18 AM"

                ));
        dataSet.add(
                new ActRecord(
                        R.drawable.still,
                        "Still",
                        53,
                        0,
                        "Today",
                        "09:37 AM"

                ));


        }
    }

//        dataSet.add(
//                new ActRecord(
//                        R.drawable.cycle,
//                        "Bike",
//                        5,
//                        0.6,
//                        "Today",
//                        "10:45 AM"
//
//                ));
//
//        dataSet.add(
//                new ActRecord(
//                        R.drawable.still,
//                        "Still",
//                        172,
//                        0,
//                        "Yesterday",
//                        "04:02 PM"
//
//                ));
//        try{
//            while(true){
//                Thread.sleep(10000);
//                dataSet.add(
//                        new ActRecord(
//                                R.drawable.still,
//                                "Still",
//                                172,
//                                0,
//                                "Yesterday",
//                                "04:02 PM"
//
//                        )
//
//                );
//
//        }}catch(Exception e){
//                System.out.println("adding data failed");
//            }


