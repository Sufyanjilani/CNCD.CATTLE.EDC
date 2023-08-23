package com.example.cncdcattleedcandroid.Models;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FarmerListitem{


    public FarmerListitem(String head, String desc){
        this.head = head;
        this.desc = desc;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String head;
    private String desc;


}
