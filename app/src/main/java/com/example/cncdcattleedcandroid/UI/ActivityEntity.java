package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivityEntityBinding;

public class ActivityEntity extends AppCompatActivity {

    ActivityEntityBinding activityEntityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntityBinding = ActivityEntityBinding.inflate(getLayoutInflater());
        setContentView(activityEntityBinding.getRoot());
    }
}