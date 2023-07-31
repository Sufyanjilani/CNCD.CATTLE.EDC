package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {


    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
    }
}