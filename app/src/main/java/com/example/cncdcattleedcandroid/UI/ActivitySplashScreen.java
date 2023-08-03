package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivitySplashScreenBinding;

public class ActivitySplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding splashScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());
    }
}