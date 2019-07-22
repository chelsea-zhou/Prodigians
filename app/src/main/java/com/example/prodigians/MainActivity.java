package com.example.prodigians;

import android.animation.ObjectAnimator;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.Animation;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.media.projection.MediaProjectionManager;
import android.util.DisplayMetrics;
import android.content.Context;
import android.net.Uri;

import android.content.Intent;

import com.example.prodigians.models.ActRecord;
import com.example.prodigians.viewmodels.MAVM;
import com.example.prodigians.models.RecordService;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;


import java.security.MessageDigest;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    CallbackManager cm;
    ShareDialog sd;
    ImageView imageView;
    ImageView burgerImage;
    AnimationDrawable anima;
    Button RecordButton;
    Button ShareButton;
    ObjectAnimator animation;
    ViewGroup.LayoutParams paramsInit;
    int defaultWidth = 750;
    int maxWidth = 1150;
    int ifSmile = 1;
    int ifAnimating = 1;
    int screenWidth;
    int screenHeight;
    int screenDensity;
    MAVM mainViewModel;
    static int myInt = 750;
    static int data_len=1;
    //if video recording starts
    boolean ifStarted = false;
    String direction = "left";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    break;
                case R.id.navigation_activities:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent intent1 = new Intent(MainActivity.this,Activity_Data.class);
                    startActivity(intent1);
                    break;

                case R.id.navigation_places:
                    //mTextMessage.setText(R.string.title_notifications);
                    Intent intent2 = new Intent(MainActivity.this,Place_Data.class);
                    startActivity(intent2);
                    //return true;
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnShareButton();
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        //init facebook
        cm = CallbackManager.Factory.create();
        sd = new ShareDialog(this);


        imageView = (ImageView)findViewById(R.id.image);
//      burgerImage = (ImageView) findViewById(R.id.imageBurger);
//      burgerImage.setImageResource(R.mipmap.burger2);
        paramsInit = imageView.getLayoutParams();
        imageView.setBackgroundResource(R.drawable.animation);

        animation = ObjectAnimator.ofFloat(imageView, "translationX", 450f);

        animation.setDuration(5000);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        anima = (AnimationDrawable) imageView.getBackground();
        defaultWidth = imageView.getLayoutParams().width;

        addListenerOnImageView();

        addListenerOnAnimation();
        animation.start();

        mainViewModel = ViewModelProviders.of(this).get(MAVM.class);

        mainViewModel.init();

        //imageView.getLayoutParams().width = mainViewModel.getWidth();
        Log.i("myapp",  String.valueOf(myInt));
        imageView.getLayoutParams().width = myInt;

        if(myInt >= 950){
            anima.stop();
            anima.setVisible(false, true);
            animation.cancel();
            imageView = (ImageView) findViewById(R.id.image);
            paramsInit = imageView.getLayoutParams();
            imageView.setBackgroundResource(R.drawable.animation2);
            imageView.setVisibility(View.VISIBLE);
            anima = (AnimationDrawable) imageView.getBackground();
            anima.start();
            animation.start();
        }
        else {
            anima.stop();
            anima.setVisible(false, true);
            animation.cancel();
            imageView = (ImageView) findViewById(R.id.image);
            paramsInit = imageView.getLayoutParams();
            imageView.setBackgroundResource(R.drawable.animation);
            imageView.setVisibility(View.VISIBLE);
            anima = (AnimationDrawable) imageView.getBackground();
            anima.start();
            animation.start();
        }

        mainViewModel.getRecords().observe(this, new Observer<List<ActRecord>>() {
            @Override
            public void onChanged(@Nullable List<ActRecord> actRecords) {
                if (data_len != actRecords.size()) {
                    data_len = actRecords.size();
                    Boolean fat = mainViewModel.IsFat();
                    Log.i("myapp", String.valueOf(fat));
                    if (fat) {
                        if (myInt <= maxWidth) {
                            myInt += 100;
                        }
                    } else {

                    if(myInt >= defaultWidth) {
                        imageView.requestLayout();
                        imageView.getLayoutParams().width -= 100;
                        myInt -= 100;
                    }
                    changeSmile();
                }
            }
        }});

        String value = getIntent().getStringExtra("message");
        if (value!=null){

            if (value=="Fat"){
                if(imageView.getLayoutParams().width >= defaultWidth) {
                    imageView.requestLayout();
                    imageView.getLayoutParams().width += 20;
                }

            }else{
                if(imageView.getLayoutParams().width >= defaultWidth) {
                    imageView.requestLayout();
                    imageView.getLayoutParams().width -= 20;
                }
            }

        }else{
            Log.i("myapp", "value is null ");
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //get screen information
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenDensity = dm.densityDpi;
        capture();
    }

    private void capture() {
        RecordButton = (Button) findViewById(R.id.buttonVideo);
        if(ifStarted) {
            //change recording button text when recording starts
            RecordButton.setText("Stop");
            RecordButton.setBackgroundColor(Color.RED);
        } else {
            //change recording button text when recording ends
            RecordButton.setText("Record");
            RecordButton.setBackgroundColor(Color.WHITE);
        }
        RecordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifStarted) {
                    RecordButton.setText("Record");
                    RecordButton.setBackgroundColor(Color.WHITE);
                    stopRecording();
                } else {
                    MediaProjectionManager mpm = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                    Intent I = mpm.createScreenCaptureIntent();
                    startActivityForResult(I, 1);
                }
            }
        });
    }

    private void stopRecording() {
        Intent I = new Intent(this, RecordService.class);
        stopService(I);
        ifStarted = !ifStarted;
    }

    private void changeSmile(){
        if(myInt == 950 && ifSmile == 1){
            anima.stop();
            anima.setVisible(false, true);
            animation.cancel();
            imageView = (ImageView) findViewById(R.id.image);
            paramsInit = imageView.getLayoutParams();
            imageView.setBackgroundResource(R.drawable.animation2);
            imageView.setVisibility(View.VISIBLE);
            anima = (AnimationDrawable) imageView.getBackground();
            anima.start();
            animation.start();
            ifSmile = 0;
        }
        else if(myInt == 950 && ifSmile == 0){
            anima.stop();
            anima.setVisible(false, true);
            animation.cancel();
            imageView = (ImageView) findViewById(R.id.image);
            paramsInit = imageView.getLayoutParams();
            imageView.setBackgroundResource(R.drawable.animation);
            imageView.setVisibility(View.VISIBLE);
            anima = (AnimationDrawable) imageView.getBackground();
            anima.start();
            animation.start();

            ifSmile = 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            // get permissions and start service to start recording
            Intent i = new Intent(this, RecordService.class);
            i.putExtra("code", resultCode);
            i.putExtra("data", data);
            i.putExtra("width", screenWidth);
            i.putExtra("height", screenHeight);
            i.putExtra("density", screenDensity);
            //start recording
            startService(i);
            ifStarted = !ifStarted;
            RecordButton.setText("Stop");
            RecordButton.setBackgroundColor(Color.RED);
        }
        else if(requestCode == 1000){
            Uri selectVideo = data.getData();
            ShareVideo video = new ShareVideo.Builder().setLocalUrl(selectVideo).build();
            ShareVideoContent svc = new ShareVideoContent.Builder().setContentTitle("pet").setContentDescription("pet video").setVideo(video).build();
            if(sd.canShow(ShareVideoContent.class)){
                sd.show(svc);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("record status", ifStarted);
    }

    private void addListenerOnImageView() {
        imageView.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                if(ifAnimating == 1) {
                    anima.stop();
                    anima.setVisible(false, true);
                    animation.cancel();
                    imageView = (ImageView) findViewById(R.id.image);
                    paramsInit = imageView.getLayoutParams();
                    imageView.setBackgroundResource(R.drawable.animation2);
                    imageView.setVisibility(View.VISIBLE);
                    anima = (AnimationDrawable) imageView.getBackground();
                    anima.start();
                    animation.start();

                    ifAnimating = 0;
                }
                else{
                    anima.stop();
                    anima.setVisible(false, true);
                    animation.cancel();
                    imageView = (ImageView) findViewById(R.id.image);
                    paramsInit = imageView.getLayoutParams();
                    imageView.setBackgroundResource(R.drawable.animation);
                    imageView.setVisibility(View.VISIBLE);
                    anima = (AnimationDrawable) imageView.getBackground();
                    anima.start();
                    animation.start();

                    ifAnimating = 1;
                }
            }
        });
    }

    private void addListenerOnShareButton() {
        ShareButton = (Button) findViewById(R.id.buttonShare);
        ShareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("video/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "select"), 1000);
            }
        });
    }

    public void addListenerOnAnimation() {
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //imageView.setImageDrawable(anima);
                ImageView IV = (ImageView)findViewById(R.id.image);
                IV.setVisibility(View.VISIBLE);
                if(direction == "left") {
                    IV.animate().rotationYBy(180f);
                    direction = "right";
                }
            }

            public void onAnimationRepeat(Animator animation) {
                ImageView IV = (ImageView)findViewById(R.id.image);
                IV.animate().rotationYBy(180f);
                if(direction == "left") {
                    direction = "right";
                }
                else{
                    direction = "left";
                }
            }

            public void onAnimationEnd(Animator animation) {
                ImageView IV = (ImageView)findViewById(R.id.image);
                IV.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        anima.start();
    }
}
