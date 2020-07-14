package com.example.gmtandroid.postLogin.profile;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;
import com.example.gmtandroid.postLogin.photo_upload_models.GetUploadUrlResponse;
import com.example.gmtandroid.utilities.Constant;
import com.google.gson.Gson;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewmodel {
    private MutableLiveData<ProfileModel> profileModel;
    private MutableLiveData<ProfileRequestBody> profileRequestBody;
    private MutableLiveData<GetUploadUrlResponse> getUploadUrlResponse;

    public ProfileViewModel() {
        profileModel = new MutableLiveData<>();
        profileRequestBody = new MutableLiveData<>();
        getUploadUrlResponse = new MutableLiveData<>();
    }

    public MutableLiveData<ProfileModel> getProfile() {
        loadProfile();
        return profileModel;
    }

    private void loadProfile() {
        ApiService apiService = getApiService();
        final Call<ProfileModel> profileModelCall = apiService.getProfile(Constant.shared.access_token);

        profileModelCall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                Log.i("API CALLS PROFILE", response.toString());
                if (response.code() < 400) {
                    Gson gson = new Gson();
                    Constant.shared.gsonProfile = gson.toJson(response.body());
                    profileModel.setValue(response.body());

                } else {
                    setErrorMessage(response.message());
                    profileModel.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.i("API CALLS PROFIILFailed", t.toString());
                t.printStackTrace();
                setErrorMessage(t.getLocalizedMessage());
                profileModel.setValue(null);
            }
        });
    }

    public MutableLiveData<ProfileRequestBody> setProfile() {
        ApiService apiService = getApiService();
        final Call<ProfileRequestBody> profileRequestBodyCall = apiService.setProfile(Constant.shared.access_token, getProfileRequestBody());

        profileRequestBodyCall.enqueue(new Callback<ProfileRequestBody>() {
            @Override
            public void onResponse(Call<ProfileRequestBody> call, Response<ProfileRequestBody> response) {
                Log.i("API CALLS SETPROFILE", response.toString());
                profileRequestBody.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProfileRequestBody> call, Throwable t) {
                Log.i("API CALLS SETPRFFAILED", t.toString());
                setErrorMessage(t.getLocalizedMessage());
                profileRequestBody.setValue(null);
            }
        });

        return profileRequestBody;
    }

    public ProfileModel getProfileModel() {
        return profileModel.getValue();
    }

    public ProfileRequestBody getProfileRequestBody() {
        return profileRequestBody.getValue();
    }

    public void setProfileRequestBody(ProfileRequestBody profileRequest) {
        if (profileRequest != null)
          profileRequestBody.setValue(profileRequest);
    }
}
