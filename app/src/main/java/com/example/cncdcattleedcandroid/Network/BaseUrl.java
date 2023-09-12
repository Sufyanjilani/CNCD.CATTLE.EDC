package com.example.cncdcattleedcandroid.Network;

import com.example.cncdcattleedcandroid.BuildConfig;

public class BaseUrl {

    String url = "http://192.168.20.136:8001/api/";
    String newurl= "http://192.168.20.222/CNCDDemos/json/";

    String baseurl3 = "https://5f8e-103-131-10-138.ngrok-free.app/demo/";

    String baseurl4= "http://192.168.20.136:8888/demo/";

    String baseurl5 = "http://192.168.20.136:8888/api/v1/";

    String BuilConfigUrl = BuildConfig.API_BASE_URL;



    public String getbaseUlr(){
        return BuilConfigUrl;

    }
}
