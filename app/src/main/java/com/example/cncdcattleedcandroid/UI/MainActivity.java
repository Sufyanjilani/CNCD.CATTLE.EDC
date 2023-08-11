package com.example.cncdcattleedcandroid.UI;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Realm.RealDBhelper;
import com.example.cncdcattleedcandroid.Realm.TestModel;
import com.example.cncdcattleedcandroid.databinding.ActivityMainBinding;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;



public class MainActivity extends AppCompatActivity {


    ActivityMainBinding activityMainBinding;
    Realm realm;

    RealDBhelper realDBhelper;

    ArrayList<TestModel> arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        arrayList = new ArrayList<>();

        realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("default.realm")
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();
        realDBhelper = new RealDBhelper();

        activityMainBinding.btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                realDBhelper.Insert(
                        activityMainBinding.farmerName.getText().toString(),
                        activityMainBinding.cattleName.getText().toString(),
                        "base64"
                        );
            }
        });


        activityMainBinding.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realDBhelper.Delete(activityMainBinding.farmerName.getText().toString());
            }
        });


        activityMainBinding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realDBhelper.Update(activityMainBinding.farmerName.getText().toString());
            }
        });


        activityMainBinding.btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrayList = realDBhelper.readTask();

                Log.d("TAG",arrayList.get(0).toString());

            }
        });

    }


}