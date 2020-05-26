package com.example.gmtandroid.fundingDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectDetailsViewModel extends ViewModel {

    public final MutableLiveData<String> community = new MutableLiveData<>();
    public final MutableLiveData<String> femaleEmploymentTarget = new MutableLiveData<>();
    public final MutableLiveData<String> targetFundForPlanning = new MutableLiveData<>();
    public final MutableLiveData<String> targetFundForConservation = new MutableLiveData<>();
    public final MutableLiveData<String> fundRaised = new MutableLiveData<>();
    public final MutableLiveData<String> progressInText = new MutableLiveData<>();
    public final MutableLiveData<Integer> progress = new MutableLiveData<>();

    public ProjectDetailsViewModel() {
        setDefaultDetails();
    }

    private void setDefaultDetails() {
        community.postValue("Eket and Oron Villages");
        femaleEmploymentTarget.postValue("% of all jobs");
        targetFundForPlanning.postValue("$30,0000,00");
        targetFundForConservation.postValue("$30,0000,00");
        fundRaised.postValue("$13,000/$14,000");
        progressInText.postValue("45%");
        progress.postValue(45);
    }
}
