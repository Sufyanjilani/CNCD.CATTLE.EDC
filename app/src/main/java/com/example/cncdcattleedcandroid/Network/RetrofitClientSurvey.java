package com.example.cncdcattleedcandroid.Network;


import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientSurvey {




    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(new BaseUrl().getbaseUlr())
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiEndPoints retrofitclient(){

        return  retrofit.create(ApiEndPoints.class);
    }
}
