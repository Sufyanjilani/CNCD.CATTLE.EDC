package com.example.cncdcattleedcandroid.Network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndPoints {

    @POST("form_json/{id}")
    Call<JsonObject> getjsonApi(@Path("id") String id);

    @GET("cities")
    Call<JsonObject> getcities();

    @GET("config/questionnaires/{id}")
    Call<JsonObject> getSurvey(@Path("id") String id);

    @POST("auth/login")
    Call<JsonObject> login(@Body JsonObject loginobject);

    @POST("auth/logout")
    Call<JsonObject> logout(@Body JsonObject logout);

    @POST("farmers")
    Call<JsonObject> Addfarmer(@Body JsonObject object);

    @POST("farmers/{farm_id}/{farmer_id}/{entities}")
    Call<JsonObject> addEntity(@Path("farm_id") String farm_id,
                               @Path("farmer_id") String farmer_id,
                               @Path("entities") String entities, @Body JsonObject jsonObject);


    @GET("dashboard")
    Call<JsonObject> getDashboardData();







}
