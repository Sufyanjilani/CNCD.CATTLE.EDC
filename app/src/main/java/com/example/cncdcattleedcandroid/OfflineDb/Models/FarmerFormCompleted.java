package com.example.cncdcattleedcandroid.OfflineDb.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FarmerFormCompleted extends RealmObject {


    public FarmerFormCompleted(int id,String survey_start_time, String survey_end_time, String coordinates_start_latitude, String coordinates_start_longitude, String coordinates_end_latitude, String coordinates_end_longitude, String formPagesCompleted, String appversion) {
        this.id = id;
        this.survey_start_time = survey_start_time;
        this.survey_end_time = survey_end_time;
        this.coordinates_start_latitude = coordinates_start_latitude;
        this.coordinates_start_longitude = coordinates_start_longitude;
        this.coordinates_end_latitude = coordinates_end_latitude;
        this.coordinates_end_longitude = coordinates_end_longitude;
        this.formPagesCompleted = formPagesCompleted;
        this.appversion = appversion;
    }

    @PrimaryKey int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getSurvey_start_time() {
        return survey_start_time;
    }

    public void setSurvey_start_time(String survey_start_time) {
        this.survey_start_time = survey_start_time;
    }

    public String getSurvey_end_time() {
        return survey_end_time;
    }

    public void setSurvey_end_time(String survey_end_time) {
        this.survey_end_time = survey_end_time;
    }

    public String getCoordinates_start_latitude() {
        return coordinates_start_latitude;
    }

    public void setCoordinates_start_latitude(String coordinates_start_latitude) {
        this.coordinates_start_latitude = coordinates_start_latitude;
    }

    public String getCoordinates_start_longitude() {
        return coordinates_start_longitude;
    }

    public void setCoordinates_start_longitude(String coordinates_start_longitude) {
        this.coordinates_start_longitude = coordinates_start_longitude;
    }

    public String getCoordinates_end_latitude() {
        return coordinates_end_latitude;
    }

    public void setCoordinates_end_latitude(String coordinates_end_latitude) {
        this.coordinates_end_latitude = coordinates_end_latitude;
    }

    public String getCoordinates_end_longitude() {
        return coordinates_end_longitude;
    }

    public void setCoordinates_end_longitude(String coordinates_end_longitude) {
        this.coordinates_end_longitude = coordinates_end_longitude;
    }

    public String getFormPagesCompleted() {
        return formPagesCompleted;
    }

    public void setFormPagesCompleted(String formPagesCompleted) {
        this.formPagesCompleted = formPagesCompleted;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    String survey_start_time;
    String survey_end_time;
    String coordinates_start_latitude;
    String coordinates_start_longitude;
    String coordinates_end_latitude;
    String coordinates_end_longitude;
    String formPagesCompleted;

    String appversion;



}
