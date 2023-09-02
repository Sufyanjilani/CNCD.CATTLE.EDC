package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.databinding.ActivityEntityBinding;

public class ActivityEntity extends AppCompatActivity {

    ActivityEntityBinding activityEntityBinding;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntityBinding = ActivityEntityBinding.inflate(getLayoutInflater());
        setContentView(activityEntityBinding.getRoot());
        sessionManager = new SessionManager(this);

        activityEntityBinding.name.setText(sessionManager.getFarmerName());
        activityEntityBinding.farmName.setText(sessionManager.getFarmName());
        activityEntityBinding.farmAddress.setText(sessionManager.getFarmAddress()+", "+sessionManager.getFarmSector());
        activityEntityBinding.farmerMobile.setText(sessionManager.getMobileNumber()+" / "+sessionManager.getAltNumber());
        activityEntityBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}