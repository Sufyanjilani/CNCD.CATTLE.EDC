package com.example.cncdcattleedcandroid.ViewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.airbnb.lottie.L;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.ActivityFarmerProfile;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {


    MutableLiveData<String> _isLoginSuccess = new MutableLiveData<>();
    public  MutableLiveData<String> isloginsucces = _isLoginSuccess;

    SessionManager sessionManager;

    RealmDatabaseHlper realmDatabaseHlper;




    public LoginViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(getApplication().getApplicationContext());
        realmDatabaseHlper = new RealmDatabaseHlper();

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


                        if (!response.body().get("error").getAsString().equals("true")) {

                            _isLoginSuccess.setValue("Login Successes");
                            JsonObject loginObject = response.body();

                            JsonObject data = loginObject.get("data").getAsJsonObject();
                            String accessToken = data.get("accessToken").getAsString();
                            sessionManager.savebarearToken(accessToken);


                            JsonObject config = data.get("config").getAsJsonObject();
                            JsonObject formconfig = config.get("formConfig").getAsJsonObject();

                            String general_basic = formconfig.get("general_basic").getAsString();

                            String general_diet = formconfig.get("general_diet").getAsString();

                            String general_medical = formconfig.get("general_medical").getAsString();

                            String personal_basic = formconfig.get("personal_basic").getAsString();

                            String personal_milk = formconfig.get("personal_milk").getAsString();

                            String personal_medical = formconfig.get("personal_medical").getAsString();

                            String personal_traits = formconfig.get("personal_traits").getAsString();

                            String personal_mik_weight = formconfig.get("personal_mik_weight").getAsString();

                            String personal_diet = formconfig.get("personal_diet").getAsString();


                            realmDatabaseHlper.InsertEntities(
                                    general_basic,
                                    general_diet,
                                    general_medical,
                                    personal_basic,
                                    personal_milk,
                                    personal_medical,
                                    personal_traits,
                                    personal_mik_weight,
                                    personal_diet
                            );

                            Log.d("Log form", general_diet);


                            String userId = data.get("userID").getAsString();
                            JsonObject userProfile = data.get("userProfile").getAsJsonObject();
                            String name = userProfile.get("name").getAsString();
                            String email = userProfile.get("email").getAsString();
                            String type = userProfile.get("type").getAsString();

                            sessionManager.SaveUserData(
                                    name,
                                    email,
                                    type,
                                    userId
                            );

                        }
                        else{
                            String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
//                            Toast.makeText(ActivityFarmerProfile.this,msg,Toast.LENGTH_SHORT).show();
                            isloginsucces.setValue(msg);
//                            isloginsucces.setValue(response.body().get("msg").getAsString());
                        }
                    }

//


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                isloginsucces.setValue(t.getMessage());
                Log.d("TAG",t.getMessage().toString());

            }
        });

    }


}
