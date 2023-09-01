package com.example.cncdcattleedcandroid.ViewModels;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cncdcattleedcandroid.Models.Cattles;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.OfflineDb.Models.DashboardDataModel;
import com.example.cncdcattleedcandroid.Realm.RealDBhelper;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends AndroidViewModel {


    Constants constants;
    RealmDatabaseHlper realDBhelper;


    MutableLiveData<String> _isLogoutSuccess = new MutableLiveData<>();
    public  MutableLiveData<String> islogoutsucces = _isLogoutSuccess;


    MutableLiveData<String> _isallDataRetrieved = new MutableLiveData<>();
    public  MutableLiveData<String> isallDataRetrieved = _isallDataRetrieved;


    SessionManager sessionManager;
    public MutableLiveData<String> citiesResponse = new MutableLiveData<>();
    public MutableLiveData<String> surveyformsResponse = new MutableLiveData<>();

    public MutableLiveData<JsonObject>  dashboardDataJson = new MutableLiveData<>();

    public MutableLiveData<String> dashboardDataResponse = new MutableLiveData<>();

    public MutableLiveData<JsonObject> dashboardFarmerData = new MutableLiveData<>();

    public MutableLiveData<String> isResponseSuccess = new MutableLiveData<>();


    public DashboardViewModel(@NonNull Application application) {
        super(application);
        constants = new Constants();
        realDBhelper  = new RealmDatabaseHlper();
        sessionManager = new SessionManager(getApplication().getApplicationContext());
    }


    public void getDataforInjection() {

        Call<JsonObject> apireader = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getcities();
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



        for(int i =1; i<9;i++){


            Call<JsonObject> formReader = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getSurvey(String.valueOf(i));
            formReader.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()){
                        JsonObject surveyObject = response.body();
                        JsonArray dataObject = surveyObject.get("data").getAsJsonArray();
                        JsonObject formObject = dataObject.get(0).getAsJsonObject();

                        Log.d(constants.Tag, surveyObject.toString());

//                    JsonArray surveyData = surveyObject.get("surveys").getAsJsonArray();
//                    JsonObject farmerObject = surveyData.get(0).getAsJsonObject();
//                    Log.d(constants.Tag, farmerObject.toString());
//
                        String questionairId = formObject.get("questionnaireID").getAsString();
                        String questionnaireName = formObject.get("questionnaireName").getAsString();
                        String form = formObject.get("questionnaireJSON").getAsString();;

                        //String farmerType = formObject.get("type").getAsString();

//                    JsonObject farmerFormJson = farmerObject.get("json").getAsJsonObject();
//                    JsonArray farmerFormPages = farmerFormJson.get("pages").getAsJsonArray();
//                    JsonObject page1object = farmerFormPages.get(0).getAsJsonObject();
//                    JsonArray elementsarray = page1object.get("elements").getAsJsonArray();
//                    JsonObject form1 = elementsarray.get(0).getAsJsonObject();




                        Log.d(constants.info,form);

                        realDBhelper.insertFarmerForm(questionairId,questionnaireName,form);

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



        Call<JsonObject> formReader = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getSurvey("1");
        formReader.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject surveyObject = response.body();
                    JsonArray dataObject = surveyObject.get("data").getAsJsonArray();
                    JsonObject formObject = dataObject.get(0).getAsJsonObject();

                    Log.d(constants.Tag, surveyObject.toString());

//                    JsonArray surveyData = surveyObject.get("surveys").getAsJsonArray();
//                    JsonObject farmerObject = surveyData.get(0).getAsJsonObject();
//                    Log.d(constants.Tag, farmerObject.toString());
//
                    String questionairId = formObject.get("questionnaireID").getAsString();
                    String questionnaireName = formObject.get("questionnaireName").getAsString();
                    String form = formObject.get("questionnaireJSON").getAsString();;

                    //String farmerType = formObject.get("type").getAsString();

//                    JsonObject farmerFormJson = farmerObject.get("json").getAsJsonObject();
//                    JsonArray farmerFormPages = farmerFormJson.get("pages").getAsJsonArray();
//                    JsonObject page1object = farmerFormPages.get(0).getAsJsonObject();
//                    JsonArray elementsarray = page1object.get("elements").getAsJsonArray();
//                    JsonObject form1 = elementsarray.get(0).getAsJsonObject();




                    Log.d(constants.info,form);

                    realDBhelper.insertFarmerForm(questionairId,questionnaireName,form);

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

    public void Logout(){
        JsonObject logoutObject = new JsonObject();
        logoutObject.addProperty("accessToken",sessionManager.getbearer());
        new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().logout(logoutObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()){

                    _isLogoutSuccess.setValue("loggedOut");
                    sessionManager.savebarearToken("null");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("error",t.getMessage().toString());

                _isLogoutSuccess.setValue("failed");
            }
        });
    }


    public void PostAllDataToServer(){



        JsonObject finalobject = new JsonObject();
        QueryFormsAsync queryFormsAsync = new QueryFormsAsync();
        queryFormsAsync.execute();
        ArrayList<String> forms = queryFormsAsync.returnAllforms();
        Log.d(constants.Tag,forms.toString());


    }

    public ArrayList<Cattles> dashboardData(){
        JsonObject tokenObject = new JsonObject();
//       tokenObject.addProperty("accessToken","8e4b79db8cf17f541812ef022974aae6043ef87efd7841a50d9514d6f2cd1088");
//        tokenObject.addProperty("accessToken", sessionManager.getbearer());
//        tokenObject.addProperty("sessionID", sessionManager.getSessionID());
//        sessionManager.getbearer();
//        Log.d("token1", tokenObject.toString());
//        Log.d("token2", sessionManager.getbearer());
        ArrayList<DashboardDataModel> dashboardDataModelArrayList = new ArrayList<>();
        ArrayList<Cattles> cattlesArrayList = new ArrayList<>();

        Call<JsonObject> getData = new RetrofitClientSurvey(getApplication().getApplicationContext()).retrofitclient().getDashboardData();
        getData.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    if (!response.body().get("error").getAsString().equals("true")){
                        isResponseSuccess.setValue("Successes");
                        JsonObject dashboardData = response.body();


                        if (response.body().get("data").isJsonObject()) {

                            JsonObject dataObject = dashboardData.get("data").getAsJsonObject();
                            JsonObject cardObject = dataObject.get("cardsData").getAsJsonObject();
                            Log.d(constants.Tag, cardObject.toString());
                            ;
                            String totalFarms = cardObject.get("totalFarms").getAsString();
                            String totalFarmers = cardObject.get("totalFarmers").getAsString();
                            String totalCattles = cardObject.get("totalCattles").getAsString();

                            JsonObject gridObject = dataObject.get("gridsData").getAsJsonObject();
                            JsonArray farmerData = gridObject.get("farmers").getAsJsonArray();
                            Log.d(constants.Tag, gridObject.toString());
                            for (int i = 0; i < farmerData.size(); i++) {
                                JsonObject obj = farmerData.get(i).getAsJsonObject();
                                String farmerID = obj.get("farmerID").getAsString();
                                String farmID = obj.get("farmID").getAsString();
                                String farmName = obj.get("farmName").getAsString();
                                String farmAddress = obj.get("farmAddress").getAsString();
                                String farmerName = obj.get("farmerName").getAsString();
                                String created_at = obj.get("created_at").getAsString();

                                //   DashboardDataModel dashboardDataModel = new DashboardDataModel(totalFarms, totalFarmers, totalCattles);
//
                                Cattles cattles = new Cattles(farmerID, farmID,
                                        farmName, farmAddress, farmerName, created_at);
                                cattlesArrayList.add(cattles);
                                Log.d("listdata", cattlesArrayList.toString());


                            }
                            dashboardDataResponse.setValue("success");
                            dashboardDataJson.setValue(dataObject);
                            dashboardFarmerData.setValue(gridObject);
                        }
                        else{

                            dashboardDataResponse.setValue(response.body().get("msg").getAsString());
                        }
                    }else{

                        isResponseSuccess.setValue(response.body().get("msg").getAsString());
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d(constants.Tag, t.getMessage().toString());
                dashboardDataResponse.setValue(t.getMessage().toString());
            }
        });
        return cattlesArrayList;
    }

    class QueryFormsAsync extends AsyncTask<Void,Void, ArrayList<String>>{


        ArrayList<String> allforms = new ArrayList<>();


        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            ArrayList<String> forms = realDBhelper.readCompletedForm();
            if (forms.size() == 0){

                _isallDataRetrieved.postValue("No forms found");
                return new ArrayList<>();
            }
            else{

                return forms;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(constants.Tag,"getting forms started");
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            Log.d(constants.Tag,"got all forms");
            isallDataRetrieved.postValue("forms retreived");
            allforms = strings;
            Log.d(constants.Tag,allforms.toString());
        }

        public ArrayList<String>  returnAllforms(){

            return allforms;
        }
    }
}
