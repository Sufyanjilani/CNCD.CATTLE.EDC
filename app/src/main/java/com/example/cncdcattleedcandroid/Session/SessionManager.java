package com.example.cncdcattleedcandroid.Session;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class SessionManager {

    Context ctx;



    SessionManager(Context context){

        this.ctx = context;
    }

    public void isdarkThemeEnabled(Boolean enabled){


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.d("tag","dark");
    }
}
