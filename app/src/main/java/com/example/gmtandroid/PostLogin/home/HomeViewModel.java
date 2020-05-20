package com.example.gmtandroid.PostLogin.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private ArrayList<String> fundList, managementList;

    public HomeViewModel() {
        fundList = new ArrayList<>();
        managementList = new ArrayList<>();
        fundList.add("Stubbs Creek Forest Reserve");
        fundList.add("Crab island Wetland");
        fundList.add("Myeik U Ayayarwaddy");
        fundList.add("Thor Heyerdahi Climate Park");
        managementList.add("Mangrove pak 1");
        managementList.add("Mangrove park 2");
    }

    public ArrayList<String> getFundList() {
        return fundList;
    }

    public ArrayList<String> getManagementList() {
        return managementList;
    }
}