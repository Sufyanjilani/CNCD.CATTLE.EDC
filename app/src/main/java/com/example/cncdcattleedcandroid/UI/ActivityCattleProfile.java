package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.databinding.ActivityCattleProfileBinding;
import com.github.mikephil.charting.charts.BarChart;
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

import java.util.ArrayList;
import java.util.List;

public class ActivityCattleProfile extends AppCompatActivity {

    BarChart chart;
    ActivityCattleProfileBinding cattleProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cattleProfileBinding = ActivityCattleProfileBinding.inflate(getLayoutInflater());
        setContentView(cattleProfileBinding.getRoot());
        setUpMPChart();
        CreateMPPicChart();
        setData(5,100);


    }

    public void setUpMPChart() {

        chart = cattleProfileBinding.piempchart2;
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
            data.setValueTextColor(ContextCompat.getColor(this, R.color.textcolor));
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.getAxisLeft().setTextColor(ContextCompat.getColor(this, R.color.textcolor));
            chart.getAxisRight().setTextColor(ContextCompat.getColor(this, R.color.textcolor));
            chart.getXAxis().setTextColor(ContextCompat.getColor(this, R.color.textcolor));
            chart.setData(data);

        }
    }

    public void CreateMPPicChart(){

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(200,"Buffalo"));
        visitors.add(new PieEntry(600,"Cow"));


        PieDataSet pieDataSet = new PieDataSet(visitors,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(R.color.textcolor);
        pieDataSet.setValueTextSize(16f);



        PieData pieData = new PieData(pieDataSet);
         cattleProfileBinding.piempchart1.setData(pieData);
         cattleProfileBinding.piempchart1.getDescription().setEnabled(false);
         cattleProfileBinding.piempchart1.setCenterText("Animals");
         cattleProfileBinding.piempchart1.setCenterTextSize(5f);
         cattleProfileBinding.piempchart1.setCenterTextColor(R.color.textcolor);
         cattleProfileBinding.piempchart1.setEntryLabelTextSize(8f);
         cattleProfileBinding.piempchart1.setEntryLabelColor(R.color.textcolor);
         cattleProfileBinding.piempchart1.animate();
    }
}