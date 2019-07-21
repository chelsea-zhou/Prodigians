package com.example.prodigians.database;

import android.arch.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert
    void insert(ActRecordEntity actrecord);

    //not useful for now
    @Update
    void update(ActRecordEntity actrecord);

    // can also have delete, but not useful for now
    @Delete
    void delete(ActRecordEntity actrecord);

    // get other days and week's data
    // wont return livedata for some reasons
    // theoretically should work according to codelab example
    @Query("SELECT * FROM act_table")
    List<ActRecordEntity> getRecords();

}
