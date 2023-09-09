package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cncdcattleedcandroid.Models.DietModel;
import com.example.cncdcattleedcandroid.Models.MedicalEntityModel;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.ActivityWebViewSurveyForm;
import com.example.cncdcattleedcandroid.databinding.DietLayoutBinding;

import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.ViewHolder>{

    public DietAdapter(ArrayList<DietModel> dietModelArrayList, Context context) {
        this.dietModelArrayList = dietModelArrayList;
        this.context = context;
    }

    ArrayList<DietModel> dietModelArrayList;
    Context context;
    SessionManager sessionManager;
    @NonNull
    @Override
    public DietAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DietLayoutBinding dietLayoutBinding = DietLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DietAdapter.ViewHolder(dietLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DietAdapter.ViewHolder holder, int position) {
        sessionManager = new SessionManager(context);
        DietModel dietModel = dietModelArrayList.get(position);
        holder.dietLayoutBinding.farmerEntityId.setText(dietModel.getFarmerEntityID());
//        holder.dietLayoutBinding.questionnaireId.setText(dietModel.getQuestionnaireID());
        holder.dietLayoutBinding.createdAt.setText(dietModel.getCreated_at());
        holder.dietLayoutBinding.createdBy.setText(dietModel.getCreated_by());
        holder.dietLayoutBinding.viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDetails(sessionManager.getDashboardFarmId(), sessionManager.getDashboardFarmerId(), dietModel.getFarmerEntityID());
            }
        });
    }

    public void ViewDetails(String farmId, String farmerId, String entityId){
        Intent i = new Intent(context, ActivityWebViewSurveyForm.class);
        i.putExtra("formID","View_general_diet");
        i.putExtra("farmID",farmId);
        i.putExtra("farmerID",farmerId);
        i.putExtra("entityID",entityId);
        i.putExtra("mode","readOnly");
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return dietModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        DietLayoutBinding dietLayoutBinding;

        public ViewHolder(DietLayoutBinding dietLayoutBinding) {
            super(dietLayoutBinding.getRoot());
            this.dietLayoutBinding = dietLayoutBinding;

        }
    }
}
