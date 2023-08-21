package com.example.cncdcattleedcandroid.OfflineDb.Helper;

import android.content.Context;
import android.util.Log;

import com.example.cncdcattleedcandroid.OfflineDb.Models.CattleSurveyModel;
import com.example.cncdcattleedcandroid.OfflineDb.Models.DashboardDataModel;
import com.example.cncdcattleedcandroid.OfflineDb.Models.FarmerFormCompleted;
import com.example.cncdcattleedcandroid.OfflineDb.Models.FarmerSurveyModel;
import com.example.cncdcattleedcandroid.OfflineDb.Models.citiesModel;
import com.example.cncdcattleedcandroid.Realm.TestModel;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class RealmDatabaseHlper {


    Realm realm;

    Constants constants;

    Context context;

    SessionManager sessionManager;


    public RealmDatabaseHlper(Context ctx){

        this.context =ctx;

        constants = new Constants();
        sessionManager = new SessionManager(ctx);
    }


    public RealmDatabaseHlper(){
        constants = new Constants();

    }


    public Realm InitializeRealm(Context context){

        realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("default.realm")
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(constants.Tag,"Initialized");

        return  realm;
    }


    public void InsertData(

            int id,
            String username,
            String profilepcitureBase64,
            String userpassword,
            String num_cattles,
            String num_cows,
            String num_farmers,
            String num_buffalos


    ){

        realm = Realm.getDefaultInstance();
        DashboardDataModel dataModel =new DashboardDataModel(id,profilepcitureBase64,username,userpassword,num_cattles,num_cows,num_buffalos,num_farmers);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.insert(dataModel);

            }
        });
    }



    public void updateData(


            int id,
            String username,
            String profilepcitureBase64,
            String userpassword,
            String num_cattles,
            String num_cows,
            String num_farmers,
            String num_buffalos
    ){

        realm = Realm.getDefaultInstance();
        DashboardDataModel dataModel =new DashboardDataModel(id,profilepcitureBase64,username,userpassword,num_cattles,num_cows,num_buffalos,num_farmers);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (dataModel != null) {

                    realm.insertOrUpdate(dataModel);
                }
            }
        });
    }



    public void insertCities(
            String countryName_en,
            String countryName_ur,
            String countryInitials,
            String countrycode,
            String provinceName_en,
            String provinceeName_ur,
            String cities
    ){

        realm = Realm.getDefaultInstance();



        int id=1;


// logic for incrementing Primary key
//        if (sessionManager.checkisApplicationFirstTime()){
//
//            id = 1;
//            Log.d(constants.info,"Check first"+id);
//            sessionManager.SavePrimarykey(1);
//        }
//        else{
//            id = sessionManager.getPrimaryKey()+1;
//            sessionManager.SavePrimarykey(id);
//            Log.d(constants.info,"Check second"+id);
//        }



        Log.d(constants.info,String.valueOf(id));

        citiesModel citiesModel = new citiesModel(id,countryName_en,countryName_ur,countryInitials,countrycode,provinceName_en,provinceeName_ur,cities);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.insertOrUpdate(citiesModel);
            }
        });
    }
    public ArrayList<String> readDataCities(){

        Realm realm = Realm.getDefaultInstance();
        RealmResults<citiesModel> tasks = realm.where(citiesModel.class).findAll();
        ArrayList<String> datalist = new ArrayList<>();
        Log.d("TAG",tasks.toString());
        for (citiesModel task : tasks) {
            String cities = task.getCities();
            datalist.add(cities);


            // Handle the task data
        }
        Log.d("TAG","readtask");

        realm.close();

        return  datalist;

    }


//    public ArrayList<String> readDatafromFarmerModel(){
//
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<citiesModel> tasks = realm.where(citiesModel.class).findAll();
//        ArrayList<String> datalist = new ArrayList<>();
//        Log.d("TAG",tasks.toString());
//        for (citiesModel task : tasks) {
//            String cities = task.getCities();
//            datalist.add(cities);
//
//
//            // Handle the task data
//        }
//        Log.d("TAG","readtask");
//
//        realm.close();
//
//        return  datalist;
//
//    }

    public void insertFarmerForm(
            String id,
            String name,
            String type,
            String formPages
    ){
        realm = Realm.getDefaultInstance();


        FarmerSurveyModel farmerSurveyModel = new FarmerSurveyModel(id, name, type, formPages);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(farmerSurveyModel);
            }
        });

    }

    public void insertCattleForm(
            String id,
            String name,
            String type,
            String formPages
    ){
        realm = Realm.getDefaultInstance();


        CattleSurveyModel cattleSurveyModel = new CattleSurveyModel(id, name, type, formPages);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(cattleSurveyModel);
            }
        });

    }

    public String readDataSurvey(String id){

        Realm realm = Realm.getDefaultInstance();
        FarmerSurveyModel tasks = realm.where(FarmerSurveyModel.class)
                .equalTo("id", id)
                .findFirst();

        Log.d("TAG",tasks.toString());

        String survey_pages = tasks.getFormPages();

        Log.d("TAG","readtask");

        realm.close();

        return  survey_pages;

    }


    public void InsertCompletedForm(
            int formid,
            String formstartime,
            String formEndtime,
            String coordinate_start_lat,
            String coordinate_start_lon,
            String coordinate_end_lat,
            String coordinate_end_lon,
            String appversion,
            String completedform){

        Realm realm = Realm.getDefaultInstance();


        FarmerFormCompleted formCompleted = new
                FarmerFormCompleted(
                        formid,
                        formstartime,
                        formEndtime,
                        coordinate_start_lat,
                        coordinate_start_lon,
                        coordinate_end_lat,
                        coordinate_end_lon,
                        completedform,
                        appversion
        );
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(formCompleted);
                Log.d(constants.info,"Form");
            }

        });
    }


    public ArrayList<String> readCompletedForm(){

        Realm realm = Realm.getDefaultInstance();
        RealmResults<FarmerFormCompleted> tasks = realm.where(FarmerFormCompleted.class).findAll();
        Log.d("TAG",tasks.toString());
        ArrayList<String> generatedResponse = new ArrayList<>();


        for(FarmerFormCompleted  completed : tasks){

            int formid = completed.getId();
            String startTime = completed.getSurvey_start_time();
            String endTime = completed.getSurvey_end_time();
            String appversion = completed.getAppversion();
            String start_coordinate_latitude = completed.getCoordinates_start_latitude();
            String start_coordinate_longitude = completed.getCoordinates_start_longitude();
            String end_coordinate_latitude = completed.getCoordinates_end_latitude();
            String end_coordinate_longitude = completed.getCoordinates_end_longitude();
            String pages= completed.getFormPagesCompleted();

            generatedResponse.add(0,startTime );
            generatedResponse.add(1,endTime);
            generatedResponse.add(2,appversion);
            generatedResponse.add(3,start_coordinate_latitude);
            generatedResponse.add(4,start_coordinate_longitude);
            generatedResponse.add(5,end_coordinate_latitude);
            generatedResponse.add(6,end_coordinate_longitude);
            generatedResponse.add(7,pages);
            generatedResponse.add(8,String.valueOf(formid));

        }

        Log.d("CompletedForm",generatedResponse.toString());

        realm.close();

        return generatedResponse;

    }

}
