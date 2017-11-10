package com.example.computec.bakingapp;

import android.app.Application;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.computec.bakingapp.service.Constant;
import com.example.computec.bakingapp.service.Service;
import com.example.computec.bakingapp.utils.AppLogger;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static Service service;

    @Override
    public void onCreate() {
        super.onCreate();
        createApi();
        AppLogger.init();
    }

    private void createApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }
}
