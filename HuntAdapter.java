package com.cs188group6.hiddengems_dsm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class HuntAdapter extends RecyclerView.Adapter<HuntAdapter.HuntViewHolder> {

    private Context context;
    private ArrayList<Hunt> hunts;
    private HuntRVClickListener mListener;

    //constructor
    public HuntAdapter(Context context, ArrayList<Hunt> dataSet, HuntRVClickListener clickListener){
        this.context=context;
        this.hunts=dataSet;
        this.mListener = clickListener;
    }

    public static class HuntViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameView;
        public TextView introView;
        public TextView pointsView;
        private HuntRVClickListener mListener;

        public HuntViewHolder(View v, HuntRVClickListener listener) {
            super(v);
            nameView = v.findViewById(R.id.name_view);
            introView = v.findViewById(R.id.intro_view);
            pointsView = v.findViewById(R.id.points_view);
            mListener = listener;
            introView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount(){
        return hunts.size();
    }

    @Override
    public HuntAdapter.HuntViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hunt_cell, parent, false);
        HuntViewHolder vh = new HuntViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(HuntViewHolder holder, int position) {
        holder.nameView.setText(hunts.get(position).getName());
        holder.introView.setText(hunts.get(position).getIntro());
        holder.pointsView.setText(hunts.get(position).getPoints());
    }


}
