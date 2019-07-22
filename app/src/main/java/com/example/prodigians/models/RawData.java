package com.example.prodigians.models;

public class RawData {
    public int act_type;
    public int trans_type;
    public long current_time;

    public RawData(int act_type, int trans_type, long current_time) {
        this.act_type = act_type;
        this.trans_type = trans_type;
        this.current_time = current_time;
    }

    public int getAct_type() {
        return act_type;
    }

    public int getTrans_type() {
        return trans_type;
    }

    public long getCurrent_time() {
        return current_time;
    }
}
