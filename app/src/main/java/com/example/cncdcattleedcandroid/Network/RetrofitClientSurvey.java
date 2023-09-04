package com.example.cncdcattleedcandroid.Network;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cncdcattleedcandroid.Session.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientSurvey {


    Context context;
    SessionManager sessionManager;
    public RetrofitClientSurvey(Context context){

        this.context = context;
        sessionManager = new SessionManager(context);
    }




    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    //SessionManager sessionManager = new SessionManager(ctx);
    OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).addInterceptor(new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request.Builder request = chain.request().newBuilder();
            String auth = "Bearer " + sessionManager.getbearer();
            request.addHeader("Authorization",auth);
            return  chain.proceed(request.build());
        }
    });

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(new BaseUrl().getbaseUlr())
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiEndPoints retrofitclient(){

        return  retrofit.create(ApiEndPoints.class);
    }
}
