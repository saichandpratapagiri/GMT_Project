package com.example.gmtandroid;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseViewmodel extends ViewModel {

    String Login_BASE_URL = "https://grovedev-auth.cfapps.io";
    String BASE_URL = "https://grovedev-admin-ui-backend.cfapps.io/";
    String errorMessage;

    public BaseViewmodel() {
    }

    public ApiService getApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("API CALLS", "API service created");
        return retrofit.create(ApiService.class);
    }

    public ApiService loginGetApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("API CALLS", "API service created");
        return retrofit.create(ApiService.class);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
