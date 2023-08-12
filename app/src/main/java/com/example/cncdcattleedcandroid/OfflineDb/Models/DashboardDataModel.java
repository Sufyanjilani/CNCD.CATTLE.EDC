package com.example.cncdcattleedcandroid.OfflineDb.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DashboardDataModel extends RealmObject {


    public DashboardDataModel(){

    }
    public DashboardDataModel(int id, String profilepictureBase64, String user_name, String user_password, String number_of_cattles, String number_of_cows, String number_of_Buffalos, String number_of_farmers) {
        this.id = id;
        this.profilepictureBase64 = profilepictureBase64;
        this.user_name = user_name;
        this.user_password = user_password;
        this.number_of_cattles = number_of_cattles;
        this.number_of_cows = number_of_cows;
        this.number_of_Buffalos = number_of_Buffalos;
        this.number_of_farmers = number_of_farmers;
    }

    @PrimaryKey int id;
    String profilepictureBase64;

    String user_name;

    String user_password;

    String number_of_cattles;
    String number_of_cows;

    String number_of_Buffalos;

    String number_of_farmers;


}
