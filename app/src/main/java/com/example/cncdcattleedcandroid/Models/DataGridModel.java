package com.example.cncdcattleedcandroid.Models;

public class DataGridModel {

    public String id;
    public String farmerid;

    public DataGridModel(String id, String farmerid, String farmid, String farmname, String cattlename, String cattleimagepath, String cattleid) {
        this.id = id;
        this.farmerid = farmerid;
        this.farmid = farmid;
        this.farmname = farmname;
        this.cattlename = cattlename;
        this.cattleimagepath = cattleimagepath;
        this.cattleid = cattleid;
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
