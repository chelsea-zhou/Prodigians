package com.example.prodigians;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.prodigians.adapters.RecyclerAdapter;
import com.example.prodigians.models.ActRecord;
import com.example.prodigians.viewmodels.MainActivityViewModel;

import java.util.List;

public class Activity_Data_copied extends AppCompatActivity {

    private static final String TAG = "Activity_Data_copied";
    private tabian.com.actionbar.SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);

                    Intent intent1 = new Intent(Activity_Data_copied.this,MainActivity.class);
                    startActivity(intent1);
                    break;
                //return true;
                case R.id.navigation_places:
                    //mTextMessage.setText(R.string.title_notifications);
                    Intent intent2 = new Intent(Activity_Data_copied.this,Place_Data.class);
                    startActivity(intent2);
                    //return true;
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_copied);

        mSectionsPageAdapter = new tabian.com.actionbar.SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.getTabAt(0).setText("Day");
        tabLayout.getTabAt(1).setText("Week");
        tabLayout.getTabAt(2).setText("Month");


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        tabian.com.actionbar.SectionsPageAdapter adapter = new tabian.com.actionbar.SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        viewPager.setAdapter(adapter);
    }



}




















