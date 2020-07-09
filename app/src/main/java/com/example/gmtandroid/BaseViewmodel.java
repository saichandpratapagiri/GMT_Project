package com.example.gmtandroid;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gmtandroid.postLogin.photo_upload_models.GetUploadUrlResponse;
import com.example.gmtandroid.postLogin.photo_upload_models.PhotoUploadRequest;
import com.example.gmtandroid.utilities.Constant;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public MutableLiveData<GetUploadUrlResponse> getUploadImageUrl(PhotoUploadRequest requestBody) {
        MutableLiveData<GetUploadUrlResponse> getUploadUrlResponse = new MutableLiveData<>();
        final Call<GetUploadUrlResponse> uploadUrlResponseCall = getApiService().getUploadURL(Constant.shared.access_token, requestBody);
        uploadUrlResponseCall.enqueue(new Callback<GetUploadUrlResponse>() {
            @Override
            public void onResponse(Call<GetUploadUrlResponse> call, Response<GetUploadUrlResponse> response) {
                if (response.code() < 400) {
                    getUploadUrlResponse.postValue(response.body());
                } else {
                    getUploadUrlResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetUploadUrlResponse> call, Throwable t) {
                getUploadUrlResponse.postValue(null);
            }
        });
        return getUploadUrlResponse;
    }

    public MutableLiveData<ResponseBody> uploadImage(MultipartBody.Part body, String url) {
        MutableLiveData<ResponseBody> responseBody = new MutableLiveData<>();
        Call<ResponseBody> responseBodyCall = getApiService().upload(url, body);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() < 400) {
                    responseBody.postValue(response.body());
                } else {
                    responseBody.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseBody.postValue(null);
            }
        });
        return responseBody;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
