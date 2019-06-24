package com.example.prodigians;

import android.animation.ObjectAnimator;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.FrameLayout;
import android.view.View.OnClickListener;

import android.content.Intent;

import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

import com.example.prodigians.models.ActRecord;
import com.example.prodigians.viewmodels.MAVM;

import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    ImageView imageView;
    ImageView imageView2;
    AnimationDrawable anima;
    Button changeButton;
    Button minusButton;
    Button changAButton;
    ObjectAnimator animation;
    ViewGroup.LayoutParams paramsInit;
    int defaultWidth;
    int ifBackgroundChanged = 0;
    MAVM mainViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    //return true;
                    break;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Intent intent1 = new Intent(MainActivity.this,Activity_Data.class);
                    startActivity(intent1);
                    break;

                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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
        addListenerOnButton();
        addListenerOnButton2();
        addListenerOnButton3();
        //addListenerOnButtonImage();
        imageView = (ImageView)findViewById(R.id.image);
        paramsInit = imageView.getLayoutParams();
        imageView.setBackgroundResource(R.drawable.animation);

        animation = ObjectAnimator.ofFloat(imageView, "translationX", 450f);

        animation.setDuration(5000);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        anima = (AnimationDrawable) imageView.getBackground();
        defaultWidth = imageView.getLayoutParams().width;

        imageView.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                Log.i("myapp", "image is clicked");
            }
        });

        addListenerOnAnimation();
        animation.start();

        mainViewModel = ViewModelProviders.of(this).get(MAVM.class);

        mainViewModel.init();

        mainViewModel.getRecords().observe(this, new Observer<List<ActRecord>>() {
            @Override
            public void onChanged(@Nullable List<ActRecord> actRecords) {
                Boolean fat = mainViewModel.IsFat();
                Log.i("myapp",  String.valueOf(fat));
                if (fat){
                    if(imageView.getLayoutParams().width >= defaultWidth) {
                        imageView.requestLayout();
                        imageView.getLayoutParams().width += 100;
                        Log.i("myapp","getting fat");
                    }
                }else{

                    if(imageView.getLayoutParams().width >= defaultWidth) {
                        imageView.requestLayout();
                        imageView.getLayoutParams().width -= 100;
                        Log.i("myapp", "getting slim ");
                    }
                }
            }
        });

        // Getting all extras
       // Bundle extras = getIntent().getExtras();
        Log.i("myapp", "onCreate: ");
        // Getting your int (second param is the default value if null)
        String value = getIntent().getStringExtra("message");
        if (value!=null){
            Log.i("myapp","width: " + String.valueOf(imageView.getLayoutParams().width));

            Log.i("myapp","default: " + String.valueOf(defaultWidth));

            if (value=="Fat"){
                if(imageView.getLayoutParams().width >= defaultWidth) {
                    imageView.requestLayout();
                    imageView.getLayoutParams().width += 20;
                    Log.i("myapp","getting fat");
                }

            }else{
                if(imageView.getLayoutParams().width >= defaultWidth) {
                    imageView.requestLayout();
                    imageView.getLayoutParams().width -= 20;
                    Log.i("myapp", "getting slim ");
                }

            }

        }else{
            Log.i("myapp", "value is null ");
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void addListenerOnButton() {
        changeButton = (Button) findViewById(R.id.button);
        changeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView)findViewById(R.id.image);
                if(imageView.getLayoutParams().width < defaultWidth+400) {
                    imageView.requestLayout();
                    imageView.getLayoutParams().width += 20;
                }
            }
        });
    }

    public void addListenerOnButton2() {
        minusButton = (Button) findViewById(R.id.button2);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView)findViewById(R.id.image);
                if(imageView.getLayoutParams().width > defaultWidth) {
                    imageView.requestLayout();
                    imageView.getLayoutParams().width -= 20;
                }
            }
        });
    }

    public void addListenerOnButton3() {
        changAButton = (Button) findViewById(R.id.button3);
        changAButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //imageView.setImageResource(R.drawable.animation2);
//                animation = ObjectAnimator.ofFloat(imageView, "translationX", 450f);
//                animation.setDuration(5000);
//                animation.setRepeatMode(ValueAnimator.REVERSE);
//                animation.setRepeatCount(Animation.INFINITE);
//                anima = (AnimationDrawable) imageView.getBackground();
//                animation.start();
                //animation.end();
                anima.stop();
                animation.cancel();
                imageView.setImageDrawable(null);
                imageView2 = (ImageView)findViewById(R.id.image);
                imageView2.setLayoutParams(paramsInit);
                imageView2.requestLayout();

                imageView2.setBackgroundResource(R.drawable.animation2);
                animation = ObjectAnimator.ofFloat(imageView2, "translationX", 450f);

                animation.setDuration(5000);
                animation.setRepeatMode(ValueAnimator.REVERSE);
                animation.setRepeatCount(Animation.INFINITE);
                addListenerOnAnimation();
                anima = (AnimationDrawable) imageView2.getBackground();
                imageView2.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addListenerOnAnimation() {
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //imageView.setImageDrawable(anima);
                ImageView IV = (ImageView)findViewById(R.id.image);
                IV.animate().rotationYBy(180f);
            }

            public void onAnimationRepeat(Animator animation) {
                ImageView IV = (ImageView)findViewById(R.id.image);
                IV.animate().rotationYBy(180f);
            }

            public void onAnimationEnd(Animator animation) {
                ImageView IV = (ImageView)findViewById(R.id.image);
                IV.setVisibility(View.INVISIBLE);
            }

        });
    }


//    public void addListenerOnButtonImage() {
//        minusButton = (Button) findViewById(R.id.button3);
//        minusButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ConstraintLayout layout =(ConstraintLayout)findViewById(R.id.container);
//                if(ifBackgroundChanged == 0) {
//                    layout.setBackgroundResource(R.mipmap.restaurant);
//                    ifBackgroundChanged = 1;
//                }
//                else {
//                    layout.setBackgroundResource(R.mipmap.library);
//                    ifBackgroundChanged = 0;
//                }
//            }
//        });
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        anima.start();
    }

}
