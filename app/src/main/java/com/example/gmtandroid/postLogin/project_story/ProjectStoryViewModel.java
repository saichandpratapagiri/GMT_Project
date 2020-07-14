package com.example.gmtandroid.postLogin.project_story;

import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;
import com.example.gmtandroid.postLogin.home.ACTIVEFUNDINGItem;
import com.example.gmtandroid.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectStoryViewModel extends BaseViewmodel {

    private List<ACTIVEFUNDINGItem> myProjects;
    private MutableLiveData<ProjectStoryCaptions> captions;

    public ProjectStoryViewModel() {
        myProjects = new ArrayList<>();
        myProjects.addAll(Constant.shared.projectList);
        captions = new MutableLiveData<>();
    }

    MutableLiveData<ProjectStoryCaptions> getCaptionsFromServer() {
        ApiService apiService = getApiService();
        final Call<ProjectStoryCaptions> projectStoryCaptionsCall = apiService.getCaptionsForProjectStory(Constant.shared.access_token);
        projectStoryCaptionsCall.enqueue(new Callback<ProjectStoryCaptions>() {
            @Override
            public void onResponse(Call<ProjectStoryCaptions> call, Response<ProjectStoryCaptions> response) {
                if (response.code() < 400) {
                    captions.postValue(response.body());
                } else {
                    setErrorMessage("Unable to fetch captions now");
                }
            }

            @Override
            public void onFailure(Call<ProjectStoryCaptions> call, Throwable t) {
                setErrorMessage("Unable to fetch captions now");
            }
        });
        return captions;
    }

    MutableLiveData<ResponseBody> uploadStatus(ProjectStoryRequest requestBody) {
        MutableLiveData<ResponseBody> responseBody = new MutableLiveData<>();
        ApiService apiService = getApiService();
        Call<ResponseBody> responseBodyCall = apiService.uploadStatus(Constant.shared.access_token, requestBody);
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

    public List<ACTIVEFUNDINGItem> getMyProjects() {
        return myProjects;
    }

    public ProjectStoryCaptions getCaptions() {
        return captions.getValue();
    }

}