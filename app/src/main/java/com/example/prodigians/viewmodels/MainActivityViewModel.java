package com.example.prodigians.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.prodigians.models.ActRecord;
import com.example.prodigians.repositories.ActRecordRepository;

import java.util.List;
import java.util.*;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<ActRecord>> mRecords;
    private ActRecordRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();



    public void init(){
        if(mRecords != null){
            return;
        }
        mRepo = ActRecordRepository.getInstance();
        mRecords = mRepo.getActivityRecords();
    }


    public void addNewValue(final ActRecord actRecord){
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<ActRecord> currentRecords = mRecords.getValue();
                currentRecords.add(actRecord);
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


    public LiveData<List<ActRecord>> getRecords(){
        return mRecords;
    }


    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}
