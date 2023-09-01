package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.ViewModels.SettingDataViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivitySplashScreenBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding splashScreenBinding;
    SessionManager sessionManager;

    RealmDatabaseHlper databaseHlper;

    Constants constants;

    String appVersion = "";

    RealmDatabaseHlper realmDatabaseHlper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        getWindow().setExitTransition(fade);
       ;

        databaseHlper = new RealmDatabaseHlper(this);
        ImageView sharedview = splashScreenBinding.appLogo;
        sessionManager = new SessionManager(this);
        constants = new Constants();
        CheckUser();

        realmDatabaseHlper = new RealmDatabaseHlper(this);

        checkThemesState();

        try {
            appVersion = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        splashScreenBinding.appVersion.setText("Version: "+appVersion);

    }


    public void checkThemesState(){

        if(sessionManager.getthemstate()){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    public void CheckUser(){

        if (!sessionManager.getbearer().equals("null")){


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    if (sessionManager.checkisApplicationFirstTime()){
                        Intent i = new Intent(ActivitySplashScreen.this, ActivitySettingData.class);
                        startActivity(i);
                        finish();

                    }
                    else {

                        Intent i = new Intent(ActivitySplashScreen.this, ActivityDashboard.class);
                        Log.d(constants.Tag, sessionManager.getbearer());
                        startActivity(i);
                        finish();
                    }

                }
            },2000);


        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(ActivitySplashScreen.this,sharedview, "applogoimage");
                    startActivity(i);
                    finish();

                }
            },2000);

        }
    }


}