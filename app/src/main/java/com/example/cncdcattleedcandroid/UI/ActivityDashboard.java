package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.logger.Logger;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ActivityDashboard extends AppCompatActivity {

    ActivityDashboardBinding activityDashboardBinding;
    DrawerLayout mDrawer;

    ActionBarDrawerToggle drawerToggle;

    NavigationView navigationView;

    Toolbar toolbar;
    private static final int REQUEST_PERMISSIONS_CODE = 123;





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
        checkAndRequestPermissions();
        LoadPieChart1();
        LoadPieChart2();
        LoadPieChart3();




        // Set a Toolbar to replace the ActionBar.


        // This will display an Up icon (<-), we will replace it with hamburger later

        // Find our drawer view


        activityDashboardBinding.addFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityDashboard.this,ActivityFarmerProfile.class));

            }
        });



        drawerToggle = setupDrawerToggle();



        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        setupDrawerContent(navigationView);



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

    }

    public void selectDrawerItem(MenuItem menuItem) {

//
//        switch(menuItem.getItemId()) {
//            case R.id.drawersignout:
//                Toast.makeText(this,"Signout Button pressed",Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                Toast.makeText(this,"Signout Button pressed",Toast.LENGTH_SHORT).show();
//                break;
//
//        }

        if(menuItem.getItemId() == R.id.drawersignout){

            Toast.makeText(this,"Signing out",Toast.LENGTH_SHORT).show();

        }





        //setTitle(menuItem.getTitle());
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
        Log.d("tag","dark");


    }

    private void setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Log.d("tag","Light");


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

    public void LoadPieChart1(){

        activityDashboardBinding.piechart.addPieSlice(
                new PieModel(
                        "R",
                        10,
                        Color.parseColor("#FFA726")));
        activityDashboardBinding.piechart.addPieSlice(
                new PieModel(
                        "Python",
                        15,
                        Color.parseColor("#66BB6A")));
        activityDashboardBinding.piechart.addPieSlice(
                new PieModel(
                        "C++",
                        25,
                        Color.parseColor("#EF5350")));
        activityDashboardBinding.piechart.addPieSlice(
                new PieModel(
                        "Java",
                        35,
                        Color.parseColor("#29B6F6")));
    }

    public void LoadPieChart2(){
        activityDashboardBinding.piechart2.addPieSlice(
                new PieModel(
                        "R",
                        10,
                        Color.parseColor("#FFA726")));
        activityDashboardBinding.piechart2.addPieSlice(
                new PieModel(
                        "Python",
                        15,
                        Color.parseColor("#66BB6A")));
        activityDashboardBinding.piechart2.addPieSlice(
                new PieModel(
                        "C++",
                        25,
                        Color.parseColor("#EF5350")));
        activityDashboardBinding.piechart2.addPieSlice(
                new PieModel(
                        "Java",
                        35,
                        Color.parseColor("#29B6F6")));

    }

    public void LoadPieChart3(){
        activityDashboardBinding.piechart3.addPieSlice(
                new PieModel(
                        "R",
                        10,
                        Color.parseColor("#FFA726")));
        activityDashboardBinding.piechart3.addPieSlice(
                new PieModel(
                        "Python",
                        15,
                        Color.parseColor("#66BB6A")));
        activityDashboardBinding.piechart3.addPieSlice(
                new PieModel(
                        "C++",
                        25,
                        Color.parseColor("#EF5350")));
        activityDashboardBinding.piechart3.addPieSlice(
                new PieModel(
                        "Java",
                        35,
                        Color.parseColor("#29B6F6")));

    }


    public void InitializeHeader(NavigationView navigationView){

        View headerView = navigationView.getHeaderView(0);
        TextView appversiontextview = headerView.findViewById(R.id.textViewVersion);


    }


}