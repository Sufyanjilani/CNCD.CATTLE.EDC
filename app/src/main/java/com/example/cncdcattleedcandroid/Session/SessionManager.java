package com.example.cncdcattleedcandroid.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class SessionManager {
    public SharedPreferences sharedPreferences;

    private Context context;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;



    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SessionData",context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setthemstate(boolean b){
        editor.putBoolean("isthemedark",b);
        editor.apply();
        editor.commit();

    }

    public  void savestartlocation(String latitude,String longitude ){
        editor.putString("startlatitude",latitude);
        editor.putString("startlongitude",longitude);
        editor.apply();
        editor.commit();
    }
    public void savebarearToken(String baerertoken){
        editor.putString("bearer_token",baerertoken);
        editor.apply();
        editor.commit();
    }
    public boolean getthemstate(){

        return  sharedPreferences.getBoolean("isthemedark",false);
    }
    public boolean getstartlocation(){
        return sharedPreferences.getBoolean("latitude",false);
    }
    public boolean getlongitude(){
        return  sharedPreferences.getBoolean("longitude",false);
    }
    public boolean getbearer(){
        return sharedPreferences.getBoolean("barriertoken",false);
    }
    public void isdarkThemeEnabled(Boolean enabled){


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.d("tag","dark");
    }


    public void SavePrimarykey(int primarykey){

        editor.putInt("primarykey",primarykey);
        editor.commit();
    }

    public Integer getPrimaryKey(){

        return sharedPreferences.getInt("primarykey",0);
    }

    public void ApplicationFirstTime(){

        editor.putBoolean("installedFirstTime",false);
        editor.apply();
        editor.commit();
    }

//    public void saveStartedFormData(
//            String formname,
//            String formid,
//            String formstartime,
//            String coordinate_start_lat,
//            String coordinate_start_lon
//    ){
//
//        editor.putString("formName",formname);
//        editor.putString("formid",formid);
//        editor.putString("survey_start_time",formstartime);
//        editor.putString("coordinate_start_lat",coordinate_start_lat);
//        editor.putString("coordinate_start_lon",coordinate_start_lon);
//        editor.apply();
//        editor.commit();
//
//    }


    public void saveStartCoordinates(double startcoordinates_lat, double endcoordinates_lon){

        editor.putString("start_coordinates_lat",String.valueOf(startcoordinates_lat));
        editor.putString("end_coordinates_lon",String.valueOf(endcoordinates_lon));
        editor.apply();
        editor.commit();
    }
    public Boolean checkisApplicationFirstTime(){
        return sharedPreferences.getBoolean("installedFirstTime",true);
    }

}




