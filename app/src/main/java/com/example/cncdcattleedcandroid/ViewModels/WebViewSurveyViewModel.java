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
import com.example.cncdcattleedcandroid.OfflineDb.Models.FarmerSurveyModel;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.ActivityEntity;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewSurveyViewModel extends AndroidViewModel {

    private MutableLiveData<String> _formdata = new MutableLiveData<>();
    public MutableLiveData<String> formdata = _formdata;



    private MutableLiveData<Boolean> _isfirstformSent = new MutableLiveData<>();
    public MutableLiveData<Boolean> isfirstformSent = _isfirstformSent;

    public MutableLiveData<String> formMsg = new MutableLiveData<>();


    private MutableLiveData<Boolean> _is_secondformSent = new MutableLiveData<>();
    public MutableLiveData<Boolean> is_secondformSent = _is_secondformSent;


    private MutableLiveData<Boolean> _isthirdformSent = new MutableLiveData<>();
    public MutableLiveData<Boolean> isthirdformSent = _isthirdformSent;

    private MutableLiveData<String> _updatedentity_ID = new MutableLiveData<>();
    public MutableLiveData<String> updatedentity_ID = _updatedentity_ID;


    public MutableLiveData<Boolean> isthirdCattleformSubmitted = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> isFourthCattleformSubmitted = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> isFifthCattleformSubmitted = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> isMedicalEntitySubmitted = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> isDietEntitySubmitted = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> isMilkWeightSubmitted = new MutableLiveData<Boolean>();


    public MutableLiveData<Boolean> isRequestSuccess = new MutableLiveData<>();

    public MutableLiveData<String> isformJson = new MutableLiveData<>();

    public MutableLiveData<String> isCattelformJson = new MutableLiveData<>();

    public MutableLiveData<String> isCattelDietformJson = new MutableLiveData<>();

    public MutableLiveData<String> isCattelMedicalformJson = new MutableLiveData<>();

    public MutableLiveData<String> isCattelTraitsformJson = new MutableLiveData<>();

    public MutableLiveData<String> personalBasicFormComplete = new MutableLiveData<>();

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

    public void PostFarmerMedicalFormData(
            Context context,
            String questionnaireID,
            String farm_ID,
            String farmer_ID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd
    ){

        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");

        Log.d("farmerId",farmer_ID);
        Log.d("farmId",farm_ID);

        payloadObject.addProperty("farmID",farm_ID);
        payloadObject.addProperty("farmerID",farmer_ID);
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().addEntity(

                farm_ID,
                farmer_ID,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());


                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);
                    isMedicalEntitySubmitted.setValue(true);


                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                    isMedicalEntitySubmitted.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
                isMedicalEntitySubmitted.setValue(false);
            }
        });
    }

    public void PostFarmerDietFormData(
            Context context,
            String questionnaireID,
            String farm_ID,
            String farmer_ID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd
    ){

        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");

        Log.d("farmerId",farmer_ID);
        Log.d("farmId",farm_ID);

        payloadObject.addProperty("farmID",farm_ID);
        payloadObject.addProperty("farmerID",farmer_ID);
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().addEntity(

                farm_ID,
                farmer_ID,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());


                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);
                    isDietEntitySubmitted.setValue(true);
                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                    isDietEntitySubmitted.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
                isDietEntitySubmitted.setValue(false);
            }
        });
    }

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

                JsonObject object = response.body();
                JsonObject data = object.get("data").getAsJsonObject();
                String farm_id = data.get("farm_id").getAsString();
                String farmer_id = data.get("farmer_id").getAsString();
                sessionManager.Save_Farm_and_Farmer_ID(farmer_id,farm_id);


                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);
                    isRequestSuccess.setValue(true);

                }else {
                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                } _isfirstformSent.setValue(true);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _isfirstformSent.setValue(false);
                isRequestSuccess.setValue(false);
            }
        });


    }

    public void SubmitMilkWeight(
            Context context,
            String questionnaireID,
            String cattleId,
            String farm_ID,
            String farmer_ID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String interviewtakenAt,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd
    ){
        JsonObject payloadObject = new JsonObject();
        payloadObject.addProperty("cattleId",cattleId);
        payloadObject.addProperty("farmID",farm_ID);
        payloadObject.addProperty("farmerID",farmer_ID);
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().insertCattleEntities(

                cattleId,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());


                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);
                    isMilkWeightSubmitted.setValue(true);

                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                    isMilkWeightSubmitted.setValue(false);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
                isMilkWeightSubmitted.setValue(false);
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
            String locationCoordinatesEnd,
            String entity

    ) {


        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");

        Log.d("farmerId",farmer_ID);
        Log.d("farmId",farm_ID);

        payloadObject.addProperty("farmID",farm_ID);
        payloadObject.addProperty("farmerID",farmer_ID);
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);





        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().addEntity(

                farm_ID,
                farmer_ID,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                _is_secondformSent.setValue(true);

                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);

                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
             //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
            }
        });


    }

    public void SubmitThirdForm(

            Context context,
            String questionnaireID,
            String farmer_ID,
            String farm_ID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String accessToken,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd,
            String entity

    ) {


        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");

        JsonObject parsedjson = (JsonObject) new JsonParser().parse(formJSON);
        payloadObject.addProperty("farmID",farm_ID);
        payloadObject.addProperty("farmerID",farmer_ID);
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





        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().addEntity(

                farm_ID,
                farmer_ID,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                _isthirdformSent.setValue(true);

                if (!response.body().get("error").getAsString().equals("msg")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);

                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _isthirdformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
            }
        });


    }


    public  void SubmitThirdFormDataMultipart(

           MultipartBody.Part image,
            Context context,
            String questionnaireID,
            String cattleId,
            String farmId,
            String farmerId,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String accessToken,
            String interviewtakenAt ,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd

    ){



        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("formdata",formJSON)
                .build();

        JsonObject payloadObject = new JsonObject();
        JsonObject parsedjson = (JsonObject) new JsonParser().parse(formJSON);
        Log.d("TAG","called");
        payloadObject.addProperty("farmID",farmId);
        payloadObject.addProperty("farmerID",farmerId);
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


        Call<JsonObject> call = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().
                getFarmerDataMutlipart(cattleId,requestBody,image);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()){

                    Log.d(constants.Tag,response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });






    }


    public void PostFirstCattleFormData(

            Context context,
            String questionnaireID,
            String farmId,
            String farmerId,
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
        payloadObject.addProperty("farmID",farmId);
        payloadObject.addProperty("farmerID",farmerId);
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


        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().insertCattleGeneral(payloadObject);
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag, response.body().toString());
                JsonObject object = response.body();

                if (!object.get("error").getAsString().equals("true")) {

                    if (object.get("data").isJsonObject()) {
                        JsonObject data = object.get("data").getAsJsonObject();
                        String cattleID = data.get("cattle_id").getAsString();

                        sessionManager.SaveCattleID(cattleID);
                        _isfirstformSent.setValue(true);



                        if (!response.body().get("msg").getAsString().equals("")) {


                            String message = response.body().get("msg").getAsString();
                            formMsg.setValue(message);

                        }
                    } else {
                        String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                        formMsg.setValue(msg);

                    }

                }
                else{

                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _isfirstformSent.setValue(false);
            }
        });


    }



    public void SubmitSecondFormCattle(

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


        String cattleId = sessionManager.getCattleId();
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);





        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().insertCattleEntities(

                cattleId,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                _is_secondformSent.setValue(true);

                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);

                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
            }
        });


    }

    public void SubmitThirdFormCattle(

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


        String cattleId = sessionManager.getCattleId();
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);





        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().insertCattleEntities(

                cattleId,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());
                isthirdCattleformSubmitted.setValue(true);

                if (!response.body().get("error").getAsString().equals("true")) {


                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);

                }
                else{

                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
                isthirdCattleformSubmitted.setValue(false);
            }
        });


    }


    public void SubmitFourthFormCattle(

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


        String cattleId = sessionManager.getCattleId();
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);


        Log.d("TAG",payloadObject.toString());

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().insertCattleEntities(

                cattleId,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());


                if (!response.body().get("error").getAsString().equals("true")) {
                    isFourthCattleformSubmitted.setValue(true);

                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);

                }
                else{
                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
                isFourthCattleformSubmitted.setValue(false);
            }
        });


    }

    public void SubmitFifthFormCattle(

            Context context,
            String questionnaireID,
            String appVersion,
            String locationCoordinate,
            String formJSON,
            String accessToken,
            String interviewtakenAt,
            String interviewTimeStart,
            String interviewTimeEnd,
            String locationCoordinatesStart,
            String locationCoordinatesEnd


    ){
        JsonObject payloadObject = new JsonObject();


        Log.d("TAG","called");


        String cattleId = sessionManager.getCattleId();
        payloadObject.addProperty("questionnaireID",questionnaireID);
        payloadObject.addProperty("appVersion",appVersion);
        payloadObject.addProperty("locationCoordinates",locationCoordinate);
        payloadObject.addProperty("formJSON",formJSON);
        payloadObject.addProperty("accessToken",accessToken);
        payloadObject.addProperty("interviewTakenAt",interviewtakenAt);
        payloadObject.addProperty("interviewTimeStart",interviewTimeStart);
        payloadObject.addProperty("interviewTimeEnd",interviewTimeEnd);
        payloadObject.addProperty("locationCoordinatesStart",locationCoordinatesStart);
        payloadObject.addProperty("locationCoordinatesEnd",locationCoordinatesEnd);


        Log.d("TAG",payloadObject.toString());

        Call<JsonObject> apicall = new RetrofitClientSurvey(context).retrofitclient().insertCattleEntities(

                cattleId,
                payloadObject

        );
        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(constants.Tag,response.body().toString());


                if (!response.body().get("error").getAsString().equals("true")) {
                    isFifthCattleformSubmitted.setValue(true);

                    String message = response.body().get("msg").getAsString();
                    formMsg.setValue(message);

                }
                else{
                    String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                    formMsg.setValue(msg);


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //   _is_secondformSent.setValue(false);
                Log.d(constants.Tag,t.getMessage());
                isFifthCattleformSubmitted.setValue(false);
            }
        });
    }

//    public void insertFarmerForm(
//            String id,
//            String name,
//            String formPages,
//            String imagesflag
//    ){
//
//        realm = Realm.getDefaultInstance();
//
//        FarmerSurveyModel farmerSurveyModel = new FarmerSurveyModel(id, name, "type", formPages,imagesflag);
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.insertOrUpdate(farmerSurveyModel);
//            }
//        });
//
//    }


    public void loadCattelEntityData(String cattleId){
        Call<JsonObject> getData = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getCattleProfile(cattleId);
        getData.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (!response.body().get("error").getAsString().equals("true")){
                        JsonObject reposeObject = response.body();
                        JsonObject dataObject = reposeObject.get("data").getAsJsonObject();
                        JsonObject cattleEntities = dataObject.get("cattleEntities").getAsJsonObject();
                        JsonObject personal_milk = cattleEntities.get("personal_milk").getAsJsonObject();
                        if (personal_milk.size() == 0){
                            isCattelformJson.setValue(String.valueOf(false));
                        }else {
                            JsonObject formJson = personal_milk.get("formJSON").getAsJsonObject();
                            isCattelformJson.setValue(formJson.toString());
                        }


                        JsonObject personal_medical = cattleEntities.get("personal_medical").getAsJsonObject();
                        if (personal_medical.size() == 0){
                            isCattelMedicalformJson.setValue(String.valueOf(false));
                        }else {
                            JsonObject personal_medicalformJson = personal_medical.get("formJSON").getAsJsonObject();
                            isCattelMedicalformJson.setValue(personal_medicalformJson.toString());
                        }


                        JsonObject personal_traits = cattleEntities.get("personal_traits").getAsJsonObject();
                        if (personal_traits.size() == 0){
                            isCattelTraitsformJson.setValue(String.valueOf(false));
                        }else {
                            JsonObject personal_traitsformJson = personal_traits.get("formJSON").getAsJsonObject();
                            isCattelTraitsformJson.setValue(personal_traitsformJson.toString());
                        }


                        JsonObject personal_diet = cattleEntities.get("personal_diet").getAsJsonObject();
                        if (personal_diet.size() == 0){
                            isCattelDietformJson.setValue(String.valueOf(false));
                        }else {
                            JsonObject personal_dietformJson = personal_diet.get("formJSON").getAsJsonObject();
                            isCattelDietformJson.setValue(personal_dietformJson.toString());
                        }


                    }else {
                        String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                        isCattelformJson.setValue(msg);
                        isCattelMedicalformJson.setValue(msg);
                        isCattelTraitsformJson.setValue(msg);
                        isCattelDietformJson.setValue(msg);

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(constants.Tag, t.getMessage().toString());
            }
        });
    }
    public void loadEntityData(String farmId, String farmerId, String entityID){
        Call<JsonObject> getData = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getEntityData(farmId,farmerId,entityID);
        getData.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (!response.body().get("error").getAsString().equals("true")){
                        JsonObject responseObject = response.body();
                        JsonObject dataObject = responseObject.get("data").getAsJsonObject();
                        JsonObject formJson = dataObject.get("formJSON").getAsJsonObject();
                        isformJson.setValue(formJson.toString());
                    }else{
                        String msg = response.body().get("msg") == null ? "null": response.body().get("msg").getAsString();
                        isformJson.setValue(msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(constants.Tag, t.getMessage().toString());
            }
        });

    }


}
