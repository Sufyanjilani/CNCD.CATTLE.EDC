package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivityCattleProfileBinding;

public class ActivityCattleProfile extends AppCompatActivity {


    ActivityCattleProfileBinding cattleProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cattleProfileBinding = ActivityCattleProfileBinding.inflate(getLayoutInflater());
        setContentView(cattleProfileBinding.getRoot());


    }
}