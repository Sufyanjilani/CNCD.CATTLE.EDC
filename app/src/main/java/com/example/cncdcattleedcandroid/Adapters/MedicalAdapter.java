package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cncdcattleedcandroid.Models.MedicalEntityModel;
import com.example.cncdcattleedcandroid.databinding.CattlelistBinding;
import com.example.cncdcattleedcandroid.databinding.MedicalLayoutBinding;

import java.util.ArrayList;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.ViewHolder> {

    public MedicalAdapter(ArrayList<MedicalEntityModel> medicalEntityModels, Context context) {
        this.medicalEntityModelArrayList = medicalEntityModels;
        this.context = context;
    }

    ArrayList<MedicalEntityModel> medicalEntityModelArrayList;
    Context context;


    @NonNull
    @Override
    public MedicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicalLayoutBinding medicalLayoutBinding = MedicalLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MedicalAdapter.ViewHolder(medicalLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalAdapter.ViewHolder holder, int position) {
        MedicalEntityModel medicalEntityModel = medicalEntityModelArrayList.get(position);
        holder.medicalLayoutBinding.farmerEntityId.setText(medicalEntityModel.getFarmerEntityID());
//        holder.medicalLayoutBinding.questionnaireId.setText(medicalEntityModel.getQuestionnaireID());
        holder.medicalLayoutBinding.createdAt.setText(medicalEntityModel.getCreated_at());
        holder.medicalLayoutBinding.createdBy.setText(medicalEntityModel.getCreated_by());

    }

    @Override
    public int getItemCount() {
        return medicalEntityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewDesc;
        MedicalLayoutBinding medicalLayoutBinding;

        public ViewHolder(MedicalLayoutBinding medicalLayoutBinding) {
            super(medicalLayoutBinding.getRoot());
            this.medicalLayoutBinding = medicalLayoutBinding;

//            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
//            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
        }
    }
}
