package com.example.cncdcattleedcandroid.Realm;

import io.realm.RealmObject;

public class TestModel  extends RealmObject {

    public  TestModel(){

    }
    public TestModel(String farmer_name, String cattle_name, String image_base64) {
        this.farmer_name = farmer_name;
        this.cattle_name = cattle_name;
        this.image_base64 = image_base64;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getCattle_name() {
        return cattle_name;
    }

    public void setCattle_name(String cattle_name) {
        this.cattle_name = cattle_name;
    }

    public String getImage_base64() {
        return image_base64;
    }

    public void setImage_base64(String image_base64) {
        this.image_base64 = image_base64;
    }

    String farmer_name;
    String cattle_name;

    String image_base64;
}
