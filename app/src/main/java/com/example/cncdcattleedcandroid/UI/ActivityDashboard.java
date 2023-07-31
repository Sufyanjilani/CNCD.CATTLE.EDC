package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ActivityDashboard extends AppCompatActivity {

    ActivityDashboardBinding activityDashboardBinding;
    DrawerLayout mDrawer;

    ActionBarDrawerToggle drawerToggle;

    NavigationView navigationView;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        mDrawer = activityDashboardBinding.mydrawerLayout;
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);


        // Set a Toolbar to replace the ActionBar.


        // This will display an Up icon (<-), we will replace it with hamburger later

        // Find our drawer view


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);


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

    }

    public void selectDrawerItem(MenuItem menuItem) {


//        switch(menuItem.getItemId()) {
//            case R.id.drawersignout:
//                Toast.makeText(this,"Signout Button pressed",Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                Toast.makeText(this,"Signout Button pressed",Toast.LENGTH_SHORT).show();
//                break;
//
//        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    public void LoadPiceChart() {
//        pieChart.addPieSlice(
//                new PieModel(
//                        "R",
//                        Integer.parseInt(tvR.getText().toString()),
//                        Color.parseColor("#FFA726")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Python",
//                        Integer.parseInt(tvPython.getText().toString()),
//                        Color.parseColor("#66BB6A")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "C++",
//                        Integer.parseInt(tvCPP.getText().toString()),
//                        Color.parseColor("#EF5350")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Java",
//                        Integer.parseInt(tvJava.getText().toString()),
//                        Color.parseColor("#29B6F6")));
//    }

    }
}