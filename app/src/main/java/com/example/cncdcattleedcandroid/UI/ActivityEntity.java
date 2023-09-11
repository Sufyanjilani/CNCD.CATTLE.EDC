package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cncdcattleedcandroid.Adapters.DietAdapter;
import com.example.cncdcattleedcandroid.Adapters.MedicalAdapter;
import com.example.cncdcattleedcandroid.Models.DataGridModel;
import com.example.cncdcattleedcandroid.Models.DietModel;
import com.example.cncdcattleedcandroid.Models.MedicalEntityModel;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.Utils.InternetUtils;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.databinding.ActivityEntityBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEntity extends AppCompatActivity {

    ActivityEntityBinding activityEntityBinding;
    MedicalAdapter medicalAdapter;
    DietAdapter dietAdapter;
    LoadingDialog loadingDialog;

    Constants constants;
    SessionManager sessionManager;
    String farmId, farmerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntityBinding = ActivityEntityBinding.inflate(getLayoutInflater());
        setContentView(activityEntityBinding.getRoot());
        constants = new Constants();
        sessionManager = new SessionManager(this);
        loadingDialog = new LoadingDialog(this,this);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        farmId = extra.getString("farmID");
        farmerId = extra.getString("farmerID");


        activityEntityBinding.name.setText(sessionManager.getFarmerName());
        activityEntityBinding.farmName.setText(sessionManager.getFarmName());
        activityEntityBinding.farmAddress.setText(sessionManager.getFarmAddress()+", "+sessionManager.getFarmSector());
//        activityEntityBinding.farmerMobile.setText(sessionManager.getMobileNumber()+" / "+sessionManager.getAltNumber());
        if (sessionManager.getAltNumber().equals(null)){
            activityEntityBinding.farmerMobile.setText(sessionManager.getMobileNumber()+" / "+sessionManager.getAltNumber());
        }else {
            activityEntityBinding.farmerMobile.setText(sessionManager.getMobileNumber());
        }
        activityEntityBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                sessionManager.saveDashboardFarmFarmerId("", "");
            }
        });

        activityEntityBinding.locationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sessionManager.getGoogleLocation()));
                startActivity(intent);
            }
        });
        activityEntityBinding.btnAddMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityEntity.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","general_medical");
                i.putExtra("farmID",farmId);
                i.putExtra("farmerID",farmerId);
                startActivity(i);
            }
        });

        activityEntityBinding.btnAddDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityEntity.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","general_diet");
                i.putExtra("farmID",farmId);
                i.putExtra("farmerID",farmerId);
                startActivity(i);
            }
        });
       LoadData();
    }

    public void getFarmerEntity(){
        ArrayList<MedicalEntityModel> medicalEntityModelArrayList = new ArrayList<>();
        ArrayList<DietModel> dietModelArrayList = new ArrayList<>();
        Call<JsonObject> apiGet = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getFarmerEntity(farmId,farmerId);
        apiGet.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if (!response.body().get("error").getAsString().equals("true")){
                        JsonObject farmerData = response.body();
                        JsonObject dataObject = farmerData.get("data").getAsJsonObject();
                        JsonObject entitiesData = dataObject.get("entitiesData").getAsJsonObject();
                        JsonArray general_medical = entitiesData.get("general_medical").getAsJsonArray();
                        for (int i = 0; i < general_medical.size(); i++){
                            JsonObject entityData = general_medical.get(i).getAsJsonObject();
                            String farmerEntityID = entityData.get("farmerEntityID").getAsString();
                            String questionnaireID = entityData.get("questionnaireID").getAsString();
                            String created_at = entityData.get("created_at").getAsString();
                            String created_by = entityData.get("created_by").getAsString();

                            MedicalEntityModel medicalEntityModel = new MedicalEntityModel(farmerEntityID, questionnaireID, created_at, created_by);
                            medicalEntityModelArrayList.add(medicalEntityModel);
                        }

                        medicalAdapter = new MedicalAdapter(medicalEntityModelArrayList, ActivityEntity.this);
                        activityEntityBinding.medicalRecyler.setLayoutManager(new LinearLayoutManager(ActivityEntity.this));
                        activityEntityBinding.medicalRecyler.setAdapter(medicalAdapter);


                        JsonArray general_diet = entitiesData.get("general_diet").getAsJsonArray();
                        for (int i = 0; i < general_diet.size(); i++){
                            JsonObject dietObject = general_diet.get(i).getAsJsonObject();
                            String farmerEntityID = dietObject.get("farmerEntityID").getAsString();
                            String questionnaireID = dietObject.get("questionnaireID").getAsString();
                            String created_at = dietObject.get("created_at").getAsString();
                            String created_by = dietObject.get("created_by").getAsString();

                            DietModel dietModel = new DietModel(farmerEntityID, questionnaireID, created_at, created_by);
                            dietModelArrayList.add(dietModel);
                        }

                        dietAdapter = new DietAdapter(dietModelArrayList, ActivityEntity.this);
                        activityEntityBinding.dietRecyler.setLayoutManager(new LinearLayoutManager(ActivityEntity.this));
                        activityEntityBinding.dietRecyler.setAdapter(dietAdapter);
                    }else{
                        String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                        Toast.makeText(ActivityEntity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                    loadingDialog.dissmissDialog();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(constants.Tag, t.getMessage().toString());
            }
        });
    }


    public void loadEntityData(){
        Call<JsonObject> getData = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getEntityData("12","5","66");
        getData.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (!response.body().get("error").getAsString().equals("true")){
                        JsonObject responseObject = response.body();
                        JsonObject dataObject = responseObject.get("data").getAsJsonObject();
                        JsonObject formJson = dataObject.get("formJSON").getAsJsonObject();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    public void LoadData(){

        loadingDialog.ShowCustomLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (InternetUtils.isInternetConnected(getApplicationContext())) {
                    // Internet is available
                    // Do your internet-related tasks here
                    getFarmerEntity();

                } else {
                    loadingDialog.dissmissDialog();
                    // No internet connection
                    // Display a message or handle the lack of internet connection
                    androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(ActivityEntity.this);
                    dialog.setTitle("No internet Connection present at the moment");
                    dialog.setMessage("Do you wish to go to internet settings?");
                    dialog.setIcon(R.drawable.cowimage);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            LoadData();
                        }
                    });

                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finishAffinity();
                        }
                    });

                    dialog.show();
                    loadingDialog.dissmissDialog();
                }



            }
        },200);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.saveDashboardFarmFarmerId("", "");
    }
}