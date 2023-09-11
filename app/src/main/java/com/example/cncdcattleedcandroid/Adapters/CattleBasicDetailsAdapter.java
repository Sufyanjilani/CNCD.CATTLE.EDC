package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cncdcattleedcandroid.Models.CattleBasicDetailsModel;
import com.example.cncdcattleedcandroid.databinding.ActivityBasicEntityRecyclerBinding;
import com.example.cncdcattleedcandroid.databinding.CattlelistBinding;

import java.util.ArrayList;

public class CattleBasicDetailsAdapter extends RecyclerView.Adapter<CattleBasicDetailsAdapter.ViewHolder>{

    ArrayList<CattleBasicDetailsModel> cattleBasicDetailsModelArrayList;
    Context context;

    public CattleBasicDetailsAdapter(ArrayList<CattleBasicDetailsModel> cattleBasicDetailsModelArrayList, Context context) {
        this.cattleBasicDetailsModelArrayList = cattleBasicDetailsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CattleBasicDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityBasicEntityRecyclerBinding activityBasicEntityRecyclerBinding = ActivityBasicEntityRecyclerBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CattleBasicDetailsAdapter.ViewHolder(activityBasicEntityRecyclerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CattleBasicDetailsAdapter.ViewHolder holder, int position) {
        CattleBasicDetailsModel cattleBasicDetailsModel = cattleBasicDetailsModelArrayList.get(position);
        holder.activityBasicEntityRecyclerBinding.breedRecently.setText(cattleBasicDetailsModel.getBreedRecently());
        holder.activityBasicEntityRecyclerBinding.cattleFeedPerDay.setText(cattleBasicDetailsModel.getCattle_feed_per_day());
        holder.activityBasicEntityRecyclerBinding.greenFeedKg.setText(cattleBasicDetailsModel.getGreenFeed_kg());
        holder.activityBasicEntityRecyclerBinding.hasGrazingArea.setText(cattleBasicDetailsModel.getHasGrazing_area());
        holder.activityBasicEntityRecyclerBinding.hoursOfGrazing.setText(cattleBasicDetailsModel.getHoursOf_grazing());
        holder.activityBasicEntityRecyclerBinding.matedRecently.setText(cattleBasicDetailsModel.getMatedRecently());
        holder.activityBasicEntityRecyclerBinding.teethOfCattle.setText(cattleBasicDetailsModel.getTeeth_of_cattle());
        holder.activityBasicEntityRecyclerBinding.wandaFeedKg.setText(cattleBasicDetailsModel.getWandaFeed_kg());

    }

    @Override
    public int getItemCount() {
        return cattleBasicDetailsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ActivityBasicEntityRecyclerBinding activityBasicEntityRecyclerBinding;

        public ViewHolder(ActivityBasicEntityRecyclerBinding activityBasicEntityRecyclerBinding) {
            super(activityBasicEntityRecyclerBinding.getRoot());
            this.activityBasicEntityRecyclerBinding = activityBasicEntityRecyclerBinding;


        }
    }
}
