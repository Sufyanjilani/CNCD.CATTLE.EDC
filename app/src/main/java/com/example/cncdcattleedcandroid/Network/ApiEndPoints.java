package com.example.cncdcattleedcandroid.Network;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @POST("farmers/{farm_id}/{farmer_id}/entities")
    Call<JsonObject> addEntity(@Path("farm_id") String farm_id,
                               @Path("farmer_id") String farmer_id,
                               @Body JsonObject jsonObject);


    @POST("cattles")
    Call<JsonObject> insertCattleGeneral(@Body JsonObject object);

    @POST("cattles/{cattle_id}/entities")
    Call<JsonObject> insertCattleEntities(@Path("cattle_id") String cattleId,@Body JsonObject object);




    @GET("dashboard")
    Call<JsonObject> getDashboardData();

    @GET("farmers/{farmID}/{farmerID}/profile")
    Call<JsonObject> getFarmerData(@Path("farmID") String farmId,
                                   @Path("farmerID") String farmerId);



    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImagesAndJson(
            @Part List<MultipartBody.Part> parts,
            @Part RequestBody body);

    @Multipart
    @POST("cattles/{cattle_id}/entities")
    Call<JsonObject> getFarmerDataMutlipart(@Path("cattle_id") String cattleID,
                                            @Part("form_data") RequestBody requestBody,
                                            @Part MultipartBody.Part part
    );


    @GET("farmers/{farmID}/{farmerID}/profile/entities")
    Call<JsonObject> getFarmerEntity(@Path("farmID") String farmId,
                                   @Path("farmerID") String farmerId);
}
