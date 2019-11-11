package com.cs188group6.hiddengems_dsm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    private Context context;
    private RealmResults<Gem> gems;
    private ProgressRVClickListener mListener;
    private User user;

    public ProgressAdapter(Context context, RealmResults<Gem> dataSet, ProgressRVClickListener listener){
        this.context = context;
        this.gems= dataSet;
        this.mListener = listener;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView gemName;
        public ImageView darkCircleImage;
        public ImageView lightCircleImage;
        public ImageView topLine;
        public ImageView bottomLine;
        private ProgressRVClickListener mListener;


        public ProgressViewHolder(View v, ProgressRVClickListener listener){
            super (v);
            gemName=v.findViewById(R.id.gem_view);
            darkCircleImage = v.findViewById(R.id.dark_circle_image);
            lightCircleImage = v.findViewById(R.id.light_circle_image);
            topLine = v.findViewById(R.id.top_line);
            bottomLine = v.findViewById(R.id.bottom_line);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount(){
        return gems.size();
    }

    @Override
    public ProgressAdapter.ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_cell, parent, false);
        ProgressViewHolder vh = new ProgressViewHolder(v, mListener);
        return vh;
    }


    @Override
    public void onBindViewHolder(ProgressViewHolder holder, int position) {
        holder.gemName.setText(gems.get(position).getGemName());
        // If checked in circle is dark blue
        for (int i=0; i < gems.size(); i++ ){
            if (user != null) {
                if (user.getCollectedGem().getGemName().equals(gems.get(i).getGemName())) {
                    holder.lightCircleImage.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
