package com.example.prodigians;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.prodigians.adapters.PlaceAdapter;
import com.example.prodigians.models.PlaceRecord;
import com.example.prodigians.viewmodels.PlaceActivityViewModel;

import java.util.List;
import android.util.Log;

public class Place_Data extends AppCompatActivity {

    private static final String TAG = "Place_Data";

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private ProgressBar mProgressBar;
    private PlaceActivityViewModel mPlaceActivityViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);

                    Intent intent1 = new Intent(Place_Data.this,MainActivity.class);
                    startActivity(intent1);
                    break;
                //return true;
                case R.id.navigation_activities:
                    //mTextMessage.setText(R.string.title_notifications);

                    Intent intent2 = new Intent(Place_Data.this,Activity_Data.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mPlaceActivityViewModel = ViewModelProviders.of(this).get(PlaceActivityViewModel.class);

        mPlaceActivityViewModel.init();

        mPlaceActivityViewModel.getRecords().observe(this, new Observer<List<PlaceRecord>>() {
            @Override
            public void onChanged(@Nullable List<PlaceRecord> placeRecords) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mPlaceActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    showProgressBar();
                }
                else{
                    hideProgressBar();
                    Log.v(TAG, "hide progress bar");
                    mRecyclerView.smoothScrollToPosition(mPlaceActivityViewModel.getRecords().getValue().size()-1);
                }
            }
        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPlaceActivityViewModel.addNewValue(
                        new PlaceRecord(
                                R.drawable.health,
                                "Health",
                                35
                        )
                );

            }

        });

        Log.v(TAG, "init recycler view");
        initRecyclerView();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initRecyclerView(){
        mAdapter = new PlaceAdapter(this, mPlaceActivityViewModel.getRecords().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

}
