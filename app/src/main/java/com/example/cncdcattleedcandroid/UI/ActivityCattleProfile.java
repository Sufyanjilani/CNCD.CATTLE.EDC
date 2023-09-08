package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
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

import java.util.ArrayList;
import java.util.List;

public class ActivityCattleProfile extends AppCompatActivity {

    ActivityCattleProfileBinding cattleProfileBinding;
    SessionManager sessionManager;
    String farmId, farmerId, cattleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cattleProfileBinding = ActivityCattleProfileBinding.inflate(getLayoutInflater());
        setContentView(cattleProfileBinding.getRoot());
        sessionManager = new SessionManager(this);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        cattleId = extra.getString("cattleID");

        cattleProfileBinding.name.setText(sessionManager.getFarmerName());
        cattleProfileBinding.farmName.setText(sessionManager.getFarmName());
        cattleProfileBinding.farmAddress.setText(sessionManager.getFarmAddress()+", "+sessionManager.getFarmSector());
        cattleProfileBinding.farmerMobile.setText(sessionManager.getMobileNumber()+" / "+sessionManager.getAltNumber());

        cattleProfileBinding.cattleType.setText(sessionManager.getcattleType());
        cattleProfileBinding.cattleGender.setText(sessionManager.getcattleGender());
        cattleProfileBinding.cattleBreed.setText(sessionManager.getcattleBreed());
        cattleProfileBinding.cattleSampleId.setText(sessionManager.getcattleSampleId());

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
                i.putExtra("cattleId",cattleId);
                i.putExtra("mode","readOnly");
                startActivity(i);
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

        cattleProfileBinding.cattleProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.saveCattleDetails("","","","");

    }

    public void alertDialog(){
        AlertDialog.Builder imageView = new AlertDialog.Builder(ActivityCattleProfile.this);
        View view = LayoutInflater.from(ActivityCattleProfile.this).inflate(R.layout.cattle_image, null, false);
        imageView.setView(view);
        ImageView cattleImgView = view.findViewById(R.id.cattleImage);
        Glide.with(ActivityCattleProfile.this).load(cattleProfileBinding.cattleProfile).into(cattleImgView);
        imageView.show();


    }
}