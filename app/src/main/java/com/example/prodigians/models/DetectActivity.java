package com.example.prodigians.models;

import android.app.PendingIntent;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.prodigians.repositories.ActRecordRepository;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class DetectActivity {
    public Context mContext = MyApplication.getAppContext();

    private List<ActivityTransition> transitions;
    private ActivityRecognitionClient activityRecognitionClient;
    private PendingIntent transitionPendingIntent;
    private TransitionIntentService TIS;
    //private MutableLiveData<List<ActRecord>> mRecords;

    public DetectActivity(ActRecordRepository datarepo){
        activityRecognitionClient = ActivityRecognition.getClient(mContext);
        Intent intent = new Intent(mContext, TransitionIntentService.class);

        transitionPendingIntent = PendingIntent.getService(mContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println("created pending intent");
        TIS = TransitionIntentService.getInstance();
        TIS.observable.addObserver(datarepo);
        System.out.println("count observer after adding" + TIS.observable.countObservers());

    }
    public MutableLiveData<List<ActRecord>> getRecords(){
        return TIS.getRecords();
    }


    public void registerHandler(){
        transitions = new ArrayList<>();
        //register list of activity transitions, type + enter/exit
        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());


        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());


        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.RUNNING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());

        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.RUNNING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());

        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());

        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());

        //build request with transitions
        ActivityTransitionRequest activityTransitionRequest = new ActivityTransitionRequest(transitions);

        //register for listener with request and intent
        Task<Void> task = activityRecognitionClient.requestActivityTransitionUpdates(activityTransitionRequest, transitionPendingIntent);
        System.out.println("detection start running");
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mContext, "Transition update set up", Toast.LENGTH_LONG).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Transition update Failed to set up", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }

}
