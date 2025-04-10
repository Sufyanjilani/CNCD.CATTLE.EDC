package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.Utils.InternetUtils;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.ViewModels.DashboardViewModel;
import com.example.cncdcattleedcandroid.ViewModels.LoginViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityLoginBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {


    ActivityLoginBinding activityLoginBinding;
    SessionManager sessionManager;


    LoadingDialog loadingDialog;

    LoginViewModel loginViewModel;

    FusedLocationProviderClient locationProviderClient;
    Constants constants;

    ArrayList<String> coordinates= new ArrayList<>();

    RealmDatabaseHlper realmDatabaseHlper;

    String appVersion = "";

    String lat,lon = "0.0";


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
        constants= new Constants();
        setUpLocation();
        getcurrentlocationEnd();
        realmDatabaseHlper = new RealmDatabaseHlper();
        realmDatabaseHlper.InitializeRealm(this);


        try {
            appVersion = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        activityLoginBinding.appVersion.setText("Version: "+appVersion);

        loginViewModel.isloginsucces.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s.equals("Login Successes")){
                    loadingDialog.ShowCustomLoadingDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {



                                startActivity(new Intent(ActivityLogin.this, ActivitySettingData.class));
                                loadingDialog.dissmissDialog();


                        }
                    },2000);
                }
                else{

                    loadingDialog.ShowCustomLoadingDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityLogin.this,s,Toast.LENGTH_SHORT).show();
                            loadingDialog.dissmissDialog();
                            loadingDialog.dissmissDialog();
                        }
                    },2000);

                }
            }
        });

        activityLoginBinding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(activityLoginBinding.name.getText().toString().equals("")) {

                    Toast.makeText(ActivityLogin.this,"Email is empty",Toast.LENGTH_SHORT).show();
                }
                else if (activityLoginBinding.password.getText().toString().equals("")){

                    Toast.makeText(ActivityLogin.this,"Passowrd is empty",Toast.LENGTH_SHORT).show();

                }
                else{

                    Login(activityLoginBinding.name.getText().toString(),activityLoginBinding.password.getText().toString());
                }
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


    public void Login(String email,String password){

        if (InternetUtils.isInternetConnected(getApplicationContext())) {
            // Internet is available
            // Do your internet-related tasks here
            String appversion = "";
            try {
                appversion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
                ;
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {

                Log.d("package", nameNotFoundException.getMessage().toString());
            }

            loginViewModel.Login(email,password,appversion,lat+","+lon);
        } else {
            // No internet connection
            // Display a message or handle the lack of internet connection
            androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(ActivityLogin.this);
            dialog.setTitle("No internet Connection present at the moment");
            dialog.setMessage("Do you wish to go to internet settings?");
            dialog.setIcon(R.drawable.cowimage);
            dialog.setCancelable(false);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finishAffinity();
                }
            });

            dialog.show();
        }




    }

    public ArrayList<String> getcurrentlocationEnd() {


        ArrayList<String> arrayList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this,permission,4);
        }
        locationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }


        }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {


                    arrayList.add(String.valueOf(location.getLatitude()));
                    arrayList.add(String.valueOf(location.getLongitude()));

                    lat = String.valueOf(location.getLatitude());
                    lon = String.valueOf(location.getLongitude());

                    Log.d(constants.Tag,String.valueOf(location.getLatitude()));
                    Log.d(constants.Tag,String.valueOf(location.getLongitude()));


                }
            }
        });

        return arrayList;
    }

    public void setUpLocation() {

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }



}