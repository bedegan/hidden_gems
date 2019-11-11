package com.cs188group6.hiddengems_dsm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

import io.realm.RealmResults;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private Context context;
    private RealmResults<Gem> gems;
    private RecyclerViewClickListener mListener;
    private String neighborhood;

    public MainAdapter(Context context,
                       RealmResults<Gem> dataSet,
                       RecyclerViewClickListener clickListener) {
        this.context = context;
        this.gems = dataSet;
        this.mListener = clickListener;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView gemNameView, gemPointView, gemAddressView, gemTagsView;
        private RecyclerViewClickListener mListener;
        public MainViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            gemNameView = v.findViewById(R.id.gem_name_view);
            gemPointView = v.findViewById(R.id.gem_list_count);
            gemAddressView = v.findViewById(R.id.address_list_view);
            gemTagsView = v.findViewById(R.id.list_view_tags);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }


    @Override
    public int getItemCount() {
        return gems.size();
    }

    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cell, parent, false);
        MainViewHolder vh = new MainViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Gem currentGem = (Gem) gems.get(position);
        holder.gemNameView.setText(currentGem.getGemName());
        holder.gemAddressView.setText(currentGem.getAddress());
        holder.gemPointView.setText(currentGem.getPoints());
        holder.gemTagsView.setText(currentGem.getTags());
    }
}
