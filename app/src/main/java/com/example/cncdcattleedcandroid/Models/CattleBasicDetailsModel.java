package com.example.cncdcattleedcandroid.Models;

public class CattleBasicDetailsModel {
    String sampleID, farmerCattleID, cTypeID, cattleGender, cBreedID, breedRecently, cattle_feed_per_day, greenFeed_kg, hasGrazing_area,
            hoursOf_grazing, matedRecently, teeth_of_cattle, wandaFeed_kg;

    public CattleBasicDetailsModel(String sampleID, String farmerCattleID, String cTypeID, String cattleGender, String cBreedID, String breedRecently, String cattle_feed_per_day, String greenFeed_kg, String hasGrazing_area, String hoursOf_grazing, String matedRecently, String teeth_of_cattle, String wandaFeed_kg) {
        this.sampleID = sampleID;
        this.farmerCattleID = farmerCattleID;
        this.cTypeID = cTypeID;
        this.cattleGender = cattleGender;
        this.cBreedID = cBreedID;
        this.breedRecently = breedRecently;
        this.cattle_feed_per_day = cattle_feed_per_day;
        this.greenFeed_kg = greenFeed_kg;
        this.hasGrazing_area = hasGrazing_area;
        this.hoursOf_grazing = hoursOf_grazing;
        this.matedRecently = matedRecently;
        this.teeth_of_cattle = teeth_of_cattle;
        this.wandaFeed_kg = wandaFeed_kg;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getFarmerCattleID() {
        return farmerCattleID;
    }

    public void setFarmerCattleID(String farmerCattleID) {
        this.farmerCattleID = farmerCattleID;
    }

    public String getcTypeID() {
        return cTypeID;
    }

    public void setcTypeID(String cTypeID) {
        this.cTypeID = cTypeID;
    }

    public String getCattleGender() {
        return cattleGender;
    }

    public void setCattleGender(String cattleGender) {
        this.cattleGender = cattleGender;
    }

    public String getcBreedID() {
        return cBreedID;
    }

    public void setcBreedID(String cBreedID) {
        this.cBreedID = cBreedID;
    }

    public String getBreedRecently() {
        return breedRecently;
    }

    public void setBreedRecently(String breedRecently) {
        this.breedRecently = breedRecently;
    }

    public String getCattle_feed_per_day() {
        return cattle_feed_per_day;
    }

    public void setCattle_feed_per_day(String cattle_feed_per_day) {
        this.cattle_feed_per_day = cattle_feed_per_day;
    }

    public String getGreenFeed_kg() {
        return greenFeed_kg;
    }

    public void setGreenFeed_kg(String greenFeed_kg) {
        this.greenFeed_kg = greenFeed_kg;
    }

    public String getHasGrazing_area() {
        return hasGrazing_area;
    }

    public void setHasGrazing_area(String hasGrazing_area) {
        this.hasGrazing_area = hasGrazing_area;
    }

    public String getHoursOf_grazing() {
        return hoursOf_grazing;
    }

    public void setHoursOf_grazing(String hoursOf_grazing) {
        this.hoursOf_grazing = hoursOf_grazing;
    }

    public String getMatedRecently() {
        return matedRecently;
    }

    public void setMatedRecently(String matedRecently) {
        this.matedRecently = matedRecently;
    }

    public String getTeeth_of_cattle() {
        return teeth_of_cattle;
    }

    public void setTeeth_of_cattle(String teeth_of_cattle) {
        this.teeth_of_cattle = teeth_of_cattle;
    }

    public String getWandaFeed_kg() {
        return wandaFeed_kg;
    }

    public void setWandaFeed_kg(String wandaFeed_kg) {
        this.wandaFeed_kg = wandaFeed_kg;
    }
}
