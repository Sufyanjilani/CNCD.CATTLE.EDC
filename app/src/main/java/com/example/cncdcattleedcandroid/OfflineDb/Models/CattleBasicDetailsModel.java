package com.example.cncdcattleedcandroid.OfflineDb.Models;

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
}
