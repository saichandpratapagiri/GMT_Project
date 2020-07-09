package com.example.gmtandroid.postLogin.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;
import com.example.gmtandroid.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewmodel {

    private List<ACTIVEFUNDINGItem> fundList;
    private List<ACTIVEFUNDINGItem> managementList;
    private MutableLiveData<ProjectList> projectLists;

    public HomeViewModel() {
        fundList = new ArrayList<>();
        managementList = new ArrayList<>();
    }

    MutableLiveData<ProjectList> getProjectLists() {
        projectLists = new MutableLiveData<>();
        loadProjects();
        return  projectLists;
    }

    private void loadProjects() {
        ApiService apiService = getApiService();
        final Call<ProjectList> projectListCall = apiService.getProjectLists(Constant.shared.access_token);

        projectListCall.enqueue(new Callback<ProjectList>() {
            @Override
            public void onResponse(Call<ProjectList> call, Response<ProjectList> response) {
                Log.i("API CALLS PROJECTS", response.toString());
                if (response.code() < 400) {
                    projectLists.postValue(response.body());
                } else {
                    setErrorMessage(response.message());
                    projectLists.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProjectList> call, Throwable t) {
                Log.i("API CALLS PROJECTFailed", t.getMessage());
                t.printStackTrace();
                setErrorMessage(t.getLocalizedMessage());
                projectLists.setValue(null);
            }
        });
    }



    List<ACTIVEFUNDINGItem> getFundList() {
        return fundList;
    }

    void setFundList(List<ACTIVEFUNDINGItem> list) {
        this.fundList = list;
    }

    List<ACTIVEFUNDINGItem> getManagementList() {
        return managementList;
    }

    void setManagementList(List<ACTIVEFUNDINGItem> list) {
        this.managementList = list;
    }
}