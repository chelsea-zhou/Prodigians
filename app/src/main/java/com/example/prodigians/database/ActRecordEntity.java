package com.example.prodigians.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName="act_table")
public class ActRecordEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int imageid;

    private String activity_type;

    private long start_time;

    private long end_time;

    private int duration;

    @ColumnInfo(name = "date")
    private String date;

    public ActRecordEntity(int imageid,String activity_type, long start_time, long end_time, int duration, String date) {
        this.imageid = imageid;
        this.activity_type = activity_type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.duration = duration;
        this.date = date;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
