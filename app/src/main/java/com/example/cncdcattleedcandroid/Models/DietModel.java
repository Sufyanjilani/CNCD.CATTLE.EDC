package com.example.cncdcattleedcandroid.Models;

public class DietModel {

    String farmerEntityID;

    public DietModel(String farmerEntityID, String questionnaireID, String created_at, String created_by) {
        this.farmerEntityID = farmerEntityID;
        this.questionnaireID = questionnaireID;
        this.created_at = created_at;
        this.created_by = created_by;
    }

    String questionnaireID;
    String created_at;
    String created_by;

    public String getFarmerEntityID() {
        return farmerEntityID;
    }

    public void setFarmerEntityID(String farmerEntityID) {
        this.farmerEntityID = farmerEntityID;
    }

    public String getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(String questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
