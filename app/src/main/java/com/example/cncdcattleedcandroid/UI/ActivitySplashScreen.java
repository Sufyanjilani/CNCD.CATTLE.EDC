package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.databinding.ActivitySplashScreenBinding;

public class ActivitySplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding splashScreenBinding;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        getWindow().setExitTransition(fade);

        ImageView sharedview = splashScreenBinding.appLogo;
        sessionManager = new SessionManager(this);
        sessionManager.ApplicationFirstTime();


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



}