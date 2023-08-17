package com.example.cncdcattleedcandroid.ViewModels;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewSurveyViewModel extends ViewModel {

    private MutableLiveData<String> _formdata = new MutableLiveData<>();
    public MutableLiveData<String> formdata = _formdata;

    public MutableLiveData<Boolean> isResponseSuccess = new MutableLiveData<>();


    public RealmDatabaseHlper realmDatabaseHlper;

    public WebViewSurveyViewModel(){

        realmDatabaseHlper = new RealmDatabaseHlper();
    }


    public void getJsonFromAPi(String id){

        Call<JsonObject> getfromdataFromApi = new RetrofitClientSurvey().retrofitclient().getjsonApi(id);
        getfromdataFromApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){

                    JsonObject jsonObject = response.body();

                    JsonArray jsonArray = jsonObject.getAsJsonArray("data");

                    Log.d("TAG",jsonArray.toString());

                    JsonObject formdata = jsonArray.get(0).getAsJsonObject();

                    String getformjson = formdata.get("form_json").getAsString();

                    Log.d("fromjson",formdata.toString());

                    //Loadgetjavascript(getformjson);

                    Log.d("pagejson",getformjson.toString());

                    isResponseSuccess.setValue(true);

                    _formdata.setValue(getformjson);

                    //progressBarwebview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isResponseSuccess.setValue(false);

            }
        });
    }


}
