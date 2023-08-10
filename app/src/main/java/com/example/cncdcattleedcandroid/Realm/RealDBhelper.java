package com.example.cncdcattleedcandroid.Realm;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealDBhelper {

    Realm realm;

    public RealDBhelper(){

        realm = Realm.getDefaultInstance();
    }

    public void Insert(int id ,String farmerName, String cattlename, String cattlebase65){

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                TestModel task = realm.createObject(TestModel.class);
//                task.setFarmer_name(farmerName);
//                task.setCattle_name(cattlename);
//            }
//        });

        realm.beginTransaction();
        TestModel testModel = realm.createObject(TestModel.class,id);
        testModel.setFarmer_name(farmerName);
        testModel.setCattle_name(cattlename);
        testModel.setImage_base64("64");
        realm.commitTransaction();
        realm.close();
    }



    public void Delete(String farmerName){



        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TestModel task = realm.where(TestModel.class).equalTo("farmer_name", farmerName).findFirst();
                if (task != null) {
                    task.deleteFromRealm();
                }
            }
        });

        realm.close();
    }


    public void Update(String farmer_name){

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TestModel testModel = realm.where(TestModel.class).equalTo("farmer_name", farmer_name).findFirst();
                if (testModel != null) {

                }
            }
        });

        realm.close();

    }


    public void readTask(){

        Realm realm = Realm.getDefaultInstance();
        RealmResults<TestModel> tasks = realm.where(TestModel.class).findAll();
        Log.d("TAG",tasks.toString());
        for (TestModel task : tasks) {
            String farmer_name = task.getFarmer_name();
            String cattle_name = task.getCattle_name();



            Log.d("TAG",cattle_name);
            // Handle the task data
        }
        Log.d("TAG","readtask");

        realm.close();

    }


}
