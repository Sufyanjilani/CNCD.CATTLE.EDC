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

        if (sessionManager.checkisApplicationFirstTime()) {
            sessionManager.ApplicationFirstTime();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplashScreen.this,ActivityWebViewSurveyForm.class);
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(ActivitySplashScreen.this,sharedview, "applogoimage");
                startActivity(i);
                finish();

            }
        },2000);

    }

    public void getDataforInjection() {

        Call<JsonObject> apireader = new RetrofitClientSurvey().retrofitclient().getcities();
        apireader.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    JsonObject citiesObject = response.body();
                    Log.d(constants.Tag, citiesObject.toString());

                    JsonArray data = citiesObject.get("data").getAsJsonArray();

                    JsonObject obj1 = data.get(0).getAsJsonObject();
                    String country = obj1.get("country").getAsString();
                    String countryInitials = obj1.get("countryInitials").getAsString();
                    String countrycode = obj1.get("countryCode").toString();
                    JsonArray statesAndCities = obj1.get("statesAndCities").getAsJsonArray();
                    JsonObject  statesAndCitiesObject1 = statesAndCities.get(0).getAsJsonObject();
                    String statename = statesAndCitiesObject1.get("stateName").getAsString();
                    JsonArray cities= statesAndCitiesObject1.get("cities").getAsJsonArray().getAsJsonArray();



                    databaseHlper.insertCities(country,countryInitials,countrycode,statename,cities.toString());




                    Log.d(constants.Tag, country);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d(constants.Tag, t.getMessage().toString());
            }
        });


    }



}