package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cncdcattleedcandroid.Adapters.CattleAdapter;
import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.ViewModels.WebViewSurveyViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityFarmerProfileBinding;
import com.smb.animatedtextview.AnimatedTextView;

import java.util.ArrayList;

public class ActivityFarmerProfile extends AppCompatActivity {


    ActivityFarmerProfileBinding farmerProfileBinding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        farmerProfileBinding = ActivityFarmerProfileBinding.inflate(getLayoutInflater());
        setContentView(farmerProfileBinding.getRoot());

        InitializeRecyclerView();
        AnimateTextView1();
        AnimateTextView2();
        sessionManager = new SessionManager(this);
        changeIcons();
        farmerProfileBinding.btnAddFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ActivityFarmerProfile.this, ActivityWebViewSurveyForm.class);
                startActivity(i);
            }
        });

        farmerProfileBinding.addcattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityFarmerProfile.this, ActivityCattleProfile.class);
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

        farmerProfileBinding.animtextcow.animateTo("1");
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

        farmerProfileBinding.animtextbuffalo.animateTo("1");
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


}