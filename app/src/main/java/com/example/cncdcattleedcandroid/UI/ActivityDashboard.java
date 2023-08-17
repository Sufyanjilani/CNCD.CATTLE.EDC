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
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Session.SessionManager;
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
import com.orhanobut.logger.Logger;
import com.smb.animatedtextview.AnimatedTextView;

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

    BarChart chart;

    SessionManager sessionManager;


    RealmDatabaseHlper databaseHlper;

    DashboardViewModel viewModel;





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
        LoadPieChart1();
        LoadPieChart2();
        LoadPieChart3();
        setUpMPChart();
        setData(5, 100);
        CreateMPPicChart();
        AnimateTextView();
        CheckisDataSavedOffline();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                setrecyclerAdapter();
            }
        },200);




                setAnimation2();


                setAnimation3();


                viewModel  = new ViewModelProvider(this).get(DashboardViewModel.class);


        // Set a Toolbar to replace the ActionBar.


        // This will display an Up icon (<-), we will replace it with hamburger later

        // Find our drawer view


        activityDashboardBinding.btnaddFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityDashboard.this, ActivityFarmerProfile.class));

            }
        });


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

        if (menuItem.getItemId() == R.id.drawersignout) {

            Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show();

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

    public void LoadPieChart1() {

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

    public void LoadPieChart2() {
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

    public void LoadPieChart3() {
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


    public void InitializeHeader(NavigationView navigationView) {

        View headerView = navigationView.getHeaderView(0);
        TextView appversiontextview = headerView.findViewById(R.id.textViewVersion);


    }


//    public void LoadBubbleChart() {
//
//        Scatter bubble = AnyChart.bubble();
//
//        bubble.animation(true);
//
//        bubble.title().enabled(true);
//        bubble.title().useHtml(true);
//        bubble.title()
//                .padding(0d, 0d, 10d, 0d);
//
//        bubble.padding(20d, 20d, 10d, 20d);
//
//        bubble.yGrid(true)
//                .xGrid(true)
//                .xMinorGrid(true)
//                .yMinorGrid(true);
//
//        bubble.minBubbleSize(5d)
//                .maxBubbleSize(40d);
//
//        bubble.xAxis(0)
//                .title("Average pulse during training")
//                .minorTicks(true);
//        bubble.yAxis(0)
//                .title("Average power")
//                .minorTicks(true);
//
//        bubble.legend().enabled(true);
//        bubble.labels().padding(0d, 0d, 10d, 0d);
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new CustomBubbleDataEntry(1, 184, 113, "10/13/2014", 20));
//
//
//        bubble.tooltip()
//                .useHtml(true)
//                .fontColor("#fff")
//                .format("function() {\n" +
//                        "        return this.getData('data') + '<br/>' +\n" +
//                        "          'Power: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
//                        "          this.getData('value') + '</span></strong><br/>' +\n" +
//                        "          'Pulse: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
//                        "          this.getData('x') + '</span></strong><br/>' +\n" +
//                        "          'Duration: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
//                        "          this.getData('size') + ' min.</span></strong>';\n" +
//                        "      }");
//        activityDashboardBinding.anychartView.setChart(bubble);
//    }


    private class CustomBubbleDataEntry extends BubbleDataEntry {

        CustomBubbleDataEntry(Integer training, Integer x, Integer value, String data, Integer size) {
            super(x, value, size);
            setValue("training", training);
            setValue("data", data);
        }
    }

//    public void AddPolarCHart() {
//
//
//        Polar polar = AnyChart.polar();
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new CustomDataEntry("Nail polish", 12814, 4376, 4229));
//        data.add(new CustomDataEntry("Eyebrow pencil", 13012, 3987, 3932));
//        data.add(new CustomDataEntry("Rouge", 11624, 3574, 5221));
//        data.add(new CustomDataEntry("Pomade", 8814, 4376, 9256));
//        data.add(new CustomDataEntry("Eyeshadows", 12998, 4572, 3308));
//        data.add(new CustomDataEntry("Eyeliner", 12321, 3417, 5432));
//        data.add(new CustomDataEntry("Foundation", 10342, 5231, 13701));
//        data.add(new CustomDataEntry("Lip gloss", 22998, 4572, 4008));
//        data.add(new CustomDataEntry("Mascara", 11261, 6134, 18712));
//        data.add(new CustomDataEntry("Powder", 10261, 5134, 25712));
//
//        Set set = Set.instantiate();
//        set.data(data);
//        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");
//        Mapping series3Data = set.mapAs("{ x: 'x', value: 'value3' }");
//
//        polar.column(series1Data);
//
//        polar.column(series2Data);
//
//        polar.column(series3Data);
//
//        polar.title("Company Profit Dynamic in Regions by Year");
//
//        polar.sortPointsByX(true)
//                .defaultSeriesType(PolarSeriesType.COLUMN)
//                .yAxis(false)
//                .xScale(ScaleTypes.ORDINAL);
//
//        polar.title().margin().bottom(20d);
//
//
//        polar.tooltip()
//                .valuePrefix("$")
//                .displayMode(TooltipDisplayMode.UNION);
//
//        activityDashboardBinding.anychartView.setChart(polar);
//    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }

    public void setUpMPChart() {

        chart = activityDashboardBinding.mpchart;
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setTextColor(ContextCompat.getColor(this,R.color.textcolor));
    }

    private void setData(int count, float range) {
        float start = 1f;
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));
            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.logout)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }
        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Cattles");
            set1.setDrawIcons(false);
            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
            List<GradientColor> gradientFills = new ArrayList<>();
            gradientFills.add(new GradientColor(startColor1, endColor1));
            gradientFills.add(new GradientColor(startColor2, endColor2));
            gradientFills.add(new GradientColor(startColor3, endColor3));
            gradientFills.add(new GradientColor(startColor4, endColor4));
            gradientFills.add(new GradientColor(startColor5, endColor5));
            set1.setGradientColors(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextColor(ContextCompat.getColor(this,R.color.textcolor));
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.getAxisLeft().setTextColor(ContextCompat.getColor(this,R.color.textcolor));
            chart.getAxisRight().setTextColor(ContextCompat.getColor(this,R.color.textcolor));
            chart.getXAxis().setTextColor(ContextCompat.getColor(this,R.color.textcolor));
            chart.setData(data);

        }


    }

    public void CreateMPPicChart(){

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(200,"Buffalo"));
        visitors.add(new PieEntry(600,"Cow"));


        PieDataSet pieDataSet = new PieDataSet(visitors,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(ContextCompat.getColor(this, R.color.textcolor));
        pieDataSet.setValueTextSize(16f);


        PieData pieData = new PieData(pieDataSet);
        activityDashboardBinding.piempchart.setData(pieData);
        activityDashboardBinding.piempchart.invalidate();
        activityDashboardBinding.piempchart.getDescription().setEnabled(false);
        activityDashboardBinding.piempchart.setCenterTextSize(5f);
        activityDashboardBinding.piempchart.setCenterText("Cattles");
        activityDashboardBinding.piempchart.setHoleRadius(0);
        activityDashboardBinding.piempchart.setCenterTextColor(R.color.textcolor);
        activityDashboardBinding.piempchart.setEntryLabelTextSize(8f);
        activityDashboardBinding.piempchart.setEntryLabelColor(R.color.textcolor);
        activityDashboardBinding.piempchart.setHoleColor(R.color.wholecolor);
        activityDashboardBinding.piempchart.animate();
        activityDashboardBinding.piempchart.setEntryLabelColor(ContextCompat.getColor(this, R.color.textcolor));
        activityDashboardBinding.piempchart.getLegend().setTextColor(ContextCompat.getColor(this, R.color.textcolor));

        //activityDashboardBinding.piempchart.graph.getPaint(activityDashboardBinding.piempchart.PAINT_VALUES).setColor(Color.BLUE);



    }


    public void AnimateTextView(){





        activityDashboardBinding.animtext.setText("1000");
        activityDashboardBinding.animtext.addOnAnimationListener(new AnimatedTextView.AnimationListener() {
            @Override
            public void onAnimationStart(@NonNull String s, @NonNull String s1) {

            }

            @Override
            public void onAnimationEnd(@NonNull String s, @NonNull String s1) {

            }
        });

        activityDashboardBinding.animtext.animateTo("1");
        activityDashboardBinding.animtext.getBareText();
    }


    public void setrecyclerAdapter(){

        ArrayList<Cattles> cattleslist = new ArrayList<>();
        cattleslist.add(new Cattles("Cow","2"));
        cattleslist.add(new Cattles("Goat","3"));
        cattleslist.add(new Cattles("Buffalo","4"));
        CattleAdapter cattleAdapter = new CattleAdapter(cattleslist);
        activityDashboardBinding.recycler.setAdapter(cattleAdapter);

        int resId = R.anim.slide_up_anim_layout;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        activityDashboardBinding.recycler.setLayoutAnimation(animation);
        cattleAdapter.notifyDataSetChanged();
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


//                Intent intent = new Intent(this, .class);
//                View sharedView = findViewById(R.id.profileImagedashboard); // The shared view
//                String transitionName = getString(R.string.transition_name); // The transition name, make sure it's the same in both activities
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, transitionName);
//                startActivity(intent, options.toBundle());
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
        i.putExtra("formID","6");
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
}


