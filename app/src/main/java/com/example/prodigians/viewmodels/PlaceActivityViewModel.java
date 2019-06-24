package com.example.prodigians.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.prodigians.R;
import com.example.prodigians.models.PlaceRecord;
import com.example.prodigians.repositories.PlaceRecordRepository;

import java.util.List;
import java.util.*;

public class PlaceActivityViewModel extends ViewModel{

    private MutableLiveData<List<PlaceRecord>> mRecords;
    private PlaceRecordRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();



    public void init(){
        if(mRecords != null){
            return;
        }
        mRepo = PlaceRecordRepository.getInstance();
        mRecords = mRepo.getRecords();
    }


    public void addNewValue(final PlaceRecord placeRecord){
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<PlaceRecord> currentRecords = mRecords.getValue();
                if (currentRecords.contains(placeRecord)){
                    int index = currentRecords.indexOf(placeRecord);
                    PlaceRecord replace = new PlaceRecord(placeRecord.getImageID(),
                            placeRecord.getPlace(),
                            75 + placeRecord.getTime());
                    placeRecord.setTime(replace.getTime());
                    currentRecords.set(index, replace);

                }
                else{
                    currentRecords.add(placeRecord);
                }
                mRecords.postValue(currentRecords);
                mIsUpdating.postValue(false);
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


    public LiveData<List<PlaceRecord>> getRecords(){
        return mRecords;
    }


    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}
