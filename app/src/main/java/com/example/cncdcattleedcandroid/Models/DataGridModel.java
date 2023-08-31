package com.example.cncdcattleedcandroid.Models;

public class DataGridModel {

    public String id;
    public String farmerid;

    String cattleID, farmerCattleID,cTypeID, cTypeName, cattleGender, cBreedID, cBreedName, created_at, created_by, updated_at, updated_by, sampleID;

//    public DataGridModel(String id, String farmerid, String farmid, String farmname, String cattlename, String cattleimagepath, String cattleid) {
//        this.id = id;
//        this.farmerid = farmerid;
//        this.farmid = farmid;
//        this.farmname = farmname;
//        this.cattlename = cattlename;
//        this.cattleimagepath = cattleimagepath;
//        this.cattleid = cattleid;
//    }

    public DataGridModel(String cattleID, String farmerCattleID, String cTypeID, String cTypeName, String cattleGender, String cBreedID, String cBreedName, String created_at, String created_by, String updated_at, String updated_by, String sampleID){
        this.cattleID = cattleID;
        this.farmerCattleID = farmerCattleID;
        this.cTypeID = cTypeID;
        this.cTypeName = cTypeName;
        this.cattleGender = cattleGender;
        this.cBreedID = cBreedID;
        this.cBreedName = cBreedName;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
        this.sampleID = sampleID;
    }

    public String getCattleID() {
        return cattleID;
    }

    public void setCattleID(String cattleID) {
        this.cattleID = cattleID;
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

    public String getcTypeName() {
        return cTypeName;
    }

    public void setcTypeName(String cTypeName) {
        this.cTypeName = cTypeName;
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

    public String getcBreedName() {
        return cBreedName;
    }

    public void setcBreedName(String cBreedName) {
        this.cBreedName = cBreedName;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

        public String farmid;
    public String farmname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getFarmid() {
        return farmid;
    }

    public void setFarmid(String farmid) {
        this.farmid = farmid;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public String getCattlename() {
        return cattlename;
    }

    public void setCattlename(String cattlename) {
        this.cattlename = cattlename;
    }

    public String getCattleimagepath() {
        return cattleimagepath;
    }

    public void setCattleimagepath(String cattleimagepath) {
        this.cattleimagepath = cattleimagepath;
    }

    public String getCattleid() {
        return cattleid;
    }

    public void setCattleid(String cattleid) {
        this.cattleid = cattleid;
    }

    public String cattlename;

    public String cattleimagepath;

    public String cattleid;






}
