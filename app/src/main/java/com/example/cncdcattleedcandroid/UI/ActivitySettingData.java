package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.ViewModels.DashboardViewModel;
import com.example.cncdcattleedcandroid.ViewModels.SettingDataViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivitySettingDataBinding;

public class ActivitySettingData extends AppCompatActivity {


    ActivitySettingDataBinding settingDataBinding;
    SettingDataViewModel viewModel;
    RealmDatabaseHlper realmDatabaseHlper;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingDataBinding = ActivitySettingDataBinding.inflate(getLayoutInflater());
        setContentView(settingDataBinding.getRoot());

        viewModel = new ViewModelProvider(this).get(SettingDataViewModel.class);

        realmDatabaseHlper = new RealmDatabaseHlper();
        realmDatabaseHlper.InitializeRealm(this);
        sessionManager = new SessionManager(this);



        CheckisDataSavedOffline();

    }

    public void CheckisDataSavedOffline(){


        viewModel.callSavedFormsMethod();
        viewModel.getDataforInjection();
        viewModel.surveyformsResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s.equals("success")){

                    settingDataBinding.txtprogress.setText("Almost Complete...");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            if (sessionManager.checkisApplicationFirstTime()) {
                                sessionManager.ApplicationFirstTime();
                            }

                            startActivity(new Intent(ActivitySettingData.this, ActivityDashboard.class));
                            finish();
                        }
                    },1000);


                }
                else if (s.equals("")){
                    settingDataBinding.txtprogress.setText("Sorry! there was some problem...");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(ActivitySettingData.this, ActivityDashboard.class));
                            finish();
                        }
                    },1000);

                }
            }
        });



    }
}