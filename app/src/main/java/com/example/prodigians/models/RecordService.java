package com.example.prodigians.models;

import android.content.Intent;
import android.app.Service;
import android.media.projection.MediaProjection;
import android.media.MediaRecorder;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjectionManager;
import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.Binder;

public class RecordService extends Service {
    MediaProjection mp;
    MediaRecorder mr;
    VirtualDisplay vd;
    Intent data;
    int screenWidth;
    int screenHeight;
    int screenDensity;
    int mResultCode;

    private MediaRecorder createMR() {
        SimpleDateFormat time = new SimpleDateFormat("HH-mm-ss");
        Date d = new Date(System.currentTimeMillis());
        String curTime = time.format(d);
        //create MediaRecorder
        MediaRecorder mr = new MediaRecorder();
        mr.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mr.setOutputFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/" + curTime + ".mp4");
        mr.setVideoSize(screenWidth, screenHeight);
        mr.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mr.setVideoEncodingBitRate(screenWidth * screenHeight);
        mr.setVideoFrameRate(30);
        try {
            mr.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mr;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mResultCode = intent.getIntExtra("code", 1);
        data = intent.getParcelableExtra("data");
        screenWidth = intent.getIntExtra("width", 800);
        screenHeight = intent.getIntExtra("height", 600);
        screenDensity = intent.getIntExtra("density", 1);
        mp = ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE)).getMediaProjection(mResultCode, data);
        mr = createMR();
        vd = mp.createVirtualDisplay("recordingService", screenWidth, screenHeight, screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mr.getSurface(), null, null);
        mr.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if(vd != null) {
            vd.release();
            vd = null;
        }
        if(mr != null) {
            mr.setOnErrorListener(null);
            mp.stop();
            mr.reset();
        }
        if(mp != null) {
            mp.stop();
            mp = null;
        }
    }

    public class LocalBinder extends Binder {
        RecordService getService() {
            return RecordService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}