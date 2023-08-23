package com.example.cncdcattleedcandroid.ViewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {


    MutableLiveData<String> _isLoginSuccess = new MutableLiveData<>();
    public  MutableLiveData<String> isloginsucces = _isLoginSuccess;

    SessionManager sessionManager;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(getApplication().getApplicationContext());
    }

    public void Login(String username, String password,String appversion,String locationcoordinates){

        JsonObject loginObject = new JsonObject();
        loginObject.addProperty("username",username);
        loginObject.addProperty("password",password);
        loginObject.addProperty("appVersion",appversion);
        loginObject.addProperty("locationCoordinates",locationcoordinates);

        new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().login(loginObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()){

                    _isLoginSuccess.setValue("Login Successs");
                    JsonObject loginObject = response.body();

                    JsonObject data = loginObject.get("data").getAsJsonObject();
                    String accessToken  = data.get("accessToken").getAsString();
                    sessionManager.savebarearToken(accessToken);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                isloginsucces.setValue("Login Failure");
            }
        });

    }


}
