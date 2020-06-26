package com.example.gmtandroid.postLogin.project_story;

import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;
import com.example.gmtandroid.postLogin.home.ACTIVEFUNDINGItem;
import com.example.gmtandroid.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

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
                if (response.code() > 400) {
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

    public String[] getMyProjects() {
        return myProjects.toArray(new String[myProjects.size()]);
    }

    public String[] getCaptions() {
        return new String[captions.getValue().getData().size()];
    }

}