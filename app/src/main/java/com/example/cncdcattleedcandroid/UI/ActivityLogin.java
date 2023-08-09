package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.Fade;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {


    ActivityLoginBinding activityLoginBinding;
    SessionManager sessionManager;


    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        CreateSharedTransition();
        sessionManager = new SessionManager(this);
        checkThemesState();
        loadingDialog = new LoadingDialog(this,this);
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();

        getWindow().setEnterTransition(fade);
        activityLoginBinding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.ShowCustomLoadingDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ActivityLogin.this,ActivityDashboard.class));
                        loadingDialog.dissmissDialog();
                    }
                },2000);

            }
        });

    }
    public void checkThemesState(){

        if(sessionManager.getthemstate()){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void CreateSharedTransition(){

        View sharedView = findViewById(R.id.appLogo);
        String transitionName = ViewCompat.getTransitionName(sharedView);
        sharedView.setTransitionName(transitionName);
        supportPostponeEnterTransition();
        sharedView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                sharedView.getViewTreeObserver().removeOnPreDrawListener(this);
                supportStartPostponedEnterTransition();
                return true;
            }
        });
    }



}