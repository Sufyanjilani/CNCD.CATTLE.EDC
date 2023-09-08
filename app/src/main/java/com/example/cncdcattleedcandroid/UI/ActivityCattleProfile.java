package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

        cattleProfileBinding.cattleType.setText("Type: "+sessionManager.getcattleType());
        cattleProfileBinding.cattleGender.setText("Gender: "+sessionManager.getcattleGender());
        cattleProfileBinding.cattleBreed.setText("Breed: "+sessionManager.getcattleBreed());
        cattleProfileBinding.cattleSampleId.setText("Sample ID: "+sessionManager.getcattleSampleId());

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.saveCattleDetails("","","","");

    }
}