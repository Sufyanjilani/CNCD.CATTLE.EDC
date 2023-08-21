package com.example.cncdcattleedcandroid.ViewModels;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.Realm.RealDBhelper;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {


    Constants constants;
    RealmDatabaseHlper realDBhelper;

    public MutableLiveData<String> citiesResponse = new MutableLiveData<>();
    public MutableLiveData<String> surveyformsResponse = new MutableLiveData<>();


    public DashboardViewModel(){


        constants = new Constants();
        realDBhelper  = new RealmDatabaseHlper();


    }


    public void getDataforInjection() {

        Call<JsonObject> apireader = new RetrofitClientSurvey().retrofitclient().getcities();
        apireader.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    JsonObject citiesObject = response.body();
                    Log.d(constants.Tag, citiesObject.toString());

                    JsonArray data = citiesObject.get("data").getAsJsonArray();

                    JsonObject obj1 = data.get(0).getAsJsonObject();
                    String countryName_en = obj1.get("countryName_en").getAsString();
                    String countryName_ur = obj1.get("countryName_ur").getAsString();
                    String countryInitials = obj1.get("countryInitials").getAsString();
                    String countrycode = obj1.get("countryCode").toString();
                    JsonArray provincesAndCities = obj1.get("provincesAndCities").getAsJsonArray();
                    JsonObject  statesAndCitiesObject1 = provincesAndCities.get(0).getAsJsonObject();
                    String provinceName_en = statesAndCitiesObject1.get("provinceName_en").getAsString();
                    String provinceeName_ur = statesAndCitiesObject1.get("provinceeName_ur").getAsString();
                    JsonArray cities= statesAndCitiesObject1.get("cities").getAsJsonArray().getAsJsonArray();



                    realDBhelper.insertCities(countryName_en,countryName_ur,countryInitials,countrycode,provinceName_en,provinceeName_ur,cities.toString());


                    surveyformsResponse.setValue("success");

                    Log.d(constants.Tag, countryName_en);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d(constants.Tag, t.getMessage().toString());
                surveyformsResponse.setValue(t.getMessage().toString());
            }
        });


    }

    public void getSurveyForm(){





        Call<JsonObject> formReader = new RetrofitClientSurvey().retrofitclient().getSurvey("6");
        formReader.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject surveyObject = response.body();
                    JsonObject dataObject = surveyObject.get("data").getAsJsonObject();
                    JsonObject formObject = dataObject.get("surveys").getAsJsonObject();

                    Log.d(constants.Tag, surveyObject.toString());

//                    JsonArray surveyData = surveyObject.get("surveys").getAsJsonArray();
//                    JsonObject farmerObject = surveyData.get(0).getAsJsonObject();
//                    Log.d(constants.Tag, farmerObject.toString());
//
                    String farmerId = formObject.get("id").getAsString();
                    String farmerName = formObject.get("name").getAsString();
                    String farmerType = formObject.get("type").getAsString();
                    String form = formObject.get("json").getAsString();
//                    JsonObject farmerFormJson = farmerObject.get("json").getAsJsonObject();
//                    JsonArray farmerFormPages = farmerFormJson.get("pages").getAsJsonArray();
//                    JsonObject page1object = farmerFormPages.get(0).getAsJsonObject();
//                    JsonArray elementsarray = page1object.get("elements").getAsJsonArray();
//                    JsonObject form1 = elementsarray.get(0).getAsJsonObject();





                    realDBhelper.insertFarmerForm(farmerId, farmerName, farmerType, form);

//                    JsonObject cattleObject = surveyData.get(1).getAsJsonObject();
//                    Log.d(constants.Tag, cattleObject.toString());
//                    String cattleId = cattleObject.get("id").getAsString();
//                    String cattleName = cattleObject.get("name").getAsString();
//                    String cattleType = cattleObject.get("type").getAsString();
//                    JsonObject cattleFormJson = cattleObject.get("json").getAsJsonObject();
//                    JsonArray cattleFormPages = cattleFormJson.get("pages").getAsJsonArray();
//                    JsonObject page1objectcattle = cattleFormPages.get(0).getAsJsonObject();
//                    JsonArray elementsarraycattle = page1object.get("elements").getAsJsonArray();
//                    JsonObject form1cattle = elementsarray.get(0).getAsJsonObject();
//
//                    Log.d("retrieved",form1.toString());
//
//                    realDBhelper.insertCattleForm(cattleId, cattleName, cattleType,cattleFormPages.toString());

                    surveyformsResponse.setValue("success");


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(constants.Tag, t.getMessage().toString());
                surveyformsResponse.setValue(t.getMessage().toString());
            }
        });
    }



    public void callSavedFormsMethod(){

        LoadDataAsyncTask task = new LoadDataAsyncTask();
        task.execute();
    }


    class LoadDataAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(constants.Tag,"Background Operation begins=======");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getSurveyForm();
            Log.d(constants.Tag,"Background Operation Resumes=======");
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d(constants.Tag,"Background Operation Ends=======");
        }
    }
}
