package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.webkit.ValueCallback;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.BubbleDataEntry;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.charts.Polar;
import com.anychart.charts.Scatter;
import com.anychart.core.axes.Linear;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.PolarSeriesType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.ScaleTypes;
import com.anychart.enums.TooltipDisplayMode;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.cncdcattleedcandroid.Adapters.CattleAdapter;
import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.OfflineDb.Models.DashboardDataModel;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.InternetUtils;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.ViewModels.DashboardViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityDashboardBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.smb.animatedtextview.AnimatedTextView;

import org.eazegraph.lib.models.PieModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.annotations.PrimaryKey;


public class ActivityDashboard extends AppCompatActivity {

    ActivityDashboardBinding activityDashboardBinding;
    DrawerLayout mDrawer;

    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    CattleAdapter cattleAdapter;

    Toolbar toolbar;
    private static final int REQUEST_PERMISSIONS_CODE = 123;

    BarChart chart;

    SessionManager sessionManager;


    RealmDatabaseHlper databaseHlper;

    DashboardViewModel viewModel;

    RealmDatabaseHlper realmDatabaseHlper;

    LoadingDialog loadingDialog;

    ArrayList<Cattles> arrayList = new ArrayList<>() ;
    String appVersion = "";
    TextView headerName, headerVersion;
    String totalFarms, totalFarmers, totalCattles;
    String farmerID, farmID;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        mDrawer = activityDashboardBinding.mydrawerLayout;
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(this);
        databaseHlper = new RealmDatabaseHlper(this);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        databaseHlper.InitializeRealm(this);
        checkThemesState();
        checkAndRequestPermissions();
        AnimateTextView();
        //CheckisDataSavedOffline();
        realmDatabaseHlper = new RealmDatabaseHlper();
        realmDatabaseHlper.InitializeRealm(this);
        loadingDialog = new LoadingDialog(ActivityDashboard.this,this);
        activityDashboardBinding.researchOfficerName.setText(sessionManager.getName());
        InitializeHeader(navigationView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

//        if (InternetUtils.isInternetConnected(getApplicationContext())) {
//            // Internet is available
//            // Do your internet-related tasks here
//            setCardsData();
//        } else {
//            // No internet connection
//            // Display a message or handle the lack of internet connection
//            Toast.makeText(ActivityDashboard.this,"No Internet",Toast.LENGTH_SHORT).show();
//        }







        LoadData();

        setAnimation2();


        setAnimation3();


        viewModel  = new ViewModelProvider(this).get(DashboardViewModel.class);






        drawerToggle = setupDrawerToggle();


        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        setupDrawerContent(navigationView);


        activityDashboardBinding.btnaddFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddFarmer();
            }
        });

        activityDashboardBinding.syncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.ShowCustomLoadingDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.PostAllDataToServer();
                    }
                },1000);

            }
        });


        viewModel.isallDataRetrieved.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {


                if (s.equals("forms retreived")){

                    loadingDialog.dissmissDialog();

                }

                else if (s.equals("No forms found")){

                    loadingDialog.dissmissDialog();

                }
                else{

                    loadingDialog.dissmissDialog();
                }
            }
        });


        viewModel.dashboardDataResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (!s.equals("")){

                    if (s.equals("token_expired")){

                        Toast.makeText(ActivityDashboard.this,s,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityDashboard.this,ActivityLogin.class));
                        sessionManager.SaveCattleID("");
                        sessionManager.savebarearToken("null");
                        sessionManager.Save_Farm_and_Farmer_ID("","");
                        sessionManager.saveStartCoordinatesAndTime(0,0,"");
                        finish();
                    } else if (s.contains("failed")) {

                        loadingDialog.dissmissDialog();
                        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(ActivityDashboard.this);
                        dialog.setTitle("No internet Connection present at the moment");
                        dialog.setMessage("Do you wish to go to internet settings?");
                        dialog.setIcon(R.drawable.cowimage);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                LoadData();

                            }
                        });

                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                finishAffinity();
                            }
                        });

                        dialog.show();


                    } else{

                        Toast.makeText(ActivityDashboard.this,s,Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                        LoadData();
                    }
                },1000);
            }
        });


    }

    public void setCardsData(){
        viewModel.dashboardData();
        viewModel.dashboardDataJson.observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject != null){
                    JsonObject cardObject = jsonObject.get("cardsData").getAsJsonObject();
                    Log.d("obj", cardObject.toString());;
                    totalFarms = cardObject.get("totalFarms").getAsString();
                    totalFarmers = cardObject.get("totalFarmers").getAsString();
                    totalCattles = cardObject.get("totalCattles").getAsString();

                    activityDashboardBinding.farmtext.setText(totalFarms);
                    activityDashboardBinding.farmertext.setText(totalFarmers);
                    activityDashboardBinding.catteltext.setText(totalCattles);
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }


        });

        MenuItem darkModeMenuItem = navigationView.getMenu().findItem(R.id.app_bar_switch);
        Switch darkModeSwitch = darkModeMenuItem.getActionView().findViewById(R.id.mainswitch);
        darkModeSwitch.setActivated(true);
        darkModeSwitch.setChecked(isDarkThemeEnabled());
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setDarkTheme();
            } else {
                setLightTheme();
            }
        });




        InitializeHeader(navigationView);


    }

    public void selectDrawerItem(MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.drawersignout) {

            loadingDialog.ShowCustomLoadingDialogWithCustomMessage("Logging out...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Logout();
                    loadingDialog.dissmissDialog();
                    startActivity(new Intent(ActivityDashboard.this,ActivityLogin.class));
                    finish();
                }
            },1000);



        }

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    private boolean isDarkThemeEnabled() {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        return nightMode == AppCompatDelegate.MODE_NIGHT_YES;
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


    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };

        // List of permissions to request that are not already granted
        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        // Request the permissions
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            // Check if all permissions were granted
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {

                Logger.i("All permissions granted");

            } else {
                // Handle the scenario when some permissions were denied
                Logger.i("All permissions granted");
            }
        }
    }



    public void InitializeHeader(NavigationView navigationView) {

        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.researchOfficer);
        TextView headerVersion = headerView.findViewById(R.id.textViewVersion);
        headerName.setText(sessionManager.getName());
        Log.d("headerName",headerName.toString());

        String appVersion;
        try {
            appVersion = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
            headerVersion.setText(appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        headerVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG","clicked");
                headerVersion.setText("1.0.3");
            }
        });
    }



    public void AnimateTextView(){





//        activityDashboardBinding.farmertext.setText("1000");
//        activityDashboardBinding.farmtext.setText("1000");
//        activityDashboardBinding.catteltext.setText("1000");
        activityDashboardBinding.farmertext.addOnAnimationListener(new AnimatedTextView.AnimationListener() {
            @Override
            public void onAnimationStart(@NonNull String s, @NonNull String s1) {

            }

            @Override
            public void onAnimationEnd(@NonNull String s, @NonNull String s1) {

            }
        });

        activityDashboardBinding.farmtext.addOnAnimationListener(new AnimatedTextView.AnimationListener() {
            @Override
            public void onAnimationStart(@NonNull String s, @NonNull String s1) {

            }

            @Override
            public void onAnimationEnd(@NonNull String s, @NonNull String s1) {

            }
        });

        activityDashboardBinding.catteltext.addOnAnimationListener(new AnimatedTextView.AnimationListener() {
            @Override
            public void onAnimationStart(@NonNull String s, @NonNull String s1) {

            }

            @Override
            public void onAnimationEnd(@NonNull String s, @NonNull String s1) {

            }
        });

//        activityDashboardBinding.farmertext.animateTo(totalFarmers);
        activityDashboardBinding.farmertext.getBareText();
//        activityDashboardBinding.farmtext.animateTo(totalFarms);
        activityDashboardBinding.farmtext.getBareText();
//        activityDashboardBinding.catteltext.animateTo(totalCattles);
        activityDashboardBinding.catteltext.getBareText();
    }


    public void setrecyclerAdapter(){
        ArrayList<Cattles> cattlesArrayList = new ArrayList<>();
        viewModel.dashboardFarmerData.observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject != null){
                    JsonArray farmerData = jsonObject.get("farmers").getAsJsonArray();
                    for (int i = 0; i < farmerData.size(); i++){
                        JsonObject obj = farmerData.get(i).getAsJsonObject();
                        farmerID = obj.get("farmerID").getAsString();
                        farmID = obj.get("farmID").getAsString();
                        String farmName = obj.get("farmName").getAsString();
                        String farmAddress = obj.get("farmAddress").getAsString();
                        String farmerName = obj.get("farmerName").getAsString();
                        String created_at = obj.get("created_at").getAsString();

                        Cattles cattles = new Cattles(farmerID, farmID,
                                farmName, farmAddress, farmerName, created_at);
                        cattlesArrayList.add(cattles);
                        Log.d("listdata",cattlesArrayList.toString());


                    }
                    cattleAdapter = new CattleAdapter(cattlesArrayList, ActivityDashboard.this);
                    activityDashboardBinding.recycler.setLayoutManager(new LinearLayoutManager(ActivityDashboard.this));


                    int resId = R.anim.slide_up_anim_layout;
                    LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(ActivityDashboard.this, resId);
                    activityDashboardBinding.recycler.setLayoutAnimation(animation);
                    //cattleAdapter.notifyDataSetChanged();
                    activityDashboardBinding.recycler.setAdapter(cattleAdapter);
                    sessionManager.saveDashboardFarmFarmerId(
                            farmID,
                            farmerID
                    );

                }
                loadingDialog.dissmissDialog();
            }
        });


    }

    public void setAnimation1(){

        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .repeat(1)
                .playOn(findViewById(R.id.card1));
    }

    public void setAnimation2(){

        YoYo.with(Techniques.SlideInUp)
                .duration(4000)
                .repeat(0)
                .playOn(findViewById(R.id.card2));
    }

    public void setAnimation3(){

        YoYo.with(Techniques.SlideInUp)
                .duration(4000)
                .repeat(0)
                .playOn(findViewById(R.id.card3));
    }



    public void sendImageToNext(){

        activityDashboardBinding.profileImagedashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String transitionName = getString(R.string.transition_name);
                ViewCompat.setTransitionName(activityDashboardBinding.profileImagedashboard, transitionName);


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

    public void AddFarmer(){




        Intent i = new Intent(this, ActivityWebViewSurveyForm.class);
        i.putExtra("formID","general_basic");

        startActivity(i);



    }



    public void CheckisDataSavedOffline(){


        viewModel.callSavedFormsMethod();
        viewModel.getDataforInjection();
        viewModel.surveyformsResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s.equals("success")){

                    Toast.makeText(ActivityDashboard.this,s,Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(ActivityDashboard.this,s,Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewModel.citiesResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("success")){

                    Toast.makeText(ActivityDashboard.this,s,Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(ActivityDashboard.this,s,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void Logout(){


        viewModel.Logout();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setrecyclerAdapter();
        Log.d("TAG","restarted");


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder a = new AlertDialog.Builder(this);
        a.setTitle("Exit");
        a.setMessage("Do you wish to exit the App");
        a.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finishAffinity();
            }

        });

        a.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        a.show();
    }


    public void LoadData(){

        loadingDialog.ShowCustomLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (InternetUtils.isInternetConnected(getApplicationContext())) {
                    // Internet is available
                    // Do your internet-related tasks here
                    setrecyclerAdapter();
                    setCardsData();

                } else {

                    // No internet connection
                    // Display a message or handle the lack of internet connection
                    androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(ActivityDashboard.this);
                    dialog.setTitle("No internet Connection present at the moment");
                    dialog.setMessage("Do you wish to go to internet settings?");
                    dialog.setIcon(R.drawable.cowimage);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            LoadData();
                        }
                    });

                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finishAffinity();
                        }
                    });

                    dialog.show();
                    loadingDialog.dissmissDialog();
                }



            }
        },200);
    }
}



