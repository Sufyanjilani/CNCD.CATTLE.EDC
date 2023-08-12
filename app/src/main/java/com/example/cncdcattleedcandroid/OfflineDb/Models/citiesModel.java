package com.example.cncdcattleedcandroid.OfflineDb.Models;

import android.telephony.SignalStrength;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class citiesModel extends RealmObject {


   public citiesModel(){

    }


    @PrimaryKey int id;
    String country;
    String countryInitials;
    String countrycode;

    String stateName;
    String cities;

    public citiesModel(int id, String country, String countryInitials, String countrycode, String stateName, String cities) {
        this.id = id;
        this.country = country;
        this.countryInitials = countryInitials;
        this.countrycode = countrycode;
        this.stateName = stateName;
        this.cities = cities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryInitials() {
        return countryInitials;
    }

    public void setCountryInitials(String countryInitials) {
        this.countryInitials = countryInitials;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }



    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }



}
