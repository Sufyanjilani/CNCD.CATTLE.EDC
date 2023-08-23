package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.UI.ActivityFarmerProfile;
import com.example.cncdcattleedcandroid.databinding.CattlelistBinding;

import java.util.ArrayList;

public class CattleAdapter extends RecyclerView.Adapter<CattleAdapter.myViewHolder> {

    ArrayList<Cattles> cattlesList;
    Context context;

    public CattleAdapter(ArrayList<Cattles> cattlesArrayList, Context context){

        cattlesList = cattlesArrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         CattlelistBinding cbinding = CattlelistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(cbinding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Cattles dataPos = cattlesList.get(position);
        holder.cattlelistBinding.cattleName.setText(dataPos.cattleName);
        holder.cattlelistBinding.milkCycle.setText(dataPos.milkCycle);
        holder.cattlelistBinding.manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, ActivityFarmerProfile.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cattlesList.size();
    }

    class myViewHolder  extends  RecyclerView.ViewHolder{


        CattlelistBinding cattlelistBinding;

        public myViewHolder(@NonNull CattlelistBinding _cattlelistBinding) {
            super(_cattlelistBinding.getRoot());
            cattlelistBinding = _cattlelistBinding;
        }
    }
}
