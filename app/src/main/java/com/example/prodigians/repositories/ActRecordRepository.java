package com.example.prodigians.repositories;


import android.arch.lifecycle.MutableLiveData;

import com.example.prodigians.R;
import com.example.prodigians.database.ActRecordEntity;
import com.example.prodigians.database.AppDatabase;
import com.example.prodigians.database.RecordDao;
import com.example.prodigians.models.ActRecord;
import com.example.prodigians.models.DetectActivity;
import com.example.prodigians.models.MyApplication;
import com.example.prodigians.models.RawData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import static com.google.android.gms.location.DetectedActivity.IN_VEHICLE;
import static com.google.android.gms.location.DetectedActivity.ON_BICYCLE;
import static com.google.android.gms.location.DetectedActivity.ON_FOOT;
import static com.google.android.gms.location.DetectedActivity.RUNNING;
import static com.google.android.gms.location.DetectedActivity.STILL;
import static com.google.android.gms.location.DetectedActivity.WALKING;
import static java.lang.Math.max;

/**
 * Singleton pattern
 */
public class ActRecordRepository implements Observer{
    private static final String TAG = ActRecordRepository.class.getSimpleName();

    private static ActRecordRepository instance;
    private ArrayList<ActRecord> dataSet = new ArrayList<>();
    private RecordDao recordDao;
    private ActRecordEntity new_data;
    private ArrayList<RawData> data= new ArrayList<>();

    public MutableLiveData<List<ActRecord>> mrecords;
    DetectActivity activity;

    public ActRecordRepository(){
        // setup database
        AppDatabase database = AppDatabase.getInstance(MyApplication.getAppContext());
        recordDao = database.recordDao();

        //start activity detection

        activity= new DetectActivity(this);
        System.out.println("created detect activity class");
        activity.registerHandler();
        System.out.println("registered handler");
        // get reference to livedata
        mrecords = activity.getRecords();

    }

    public static ActRecordRepository getInstance(){
        if(instance == null){
            Log.i("datarepo","created data repo");
            System.out.println("created data repo");
            instance = new ActRecordRepository();

        }
        return instance;
    }

    public String getDate(){
        String pattern = "yyyy/MM/dd HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
    public String getTime(String fdate){
        String time = fdate.substring(11);
        System.out.println("time is" + time);
        String hh = time.substring(0,2);
        System.out.println("hour is" + hh);
        int h = Integer.parseInt(hh);
        if (h<12){
            time += " am";
        }else{
            time +=  " pm";
        }
        return time;

    }
    public String getTruncate(String date){
        //year/month/day
        return date.substring(0,11);
    }




    public void insert_queue(RawData d){
        data.add(d);
    }

    public ActRecordEntity process_event(RawData d) {
        int activity = d.act_type;
        int trans = d.trans_type;
        long end_time = d.current_time;
        String act;
        String date = getDate();
        int imageid=0;
        int duration = 0;
        ActRecordEntity act_record = new ActRecordEntity(0,"",0,0,0,"");

        switch (activity) {
            case IN_VEHICLE:
                imageid = R.drawable.vehicle;
                act = "In vehicle";
                break;
            case ON_BICYCLE:
                imageid = R.drawable.cycle;
                act = "ON_BICYCLE";
                break;
            case STILL:
                imageid = R.drawable.still;
                act = "Still";
                break;
            case WALKING:
                imageid = R.drawable.walk;
                act = "Walk";
                break;
            case RUNNING:
                imageid = R.drawable.run;
                act = "Run";
                break;
                // should i comment this?
            case ON_FOOT:
                act = "On Foot";
            default:
                act = "Unknown";
                break;
        }

            Log.i(TAG, "exit activity + size " + data.size());
            if (data.size() > 0) {
                for (RawData rd : data) {
                    if (rd.act_type == activity) {
                        int dur =  max(1,(int) ((end_time -rd.current_time) * Math.pow(10, -9)/60));

                        act_record = new ActRecordEntity(imageid,act,rd.current_time,end_time,dur,date);
                        break;
                    }
                }
            }else{
                Log.i(TAG, "data list is not persistent");
            }

        return act_record;
    }



    @Override
    public void update(Observable o,Object actrecord){
        System.out.println("insert object to db");

        RawData rd = (RawData) actrecord;
        if (rd.trans_type==0){
            data.add(rd);
        }else{
            ActRecordEntity ar = process_event(rd);
            insert(ar);
            Log.i("datarepo", "mutate live data ");


            addNewValue(new ActRecord(
                    ar.getImageid(),
                    ar.getActivity_type(),
                    ar.getDuration(),
                    0.5,
                    "Today",
                    getTime(ar.getDate())));
        }
    }


    // currently only insert
    public void insert(ActRecordEntity actrecord){
        new InsertRecordAsyncTask(recordDao).execute(actrecord);

    }

    // need corresponding delete asyntack class
    public void delete(ActRecordEntity actrecord){

    }

    public MutableLiveData<List<ActRecord>> getActivityRecords(){
        return mrecords;
    }

    private static class InsertRecordAsyncTask extends AsyncTask<ActRecordEntity,Void,Void>{
        private RecordDao recordDao;

        private InsertRecordAsyncTask(RecordDao recordDao){
            this.recordDao = recordDao;
        }

        @Override
        protected Void doInBackground(ActRecordEntity...actrecords){
            // why 0 ?
            recordDao.insert(actrecords[0]);
            return null;
        }
    }

    public void addNewValue(final ActRecord actRecord) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<ActRecord> currentRecords = mrecords.getValue();
                currentRecords.add(actRecord);
                mrecords.postValue(currentRecords);

            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }


}











