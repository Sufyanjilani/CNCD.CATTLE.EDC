package com.example.cncdcattleedcandroid.Realm;

import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealDBhelper {

    Realm realm;


    public RealDBhelper(){

        realm = Realm.getDefaultInstance();
    }

    public void Insert(String farmerName, String cattlename, String cattlebase65){
        TestModel Task = new TestModel(farmerName,cattlename,cattlebase65);
        realm.executeTransaction (transactionRealm -> {
            transactionRealm.insert(Task);
            Log.d("TAg","insertion success");
        });
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


    public ArrayList<TestModel> readTask(){

        Realm realm = Realm.getDefaultInstance();
        RealmResults<TestModel> tasks = realm.where(TestModel.class).findAll();
        ArrayList<TestModel> datalist = new ArrayList<>();
        Log.d("TAG",tasks.toString());
        for (TestModel task : tasks) {
            String farmer_name = task.getFarmer_name();
            String cattle_name = task.getCattle_name();
            String base65 = task.getImage_base64();

            TestModel model = new TestModel(farmer_name,cattle_name,base65);

            datalist.add(model);


            // Handle the task data
        }
        Log.d("TAG","readtask");

        realm.close();

        return  datalist;

    }


}
