package com.example.prodigians.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.prodigians.models.ActRecord;
import com.example.prodigians.repositories.ActRecordRepository;

import java.util.List;
import java.util.*;

public class MAVM extends ViewModel {

    private MutableLiveData<List<ActRecord>> mRecords;
    private ActRecordRepository mRepo;
    static int avg_move_min = 30;

    // only walk, run, cycle counts, later can be more fine grained, like measure the speed of wheel to get heart points
    static int avg_hrt_point= 10;

    static int run_factor = 2;
    static double cycle_factor = 1.5;
    static int walk_factor =1;

    public void init(){
        if(mRecords != null){
            return;
        }
        mRepo = ActRecordRepository.getInstance();
        mRecords = mRepo.getRecords();
    }

    public LiveData<List<ActRecord>> getRecords(){
        return mRecords;
    }

    private int[] cal_running_avg(int days) {
        List<ActRecord> currentRec = mRecords.getValue();
        int len = currentRec.size();
        if (len >= days) {
            List<ActRecord> last5 = currentRec.subList(len - days, len - 1);
            ListIterator<ActRecord> iterator = last5.listIterator();

            int total_time =0;
            int total_hrt_pt =0;
            while (iterator.hasNext()) {
                ActRecord ar = iterator.next();

                String act = ar.getActivity();
                double dist = ar.getDistance();

                if (act=="WALKING"){
                    total_hrt_pt += dist * 10;
                }else if(act=="RUNNING"){
                    total_hrt_pt += dist * 20;
                }else if(act=="CYCLING") {
                    total_hrt_pt += (int) dist * 15;
                }
                total_time += ar.getTime();
            }

            return new int[] {total_time/days, total_hrt_pt/days};

        }
        return new int[] {avg_move_min,avg_hrt_point};
    }

    // if the recent 10 is below average/or smaller than previous, then add weight
    // if smaller than average, but better than previous, then fat
    // if smaller than average, and worse than previous fat
    // equal then no change
    public boolean IsFat(){
        int [] recent = cal_running_avg(2);
        int recent_move_min = recent[0];
        int recent_hrt_pts = recent[1];
        if (recent_move_min >=avg_move_min+10){
            return false;
        }
        return true;


    }

}
