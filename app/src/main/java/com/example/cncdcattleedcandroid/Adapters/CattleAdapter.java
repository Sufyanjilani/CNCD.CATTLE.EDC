package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.ActivityFarmerProfile;
import com.example.cncdcattleedcandroid.UI.FarmerAdapter;
import com.example.cncdcattleedcandroid.databinding.CattlelistBinding;

import java.util.ArrayList;

public class CattleAdapter extends RecyclerView.Adapter<CattleAdapter.ViewHolder> {

    ArrayList<Cattles> cattlesList;
    Context context;
    public CattleAdapter(ArrayList<Cattles> cattlesArrayList, Context context){

        cattlesList = cattlesArrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CattlelistBinding cattlelistBinding = CattlelistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CattleAdapter.ViewHolder(cattlelistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CattleAdapter.ViewHolder holder, int position) {
        Cattles cattles = cattlesList.get(position);
        holder.cattlelistBinding.farmName.setText(cattles.getFarmName());
        holder.cattlelistBinding.farmAddress.setText(cattles.getFarmAddress());
        holder.cattlelistBinding.farmerName.setText(cattles.getFarmerName());
        holder.cattlelistBinding.createdAt.setText(cattles.getCreated_at());
        holder.cattlelistBinding.manage.setOnClickListener(view -> ViewDetails(cattles.getFarmID(), cattles.getFarmerID()));
    }

    public void ViewDetails(String farmId, String farmerId){
        Intent intent = new Intent(context,ActivityFarmerProfile.class);
        intent.putExtra("farmId",farmId);
        intent.putExtra("farmerId", farmerId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return cattlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CattlelistBinding cattlelistBinding;

        public ViewHolder(CattlelistBinding cattlelistBinding) {
            super(cattlelistBinding.getRoot());
            this.cattlelistBinding = cattlelistBinding;

        }
    }

}
