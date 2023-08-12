package com.example.cncdcattleedcandroid.OfflineDb.Helper;

import android.content.Context;
import android.util.Log;

import com.example.cncdcattleedcandroid.OfflineDb.Models.DashboardDataModel;
import com.example.cncdcattleedcandroid.OfflineDb.Models.citiesModel;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
            String country,
            String countryInitials,
            String countrycode,
            String stateName,
            String cities
    ){

        realm = Realm.getDefaultInstance();



        int id=0;

        if (sessionManager.checkisApplicationFirstTime()){

            id = 1;
            Log.d(constants.info,"Check first"+id);
        }
        else{
            id = sessionManager.getPrimaryKey()+1;
            Log.d(constants.info,"Check second"+id);
        }


        citiesModel citiesModel = new citiesModel(id,country,countryInitials,countrycode,stateName,cities);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.insert(citiesModel);
            }
        });
    }
}
