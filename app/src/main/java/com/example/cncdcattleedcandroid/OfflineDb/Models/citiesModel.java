package com.example.cncdcattleedcandroid.OfflineDb.Models;

import android.telephony.SignalStrength;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class citiesModel extends RealmObject {


    public citiesModel(){

    }


    @PrimaryKey int id;
    String country;
    String countryName_en;
    String countryName_ur;
    String countryInitials;
    String countrycode;

    String stateName;
    String provinceName_en;
    String provinceName_ur;
    String cities;

    public citiesModel(int id, String countryName_en, String countryName_ur, String countryInitials, String countrycode, String provinceName_en, String provinceName_ur, String cities) {
        this.id = id;
        this.countryName_en = countryName_en;
        this.countryName_ur = countryName_ur;
        this.countryInitials = countryInitials;
        this.countrycode = countrycode;
        this.provinceName_en = provinceName_en;
        this.provinceName_ur = provinceName_ur;
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

    public String getCountryName_en() {
        return countryName_en;
    }

    public void setCountryName_en(String countryName_en) {
        this.countryName_en = countryName_en;
    }

    public String getCountryName_ur() {
        return countryName_ur;
    }

    public void setCountryName_ur(String countryName_ur) {
        this.countryName_ur = countryName_ur;
    }

    public String getProvinceName_en() {
        return provinceName_en;
    }

    public void setProvinceName_en(String provinceName_en) {
        this.provinceName_en = provinceName_en;
    }

    public String getProvinceName_ur() {
        return provinceName_ur;
    }

    public void setProvinceName_ur(String provinceName_ur) {
        this.provinceName_ur = provinceName_ur;
    }
}
