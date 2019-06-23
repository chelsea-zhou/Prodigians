package com.example.prodigians.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.prodigians.R;
import com.example.prodigians.models.ActRecord;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ActRecord> mActRecords = new ArrayList<>();
    private Context mContext;

    public RecyclerAdapter(Context context, List<ActRecord> actRecords) {
        mActRecords = actRecords;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        // Set the name of the 'ActRecord'
        ((ViewHolder)viewHolder).mName.setText(mActRecords.get(i).getActivity() );

        ((ViewHolder)viewHolder).distance.setText( String.valueOf(mActRecords.get(i).getDistance()) + " miles");

        ((ViewHolder)viewHolder).time.setText(String.valueOf(mActRecords.get(i).getTime()) + " min");


        // Set the image
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(mActRecords.get(i).getImageID())
                .into(((ViewHolder)viewHolder).mImage);

//
//        Glide.with(mContext)
//                .load(R.drawable.cycle)
//                .into(((ViewHolder)viewHolder).mImage);
    }

    @Override
    public int getItemCount() {
        return mActRecords.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mImage;
        private TextView mName;
        private TextView time;
        private TextView distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.image_name);
            time = itemView.findViewById(R.id.time);
            distance = itemView.findViewById(R.id.distance);


        }
    }
}
