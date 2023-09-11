package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cncdcattleedcandroid.Adapters.CattleBasicDetailsAdapter;
import com.example.cncdcattleedcandroid.Models.CattleBasicDetailsModel;
import com.example.cncdcattleedcandroid.Network.BaseUrl;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.databinding.ActivityCattleProfileBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCattleProfile extends AppCompatActivity {

    ActivityCattleProfileBinding cattleProfileBinding;
    SessionManager sessionManager;
    String farmId, farmerId, cattleId, mainImageUrl, frontPoseUrl, sidePoseUrl;
    LoadingDialog loadingDialog;
    CattleBasicDetailsAdapter cattleBasicDetailsAdapter;
    Constants constants;
    String Url = "http://192.168.20.136:8888/public/images/rearattachment_1.png";

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCattleProfileData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cattleProfileBinding = ActivityCattleProfileBinding.inflate(getLayoutInflater());
        setContentView(cattleProfileBinding.getRoot());
        sessionManager = new SessionManager(this);
        loadingDialog = new LoadingDialog(this,this);
        constants = new Constants();
        loadingDialog.ShowCustomLoadingDialog();
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        cattleId = extra.getString("cattleID");
        Log.d("id", cattleId);

        cattleProfileBinding.name.setText(sessionManager.getFarmerName());
        cattleProfileBinding.farmName.setText(sessionManager.getFarmName());
        cattleProfileBinding.farmAddress.setText(sessionManager.getFarmAddress()+", "+sessionManager.getFarmSector());
//        cattleProfileBinding.farmerMobile.setText(sessionManager.getMobileNumber()+" / "+sessionManager.getAltNumber());
        if (sessionManager.getAltNumber().equals(null)){
            cattleProfileBinding.farmerMobile.setText(sessionManager.getMobileNumber()+" / "+sessionManager.getAltNumber());
        }else {
            cattleProfileBinding.farmerMobile.setText(sessionManager.getMobileNumber());
        }
//        cattleProfileBinding.cattleType.setText(sessionManager.getcattleType());
//        cattleProfileBinding.cattleGender.setText(sessionManager.getcattleGender());
//        cattleProfileBinding.cattleBreed.setText(sessionManager.getcattleBreed());
//        cattleProfileBinding.cattleSampleId.setText(sessionManager.getcattleSampleId());

        cattleProfileBinding.viewCattleBasicData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","view_personal_basic");
                i.putExtra("cattleId",cattleId);
                i.putExtra("mode","readOnly");
                startActivity(i);
            }
        });

        cattleProfileBinding.viewCattleMilkingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","view_personal_milk");
                i.putExtra("farmID",sessionManager.getDashboardFarmId());
                i.putExtra("farmerID",sessionManager.getDashboardFarmerId());
                i.putExtra("cattleId",cattleId);
                i.putExtra("mode","readOnly");
                Log.d("cattleId",cattleId);
                startActivity(i);
            }
        });
        cattleProfileBinding.locationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sessionManager.getGoogleLocation()));
                startActivity(intent);
            }
        });
        cattleProfileBinding.viewCattleMedicalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","view_personal_medical");
                i.putExtra("cattleId",cattleId);
                i.putExtra("mode","readOnly");
                startActivity(i);
            }
        });

        cattleProfileBinding.viewCattleTraitsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","view_personal_traits");
                i.putExtra("cattleId",cattleId);
                i.putExtra("mode","readOnly");
                startActivity(i);
            }
        });

        cattleProfileBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.saveCattleDetails("","","","");
                finish();
            }
        });

        cattleProfileBinding.btnAddMilkCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","personal_mik_weight");
                i.putExtra("cattleID", cattleId);
                startActivity(i);
            }
        });

        cattleProfileBinding.addCattleMilkingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","personal_milk");
                i.putExtra("cattleID", cattleId);
                startActivity(i);
            }
        });

        cattleProfileBinding.addCattleTraitsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","personal_traits");
                i.putExtra("cattleID", cattleId);
                startActivity(i);
            }
        });

        cattleProfileBinding.addCattleMedicalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityCattleProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","personal_medical");
                i.putExtra("cattleID", cattleId);
                startActivity(i);
            }
        });

        cattleProfileBinding.cattleProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        cattleProfileBinding.sidePose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSidePose();
            }
        });

        cattleProfileBinding.frontPose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogFrontPose();
            }
        });

//        getCattleProfileData();




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.saveCattleDetails("","","","");
        sessionManager.SaveCattleID("");

    }

    public void alertDialog(){
        AlertDialog.Builder imageView = new AlertDialog.Builder(ActivityCattleProfile.this);
        View view = LayoutInflater.from(ActivityCattleProfile.this).inflate(R.layout.cattle_image, null, false);
        imageView.setView(view);
        ImageView cattleImgView = view.findViewById(R.id.cattleImage);
        Glide.with(ActivityCattleProfile.this).load(mainImageUrl).into(cattleImgView);
        imageView.show();
    }

    public void alertDialogFrontPose(){
        AlertDialog.Builder imageView = new AlertDialog.Builder(ActivityCattleProfile.this);
        View view = LayoutInflater.from(ActivityCattleProfile.this).inflate(R.layout.cattle_image, null, false);
        imageView.setView(view);
        ImageView cattleImgView = view.findViewById(R.id.cattleImage);
        Glide.with(ActivityCattleProfile.this).load(frontPoseUrl).into(cattleImgView);
        imageView.show();
    }

    public void alertDialogSidePose(){
        AlertDialog.Builder imageView = new AlertDialog.Builder(ActivityCattleProfile.this);
        View view = LayoutInflater.from(ActivityCattleProfile.this).inflate(R.layout.cattle_image, null, false);
        imageView.setView(view);
        ImageView cattleImgView = view.findViewById(R.id.cattleImage);
        Glide.with(ActivityCattleProfile.this).load(sidePoseUrl).into(cattleImgView);
        imageView.show();
    }

    public void getCattleProfileData(){
        ArrayList<CattleBasicDetailsModel> cattleBasicDetailsModelArrayList = new ArrayList<>();
        Call<JsonObject> callApi = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getCattleProfile(cattleId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (!response.body().get("error").getAsString().equals("true")){
                        JsonObject cattlePorfile = response.body();
                        JsonObject dataObject = cattlePorfile.get("data").getAsJsonObject();

                        JsonObject cattleDetails = dataObject.get("cattleDetails").getAsJsonObject();
                        String sampleID = cattleDetails.get("sampleID").getAsString();
                        String farmerCattleID = cattleDetails.get("farmerCattleID").getAsString();
                        String cTypeID = cattleDetails.get("cTypeID").getAsString();
                        String cTypeName = cattleDetails.get("cTypeName").getAsString();
                        String cBreedName = cattleDetails.get("cBreedName").getAsString();
                        String cattleGender = cattleDetails.get("cattleGender").getAsString();
                        String cBreedID = cattleDetails.get("cBreedID").getAsString();
                        String breedRecently = cattleDetails.get("breedRecently").getAsString();
                        String cattle_feed_per_day = cattleDetails.get("cattle_feed_per_day").getAsString();
                        String greenFeed_kg = cattleDetails.get("greenFeed_kg").getAsString();
                        String hasGrazing_area = cattleDetails.get("hasGrazing_area").getAsString();
                        String hoursOf_grazing = cattleDetails.get("hoursOf_grazing").getAsString();
                        String matedRecently = cattleDetails.get("matedRecently").getAsString();
                        String teeth_of_cattle = cattleDetails.get("teeth_of_cattle").getAsString();
                        String wandaFeed_kg = cattleDetails.get("wandaFeed_kg").getAsString();

                        cattleProfileBinding.cattleType.setText(cTypeName);
                        cattleProfileBinding.cattleGender.setText(cattleGender);
                        cattleProfileBinding.cattleBreed.setText(cBreedName);
                        cattleProfileBinding.cattleSampleId.setText(sampleID);

                        CattleBasicDetailsModel cattleBasicDetailsModel = new CattleBasicDetailsModel(sampleID, farmerCattleID, cTypeID, cattleGender,
                                cBreedID, breedRecently, cattle_feed_per_day, greenFeed_kg, hasGrazing_area, hoursOf_grazing, matedRecently, teeth_of_cattle, wandaFeed_kg);
                        cattleBasicDetailsModelArrayList.add(cattleBasicDetailsModel);

                        cattleBasicDetailsAdapter = new CattleBasicDetailsAdapter(cattleBasicDetailsModelArrayList,ActivityCattleProfile.this);
                        cattleProfileBinding.basicEntityRecycler.setLayoutManager(new LinearLayoutManager(ActivityCattleProfile.this));
                        cattleProfileBinding.basicEntityRecycler.setAdapter(cattleBasicDetailsAdapter);

                        JsonObject cattleImages = dataObject.get("cattleImages").getAsJsonObject();
                        mainImageUrl = cattleImages.get("main_img").getAsString().replace("\\", "");
                        Log.d("url",mainImageUrl);
                        frontPoseUrl = cattleImages.get("frontPose_picture").getAsString().replace("\\", "");
                        sidePoseUrl = cattleImages.get("sidePose_picture").getAsString().replace("\\", "");

                        Glide.with(cattleProfileBinding.cattleProfile).load(mainImageUrl).placeholder(R.drawable.baseline_image_not_supported_24).into(cattleProfileBinding.cattleProfile);

                        Glide.with(cattleProfileBinding.frontPose).load(frontPoseUrl).placeholder(R.drawable.baseline_image_not_supported_24).into(cattleProfileBinding.frontPose);

                        Glide.with(cattleProfileBinding.sidePose).load(sidePoseUrl).placeholder(R.drawable.baseline_image_not_supported_24).into(cattleProfileBinding.sidePose);

                        JsonObject cattleEntities = dataObject.get("cattleEntities").getAsJsonObject();
                        JsonObject personal_milk = cattleEntities.get("personal_milk").getAsJsonObject();
                        if (personal_milk.getAsJsonObject().size() == 0){
                            cattleProfileBinding.viewCattleMilkingDataLayout.setVisibility(View.GONE);
                            cattleProfileBinding.addCattleMilkingDataLayout.setVisibility(View.VISIBLE);
                        }else {
                            cattleProfileBinding.viewCattleMilkingDataLayout.setVisibility(View.VISIBLE);
                            cattleProfileBinding.addCattleMilkingDataLayout.setVisibility(View.GONE);
                            String cattleEntityID = personal_milk.get("cattleEntityID").getAsString();
                            JsonObject formJson = personal_milk.get("formJSON").getAsJsonObject();
                        }

                        JsonObject personal_medical = cattleEntities.get("personal_medical").getAsJsonObject();
                        Log.d("personal_medical", String.valueOf(personal_medical));
                        if (personal_medical.getAsJsonObject().size() == 0){
                            cattleProfileBinding.viewCattleMedicalDataLayout.setVisibility(View.GONE);
                            cattleProfileBinding.addCattleMedicalDataLayout.setVisibility(View.VISIBLE);
                        }else {
                            cattleProfileBinding.viewCattleMedicalDataLayout.setVisibility(View.VISIBLE);
                            cattleProfileBinding.addCattleMedicalDataLayout.setVisibility(View.GONE);
                        }

                        JsonObject personal_traits = cattleEntities.get("personal_traits").getAsJsonObject();
                        if (personal_traits.getAsJsonObject().size() == 0){
                            cattleProfileBinding.viewCattleTraitsDataLayout.setVisibility(View.GONE);
                            cattleProfileBinding.addCattleTraitsDataLayout.setVisibility(View.VISIBLE);
                        }else {
                            cattleProfileBinding.viewCattleTraitsDataLayout.setVisibility(View.VISIBLE);
                            cattleProfileBinding.addCattleTraitsDataLayout.setVisibility(View.GONE);
                        }
                    }else {
                        String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                        Toast.makeText(ActivityCattleProfile.this,msg,Toast.LENGTH_SHORT).show();
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


}