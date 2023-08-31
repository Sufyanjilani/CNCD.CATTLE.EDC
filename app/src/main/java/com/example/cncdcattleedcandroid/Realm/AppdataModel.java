package com.example.cncdcattleedcandroid.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AppdataModel extends RealmObject {

    public AppdataModel(){

    }
    public AppdataModel(int id, String number_of_cattles, String number_of_farmers) {
        this.id = id;
        this.number_of_cattles = number_of_cattles;
        this.number_of_farmers = number_of_farmers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey  int id;

    public String getNumber_of_cattles() {
        return number_of_cattles;
    }

    public void setNumber_of_cattles(String number_of_cattles) {
        this.number_of_cattles = number_of_cattles;
    }

    public String getNumber_of_farmers() {
        return number_of_farmers;
    }

    public void setNumber_of_farmers(String number_of_farmers) {
        this.number_of_farmers = number_of_farmers;
    }

    String number_of_cattles;
    String number_of_farmers;

}
