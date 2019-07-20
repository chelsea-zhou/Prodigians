package com.example.prodigians.adapters;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.prodigians.R;
import com.example.prodigians.models.ActRecord;
import com.example.prodigians.models.PlaceRecord;
import com.example.prodigians.viewmodels.PlaceActivityViewModel;

import android.view.View.OnClickListener;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlaceAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlaceRecord> mPlaceRecords = new ArrayList<>();
    private Context mContext;

    public PlaceAdapter(Context context, List<PlaceRecord> placeRecords) {
        mPlaceRecords = placeRecords;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem2, viewGroup, false);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.popup_window, null);

//                RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//
//                MutableLiveData<List<ActRecord>> data = new MutableLiveData<>();
//                ArrayList<ActRecord> dataSet = new ArrayList<>();
//                dataSet.add(
//                        new ActRecord(R.drawable.cycle,
//                                "Cycling",
//                                12,
//                                13));
//
//                data.setValue(dataSet);
//                RecyclerAdapter mAdapter;
//                mAdapter = new RecyclerAdapter(mContext,dataSet);
//                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//                mRecyclerView.setLayoutManager(linearLayoutManager);
//                mRecyclerView.setAdapter(mAdapter);




                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                TextView popupText = popupView.findViewById(R.id.popupWind);
                //popupText.setText(String.valueOf(mPlaceRecords.get(0).getPlace()));
                popupText.setText("DC     50 min\nMC     25 min");
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        // Set the name of the 'ActRecord'
        ((ViewHolder)viewHolder).mName.setText(mPlaceRecords.get(i).getPlace() );

        if (mPlaceRecords.get(i).getTime() >60){
            String fulltime;
            fulltime = String.valueOf(mPlaceRecords.get(i).getTime()/60) + " h ";
            if (mPlaceRecords.get(i).getTime()%60>0){
                fulltime = fulltime + String.valueOf(mPlaceRecords.get(i).getTime()%60) + " min";
            }
            ((ViewHolder) viewHolder).time.setText(fulltime);
        }else {
            ((ViewHolder) viewHolder).time.setText(String.valueOf(mPlaceRecords.get(i).getTime()) + " min");
        }
        // Set the image
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(mPlaceRecords.get(i).getImageID())
                .into(((ViewHolder)viewHolder).mImage);

//
//        Glide.with(mContext)
//                .load(R.drawable.cycle)
//                .into(((ViewHolder)viewHolder).mImage);
    }

    @Override
    public int getItemCount() {
        return mPlaceRecords.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mImage;
        private TextView mName;
        private TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.image_name);
            time = itemView.findViewById(R.id.time);


        }
    }
}
