package com.example.gmtandroid.postLogin.fundingDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectDetailsViewModel extends ViewModel {

    public final MutableLiveData<String> community = new MutableLiveData<>();
    public final MutableLiveData<String> femaleEmploymentTarget = new MutableLiveData<>();
    public final MutableLiveData<String> targetFundForPlanning = new MutableLiveData<>();
    public final MutableLiveData<String> targetFundForConservation = new MutableLiveData<>();
    public final MutableLiveData<String> fundRaised = new MutableLiveData<>();
    public final MutableLiveData<String> progressInText = new MutableLiveData<>();
    public int progress = 0;

    public ProjectDetailsViewModel() {
        setDefaultDetails();
    }

    private void setDefaultDetails() {
        community.postValue("");
        femaleEmploymentTarget.postValue("% of all jobs");
        targetFundForPlanning.postValue("$0");
        targetFundForConservation.postValue("$0");
    }
}
