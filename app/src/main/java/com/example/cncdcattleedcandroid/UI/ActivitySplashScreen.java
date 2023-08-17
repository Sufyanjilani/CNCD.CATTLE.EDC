package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
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

    RealmDatabaseHlper realmDatabaseHlper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        getWindow().setExitTransition(fade);

        databaseHlper = new RealmDatabaseHlper(this);
        ImageView sharedview = splashScreenBinding.appLogo;
        sessionManager = new SessionManager(this);
        constants = new Constants();
        realmDatabaseHlper = new RealmDatabaseHlper(this);

        if (sessionManager.checkisApplicationFirstTime()) {
            sessionManager.ApplicationFirstTime();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplashScreen.this,ActivityDashboard.class);
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(ActivitySplashScreen.this,sharedview, "applogoimage");
                startActivity(i);
                finish();

            }
        },2000);

    }





}