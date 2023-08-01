package com.example.cncdcattleedcandroid.Network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndPoints {

    @POST("")
    Call<JsonObject> getjsonApi(@Path("id") String id);

}
