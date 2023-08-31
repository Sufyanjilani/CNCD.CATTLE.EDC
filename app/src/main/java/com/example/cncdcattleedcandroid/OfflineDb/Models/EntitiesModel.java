package com.example.cncdcattleedcandroid.OfflineDb.Models;

import io.realm.RealmObject;

public class EntitiesModel extends RealmObject {



    String general_basic;


    public EntitiesModel(){

    }

    public String getGeneral_basic() {
        return general_basic;
    }

    public void setGeneral_basic(String general_basic) {
        this.general_basic = general_basic;
    }

    public String getGeneral_diet() {
        return general_diet;
    }

    public void setGeneral_diet(String general_diet) {
        this.general_diet = general_diet;
    }

    public String getGeneral_medical() {
        return general_medical;
    }

    public void setGeneral_medical(String general_medical) {
        this.general_medical = general_medical;
    }

    public String getPersonal_basic() {
        return personal_basic;
    }

    public void setPersonal_basic(String personal_basic) {
        this.personal_basic = personal_basic;
    }

    public String getPersonal_milk() {
        return personal_milk;
    }

    public void setPersonal_milk(String personal_milk) {
        this.personal_milk = personal_milk;
    }

    public String getPersonal_medical() {
        return personal_medical;
    }

    public void setPersonal_medical(String personal_medical) {
        this.personal_medical = personal_medical;
    }

    public String getPersonal_traits() {
        return personal_traits;
    }

    public void setPersonal_traits(String personal_traits) {
        this.personal_traits = personal_traits;
    }

    public String getPersonal_mik_weight() {
        return personal_mik_weight;
    }

    public void setPersonal_mik_weight(String personal_mik_weight) {
        this.personal_mik_weight = personal_mik_weight;
    }

    String general_diet;

    String general_medical;

    String personal_basic;

    String personal_milk;

    String personal_medical;

    String personal_traits;

    String personal_mik_weight;

    public EntitiesModel(String general_basic, String general_diet, String general_medical, String personal_basic, String personal_milk, String personal_medical, String personal_traits, String personal_mik_weight) {
        this.general_basic = general_basic;
        this.general_diet = general_diet;
        this.general_medical = general_medical;
        this.personal_basic = personal_basic;
        this.personal_milk = personal_milk;
        this.personal_medical = personal_medical;
        this.personal_traits = personal_traits;
        this.personal_mik_weight = personal_mik_weight;
    }











}
