package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.cncdcattleedcandroid.Adapters.CattleAdapter;
import com.example.cncdcattleedcandroid.Adapters.DataGridAdapter;
import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.Models.DataGridModel;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.FarmerAdapter;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.ViewModels.WebViewSurveyViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityFarmerProfileBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smb.animatedtextview.AnimatedTextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmerProfile extends AppCompatActivity {


    ActivityFarmerProfileBinding farmerProfileBinding;

    LoadingDialog loadingDialog;
    SessionManager sessionManager;
    Constants constants;
    DataGridAdapter gridAdapter;

    String farmId, farmerId, entity;
    String totalCattles, totalCows, googleMapsURL, totalBuffalo, farmID, farmName, farmAddress, farmSector, created_at, farmerID, farmerName, farmerMobileNumber, farmerMobileAlternative;

    @Override
    protected void onResume() {
        super.onResume();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.ShowCustomLoadingDialog();
                getFarmerProfile();
                loadingDialog.dissmissDialog();
            }
        },2000);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.ShowCustomLoadingDialog();
                getFarmerProfile();
                loadingDialog.dissmissDialog();
            }
        },2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        farmerProfileBinding = ActivityFarmerProfileBinding.inflate(getLayoutInflater());
        setContentView(farmerProfileBinding.getRoot());
        constants = new Constants();
        loadingDialog = new LoadingDialog(this,this);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
//        entity = extra.getString("entityProfile");
        farmId = extra.getString("farmId");
        farmerId = extra.getString("farmerId");



        AnimateTextView1();
        AnimateTextView2();
        sessionManager = new SessionManager(this);
        changeIcons();
//        LoadDataTOGrid();
        AddFilterFunctionalityToDataGrid();
//        InitializeRecyclerViewWithDataGrid();

//        farmerProfileBinding.btnAddFarmer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(ActivityFarmerProfile.this, ActivityWebViewSurveyForm.class);
//                startActivity(i);
//            }
//        });

        farmerProfileBinding.btnaddcattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityFarmerProfile.this, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","personal_basic");
                i.putExtra("farmID",farmID);
                i.putExtra("farmerID",farmerID);

                startActivity(i);

            }
        });
        loadingDialog.ShowCustomLoadingDialog();
        getFarmerProfile();

        farmerProfileBinding.locationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsURL));
                startActivity(intent);
            }
        });

        farmerProfileBinding.btnAddEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityFarmerProfile.this, ActivityEntity.class);
                startActivity(intent);
            }
        });

        farmerProfileBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityFarmerProfile.this, ActivityDashboard.class);
                startActivity(intent);
            }
        });

    }

    public void getFarmerProfile(){
        ArrayList<DataGridModel> dataGridModelArrayList = new ArrayList<>();
        Call<JsonObject> apiGet = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getFarmerData(farmId,farmerId);
        apiGet.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){

                    if (!response.body().get("error").getAsString().equals("true")){

                        JsonObject farmerData = response.body();
                        JsonObject dataObject = farmerData.get("data").getAsJsonObject();
                        JsonObject cardsData = dataObject.get("cardsData").getAsJsonObject();
                        Log.d(constants.Tag, String.valueOf(cardsData));
                        totalCattles = cardsData.get("totalCattles").getAsString();
                        totalCows = cardsData.get("totalCows").getAsString();
                        totalBuffalo = cardsData.get("totalBuffaloes").getAsString();


                        JsonObject gridData = dataObject.get("gridsData").getAsJsonObject();
                        JsonObject farmObject = gridData.get("farm").getAsJsonObject();
                        Log.d(constants.Tag, String.valueOf(farmObject));
                        farmID = farmObject.get("farmID").getAsString();
                        farmName = farmObject.get("farmName").getAsString();
                        farmAddress = farmObject.get("farmAddress").getAsString();
                        farmSector = farmObject.get("farmSector").getAsString();
                        googleMapsURL = farmObject.get("googleMapsURL").getAsString();
                        created_at = farmObject.get("created_at").getAsString();


                        JsonObject farmerObject = gridData.get("farmer").getAsJsonObject();
                        Log.d(constants.Tag, String.valueOf(farmerObject));
                        farmerID = farmerObject.get("farmerID").getAsString();
                        farmerName = farmerObject.get("farmerName").getAsString();
                        farmerMobileNumber = farmerObject.get("farmerMobileNumber").getAsString();
                        farmerMobileAlternative = farmerObject.get("farmerMobileAlternative").getAsString();

                        JsonArray cattleArray = gridData.get("cattles").getAsJsonArray();
                        for (int i = 0; i <cattleArray.size(); i++){
                            JsonObject cattelObject = cattleArray.get(i).getAsJsonObject();
                            String cattleID = cattelObject.get("cattleID").getAsString();
                            String farmerCattleID = cattelObject.get("farmerCattleID").getAsString();
                            String cTypeID = cattelObject.get("cTypeID").getAsString();
                            String cTypeName = cattelObject.get("cTypeName").getAsString();
                            String cattleGender = cattelObject.get("cattleGender").getAsString();
                            String cBreedID = cattelObject.get("cBreedID").getAsString();
                            String cBreedName = cattelObject.get("cBreedName").getAsString();
                            String created_at = cattelObject.get("created_at").getAsString();
                            String created_by = cattelObject.get("created_by").getAsString();
                            String updated_at = cattelObject.get("updated_at").toString() == null ? "null":cattelObject.get("updated_at").toString();
                            String updated_by = cattelObject.get("updated_by").getAsString();
                            String sampleID = cattelObject.get("sampleID").getAsString();

                            DataGridModel dataGridModel = new DataGridModel(cattleID, farmerCattleID, cTypeID, cTypeName, cattleGender, cBreedID,
                                    cBreedName, created_at, created_by, updated_at, updated_by, sampleID);
                            dataGridModelArrayList.add(dataGridModel);
                        }

                        gridAdapter = new DataGridAdapter(dataGridModelArrayList,ActivityFarmerProfile.this);
                        farmerProfileBinding.recyccattle.setLayoutManager(new LinearLayoutManager(ActivityFarmerProfile.this));
                        farmerProfileBinding.recyccattle.setAdapter(gridAdapter);

                        farmerProfileBinding.animtextcattel.setText(totalCattles);
                        farmerProfileBinding.animtextcow.setText(totalCows);
                        farmerProfileBinding.animtextbuffalo.setText(totalBuffalo);
                        farmerProfileBinding.name.setText(farmerName);
                        if (farmerMobileAlternative != ""){
                            farmerProfileBinding.farmerMobile.setText(farmerMobileNumber+" / "+farmerMobileAlternative);
                        }else {
                            farmerProfileBinding.farmerMobile.setText(farmerMobileNumber);
                        }

                        farmerProfileBinding.farmName.setText(farmName);
                        farmerProfileBinding.farmAddress.setText(farmAddress+", "+ farmSector);


//                    farmerProfileBinding.farmSector.setText("Farm Sector: "+farmSector);
//        Log.d("name", farmerName);

                        sessionManager.saveFarmerData(
                                farmerName,
                                farmName,
                                farmAddress,
                                farmSector,
                                farmerMobileNumber,
                                farmerMobileAlternative);
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

//    public void InitializeRecyclerView(){
//
//        ArrayList<Cattles> cattleslist = new ArrayList<>();
////        cattleslist.add(new Cattles("Cow","2"));
////        cattleslist.add(new Cattles("Goat","3"));
////        cattleslist.add(new Cattles("Buffalo","4"));
//
//        CattleAdapter cattleAdapter = new CattleAdapter(cattleslist,this);
//        farmerProfileBinding.recyccattle.setAdapter(cattleAdapter);
//
//
//    }

//    public void InitializeRecyclerViewWithDataGrid(){
//
//        ArrayList<DataGridModel> datalist = new ArrayList<>();
//        datalist.add(new DataGridModel("1","1","1","MyFarm","Cow",
//                "data:image/jpeg;base64,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","1"));
//        datalist.add(new DataGridModel("2","1","1","MyFarm","Calf",
//                "data:image/jpeg;base64,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","2"));
//        datalist.add(new DataGridModel("2","1","1","MyFarm","Buffalo",
//                "data:image/jpeg;base64,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","3"));
//        gridAdapter = new DataGridAdapter(datalist,this);
//        farmerProfileBinding.recyccattle.setAdapter(gridAdapter);
//        Log.d("data",datalist.toString());
//
//
//    }



    private Boolean isDarkThemeEnabled() {

        if(sessionManager.getthemstate()){

            setDarkTheme();
            return  true;
        }
        else{

            setLightTheme();
            return  false;
        }
    }


    public void changeIcons(){

        if(isDarkThemeEnabled()){

            farmerProfileBinding.cowimg.setImageResource(R.drawable.cowwhite);
            farmerProfileBinding.buffaloimg.setImageResource(R.drawable.buffalowhite);

        }
        else{

            farmerProfileBinding.cowimg.setImageResource(R.drawable.buffaloicon);
            farmerProfileBinding.cowimg.setImageResource(R.drawable.cowicon);
        }
    }

    public void AnimateTextView1(){




        farmerProfileBinding.animtextcow.setText("1000");
        farmerProfileBinding.animtextcow.addOnAnimationListener(new AnimatedTextView.AnimationListener() {
            @Override
            public void onAnimationStart(@NonNull String s, @NonNull String s1) {

            }

            @Override
            public void onAnimationEnd(@NonNull String s, @NonNull String s1) {

            }
        });

//        farmerProfileBinding.animtextcow.animateTo("1");
        farmerProfileBinding.animtextcow.getBareText();
    }


    public void AnimateTextView2(){




        farmerProfileBinding.animtextbuffalo.setText("1000");
        farmerProfileBinding.animtextbuffalo.addOnAnimationListener(new AnimatedTextView.AnimationListener() {
            @Override
            public void onAnimationStart(@NonNull String s, @NonNull String s1) {

            }

            @Override
            public void onAnimationEnd(@NonNull String s, @NonNull String s1) {

            }
        });

//        farmerProfileBinding.animtextbuffalo.animateTo("1");
        farmerProfileBinding.animtextbuffalo.getBareText();
    }


    private void setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.d("tag", "dark");
        sessionManager.setthemstate(true);


    }

    private void setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Log.d("tag", "Light");
        sessionManager.setthemstate(false);


    }


//    public void LoadDataTOGrid(){
//
//        ArrayList<DataGridModel> dataGridModels = new ArrayList<>();
//        dataGridModels.add(new DataGridModel(
//                "1",
//                "1",
//                "Farmer",
//                "cow",
//                "imagepath",
//                "imagepath"
//                ,"1"));
//
//        dataGridModels.add(new DataGridModel(
//                "2",
//                "2",
//                "Farmer khan",
//                "cow",
//                "imagepath",
//                "imagepath"
//                ,"2"));
//        dataGridModels.add(new DataGridModel(
//                "3",
//                "3",
//                "Farmer Rana",
//                "cow",
//                "imagepath",
//                "imagepath"
//                ,"3"));
//        dataGridModels.add(new DataGridModel(
//                "4",
//                "4",
//                "Farmer Man",
//                "cow",
//                "imagepath",
//                "imagepath"
//                ,"4"));
//
//        gridAdapter = new DataGridAdapter(dataGridModels,this);
//        farmerProfileBinding.recyccattle.setAdapter(gridAdapter);
//    }


    public void AddFilterFunctionalityToDataGrid(){
        farmerProfileBinding.SearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                gridAdapter.filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }



}