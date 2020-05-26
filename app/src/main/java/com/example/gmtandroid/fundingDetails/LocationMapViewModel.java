package com.example.gmtandroid.fundingDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationMapViewModel extends ViewModel {

    public final MutableLiveData<String> plantingArea = new MutableLiveData<>();
    public final MutableLiveData<String> density = new MutableLiveData<>();
    public final MutableLiveData<String> totalTrees = new MutableLiveData<>();

    public LocationMapViewModel() {
        setDefaultData();
    }

    private void setDefaultData() {
        plantingArea.postValue("00,000 Hectares");
        density.postValue("00,000 Trees/Hectare");
        totalTrees.postValue("00,000 Trees");
    }
}
