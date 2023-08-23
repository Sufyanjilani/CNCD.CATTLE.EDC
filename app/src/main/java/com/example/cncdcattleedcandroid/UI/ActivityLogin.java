package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.ViewModels.DashboardViewModel;
import com.example.cncdcattleedcandroid.ViewModels.LoginViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityLoginBinding;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {


    ActivityLoginBinding activityLoginBinding;
    SessionManager sessionManager;


    LoadingDialog loadingDialog;

    LoginViewModel loginViewModel;

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
        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);


        loginViewModel.isloginsucces.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s.equals("Login Successs")){
                    loadingDialog.ShowCustomLoadingDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (sessionManager.checkisApplicationFirstTime()) {

                                startActivity(new Intent(ActivityLogin.this, ActivitySettingData.class));
                                loadingDialog.dissmissDialog();
                            }
                            else{

                                startActivity(new Intent(ActivityLogin.this, ActivityDashboard.class));
                                loadingDialog.dissmissDialog();
                            }
                        }
                    },2000);
                }
                else{

                    loadingDialog.ShowCustomLoadingDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityLogin.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                            loadingDialog.dissmissDialog();
                        }
                    },2000);

                }
            }
        });

        activityLoginBinding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login();
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


    public void Login(){

        loginViewModel.Login("ro1@cncdpk.com","secret","1.0","24.2");

    }



}