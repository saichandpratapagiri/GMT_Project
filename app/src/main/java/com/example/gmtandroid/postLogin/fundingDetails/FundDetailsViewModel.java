package com.example.gmtandroid.postLogin.fundingDetails;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;
import com.example.gmtandroid.utilities.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FundDetailsViewModel extends BaseViewmodel {
    private MutableLiveData<FundingDetailsModel> fundingDetailsModel;
    private ProjectDetailsViewModel projectDetailsViewModel;
    private LocationMapViewModel locationMapViewModel;

    public FundDetailsViewModel() {
        projectDetailsViewModel = new ProjectDetailsViewModel();
        locationMapViewModel = new LocationMapViewModel();
    }

    MutableLiveData<FundingDetailsModel> getFundingDetails(int id) {
        fundingDetailsModel = new MutableLiveData<>();
        loadFundingDetails(id);
        return  fundingDetailsModel;
    }

    private void loadFundingDetails(int projectId) {
        ApiService apiService = getApiService();
        final Call<FundingDetailsModel> fundingDetailsModelCall = apiService.getFundingDetails(Constant.shared.access_token, projectId);
        fundingDetailsModelCall.enqueue(new Callback<FundingDetailsModel>() {
            @Override
            public void onResponse(Call<FundingDetailsModel> call, Response<FundingDetailsModel> response) {
                Log.i("API CALLS project "+projectId, response.toString());
                if (response.code() < 400 ) {
                    fundingDetailsModel.setValue(response.body());
                    setprojectDetails();
                    setLocationDetails();
                } else {
                    setErrorMessage(response.message());
                    fundingDetailsModel.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<FundingDetailsModel> call, Throwable t) {
                Log.i("API CALLS FUNDETFailed", t.getMessage());
                t.printStackTrace();
                setErrorMessage(t.getLocalizedMessage());
                fundingDetailsModel.setValue(null);
            }
        });
    }

    private void setprojectDetails() {
        try {
            Data data = fundingDetailsModel.getValue().getData();
            projectDetailsViewModel.community.postValue(data.getCommunity());
            projectDetailsViewModel.femaleEmploymentTarget.postValue(data.getFemaleEmpTarget().toString() + "% of jobs");
            projectDetailsViewModel.fundRaised.postValue(data.getFundsRaised().toString()+"/"+data.getTotalFundingTarget().toString());
            projectDetailsViewModel.progress = data.getFundsRaised()*100/data.getTotalFundingTarget();
            projectDetailsViewModel.progressInText.postValue(projectDetailsViewModel.progress + " %");
            projectDetailsViewModel.targetFundForConservation.postValue("$" + data.getConservationBalance().toString());
            projectDetailsViewModel.targetFundForPlanning.postValue("$" + data.getConservationBalance().toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setLocationDetails() {
        try {
            Data data = fundingDetailsModel.getValue().getData();
            MapSegmentation mapSegmentation = fundingDetailsModel.getValue().getData().getMapSegmentation().get(0);
            locationMapViewModel.plantingArea.postValue(data.getPlantationSize() + "Hectares");
            locationMapViewModel.density.postValue(data.getPlantingDensity() + "Trees/Hectare");
            locationMapViewModel.totalTrees.postValue(data.getTotalNoOfTrees().toString() + "Trees");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProjectDetailsViewModel getProjectDetailsViewModel() {
        return projectDetailsViewModel;
    }

    public LocationMapViewModel getLocationMapViewModel() {
        return locationMapViewModel;
    }
}
