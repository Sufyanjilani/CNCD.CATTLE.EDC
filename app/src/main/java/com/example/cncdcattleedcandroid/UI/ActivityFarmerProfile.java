package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cncdcattleedcandroid.Adapters.CattleAdapter;
import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.ViewModels.WebViewSurveyViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityFarmerProfileBinding;

import java.util.ArrayList;

public class ActivityFarmerProfile extends AppCompatActivity {


    ActivityFarmerProfileBinding farmerProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        farmerProfileBinding = ActivityFarmerProfileBinding.inflate(getLayoutInflater());
        setContentView(farmerProfileBinding.getRoot());

        InitializeRecyclerView();

        farmerProfileBinding.btnAddFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ActivityFarmerProfile.this, ActivityWebViewSurveyForm.class);
                startActivity(i);
            }
        });

    }

    public void InitializeRecyclerView(){

        ArrayList<Cattles> cattleslist = new ArrayList<>();
        cattleslist.add(new Cattles("Cow","2"));
        cattleslist.add(new Cattles("Goat","3"));
        cattleslist.add(new Cattles("Buffalo","4"));

        CattleAdapter cattleAdapter = new CattleAdapter(cattleslist);
        farmerProfileBinding.recyccattle.setAdapter(cattleAdapter);


    }
}