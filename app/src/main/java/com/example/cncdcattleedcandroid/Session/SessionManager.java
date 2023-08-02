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


}




