package com.example.prodigians;

import android.animation.ObjectAnimator;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.AnimatorListenerAdapter;
import android.view.animation.Animation;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    AnimationDrawable anima;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        ObjectAnimator animation = ObjectAnimator.ofFloat(imageView, "translationX", 500f);

        animation.setDuration(5000);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ImageView imageView = (ImageView)findViewById(R.id.image);
                imageView.setImageDrawable(anima);
                imageView.animate().rotationYBy(180f);
            }

            public void onAnimationRepeat(Animator animation) {
                ImageView imageView = (ImageView)findViewById(R.id.image);
                imageView.setImageDrawable(anima);
                imageView.animate().rotationYBy(180f);
            }
        });
        animation.start();

        anima = (AnimationDrawable) imageView.getBackground();



        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        anima.start();
    }

}
