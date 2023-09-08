package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cncdcattleedcandroid.Models.MedicalEntityModel;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.ActivityCattleProfile;
import com.example.cncdcattleedcandroid.UI.ActivityFarmerProfile;
import com.example.cncdcattleedcandroid.UI.ActivityWebViewSurveyForm;
import com.example.cncdcattleedcandroid.databinding.CattlelistBinding;
import com.example.cncdcattleedcandroid.databinding.MedicalLayoutBinding;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.ViewHolder> {

    public MedicalAdapter(ArrayList<MedicalEntityModel> medicalEntityModels, Context context) {
        this.medicalEntityModelArrayList = medicalEntityModels;
        this.context = context;
    }

    ArrayList<MedicalEntityModel> medicalEntityModelArrayList;
    Context context;
    String entityID;
    SessionManager sessionManager;


    @NonNull
    @Override
    public MedicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicalLayoutBinding medicalLayoutBinding = MedicalLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MedicalAdapter.ViewHolder(medicalLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MedicalAdapter.ViewHolder holder, int position) {
        sessionManager = new SessionManager(context);
        MedicalEntityModel medicalEntityModel = medicalEntityModelArrayList.get(position);
        holder.medicalLayoutBinding.farmerEntityId.setText(medicalEntityModel.getFarmerEntityID());
        entityID = medicalEntityModel.getFarmerEntityID();
        holder.medicalLayoutBinding.createdAt.setText(medicalEntityModel.getCreated_at());
        holder.medicalLayoutBinding.createdBy.setText(medicalEntityModel.getCreated_by());
        holder.medicalLayoutBinding.viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDetails(sessionManager.getDashboardFarmId(), sessionManager.getDashboardFarmerId(), medicalEntityModel.getFarmerEntityID());
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
        return medicalEntityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MedicalLayoutBinding medicalLayoutBinding;

        public ViewHolder(MedicalLayoutBinding medicalLayoutBinding) {
            super(medicalLayoutBinding.getRoot());
            this.medicalLayoutBinding = medicalLayoutBinding;


        }
    }


}
