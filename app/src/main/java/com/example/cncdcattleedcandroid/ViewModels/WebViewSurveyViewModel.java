package com.example.cncdcattleedcandroid.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewSurveyViewModel extends AndroidViewModel {

    private MutableLiveData<String> _formdata = new MutableLiveData<>();
    public MutableLiveData<String> formdata = _formdata;



    private MutableLiveData<Boolean> _isfirstformSent = new MutableLiveData<>();
    public MutableLiveData<Boolean> isfirstformSent = _isfirstformSent;


    private MutableLiveData<Boolean> _is_secondformSent = new MutableLiveData<>();
    public MutableLiveData<Boolean> is_secondformSent = _is_secondformSent;


    private MutableLiveData<Boolean> _isthirdformSent = new MutableLiveData<>();
    public MutableLiveData<Boolean> isthirdformSent = _isthirdformSent;

    private MutableLiveData<String> _updatedentity_ID = new MutableLiveData<>();
    public MutableLiveData<String> updatedentity_ID = _updatedentity_ID;



    SessionManager sessionManager;


    public RealmDatabaseHlper realmDatabaseHlper;





    Constants constants;

    public WebViewSurveyViewModel(@NonNull Application application) {
        super(application);
        realmDatabaseHlper = new RealmDatabaseHlper();
        sessionManager = new SessionManager(getApplication().getApplicationContext());
        constants = new Constants();
    }


//    public void getJsonFromAPi(String id){
//
//        Call<JsonObject> getfromdataFromApi = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getjsonApi(id);
//        getfromdataFromApi.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()){
//
//                    JsonObject jsonObject = response.body();
//
//                    JsonArray jsonArray = jsonObject.getAsJsonArray("data");
//
//                    Log.d("TAG",jsonArray.toString());
//
//                    JsonObject formdata = jsonArray.get(0).getAsJsonObject();
//
//                    String getformjson = formdata.get("form_json").getAsString();
//
//                    Log.d("fromjson",formdata.toString());
//
//                    //Loadgetjavascript(getformjson);
//
//                    Log.d("pagejson",getformjson.toString());
//
//                    isResponseSuccess.setValue(true);
//
//                    _formdata.setValue(getformjson);
//
//                    //progressBarwebview.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                isResponseSuccess.setValue(false);
//
//            }
//        });
//    }

    public void PostFirstFormData(

            Context context,
            String questionnaireID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String accessToken,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd

    ) {


        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");


        JsonObject parsedjson = (JsonObject) new JsonParser().parse(formJSON);
        JsonObject firstformObject = new JsonObject();
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",parsedjson.toString());
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);


        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().Addfarmer(payloadObject);
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                _isfirstformSent.setValue(true);
                sessionManager.Save_Farm_and_Farmer_ID("","");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _isfirstformSent.setValue(false);
            }
        });


    }


    public void SubmitSecondForm(

            Context context,
            String questionnaireID,
            String farm_ID,
            String farmer_ID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String accessToken,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd

    ) {


        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");



        JsonObject firstformObject = new JsonObject();
        payloadObject.addProperty("farmID",farm_ID);
        payloadObject.addProperty("farmerID",farmer_ID);
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinate",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);

        firstformObject.add("payload",payloadObject);

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().Addfarmer(firstformObject);
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                _is_secondformSent.setValue(true);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
            }
        });


    }

    public void SubmitThirdForm(

            Context context,
            String questionnaireID,
            String farmer_ID,
            String farm_iD,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String accessToken,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd

    ) {


        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");



        JsonObject firstformObject = new JsonObject();
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("farmID",farmer_ID);
        payloadObject.addProperty("farmerID",farm_iD);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinate",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);

        firstformObject.add("payload",payloadObject);

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().Addfarmer(firstformObject);
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                _isthirdformSent.setValue(true);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _isthirdformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
            }
        });


    }






}
