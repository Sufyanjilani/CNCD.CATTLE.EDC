package com.example.cncdcattleedcandroid.Models;

public class Cattles {
    String farmerID, farmID, farmName, farmAddress, farmerName, created_at;
    public Cattles(String cattleName, String milkCycle) {
        this.cattleName = cattleName;
        this.milkCycle = milkCycle;
    }

    public Cattles(String farmerID, String farmID, String farmName, String farmAddress, String farmerName, String created_at){
        this. farmerID = farmerID;
        this.farmID = farmID;
        this.farmName = farmName;
        this.farmAddress = farmAddress;
        this.farmerName = farmerName;
        this.created_at = created_at;
    }

    public String cattleName;
    public String milkCycle;

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getFarmID() {
        return farmID;
    }

    public void setFarmID(String farmID) {
        this.farmID = farmID;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
