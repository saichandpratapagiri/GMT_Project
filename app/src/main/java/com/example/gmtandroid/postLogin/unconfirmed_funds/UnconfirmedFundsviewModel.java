package com.example.gmtandroid.postLogin.unconfirmed_funds;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;
import com.example.gmtandroid.utilities.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnconfirmedFundsviewModel extends BaseViewmodel {
    private MutableLiveData<UnconfirmedFunds> unconfirmedFunds;

    MutableLiveData<UnconfirmedFunds> getUnconfirmedFunds() {
        unconfirmedFunds = new MutableLiveData<>();
        loadUnconfirmedFunds();
        return unconfirmedFunds;
    }

    private void loadUnconfirmedFunds() {
        ApiService apiService = getApiService();
        Call<UnconfirmedFunds> unconfirmedFundsCall = apiService.getUnconfirmedFunds(Constant.shared.access_token);

        unconfirmedFundsCall.enqueue(new Callback<UnconfirmedFunds>() {
            @Override
            public void onResponse(Call<UnconfirmedFunds> call, Response<UnconfirmedFunds> response) {
                Log.i("API CALLS UNCONFUNDS", response.toString());
                if (response.code() < 400) {
                    unconfirmedFunds.postValue(response.body());
                } else {
                    setErrorMessage(response.message());
                    unconfirmedFunds.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UnconfirmedFunds> call, Throwable t) {
                Log.i("API CALLS FAILEDUNCONFUND", t.toString());
                setErrorMessage(t.getLocalizedMessage());
                unconfirmedFunds.setValue(null);
            }
        });
    }

    MutableLiveData<ResponseBody> uploadReciept(UnconfirmedFundsRequest requestBody) {
        MutableLiveData<ResponseBody> responseBody = new MutableLiveData<>();
        ApiService apiService = getApiService();
        Call<ResponseBody> responseBodyCall = apiService.uploadReciept(Constant.shared.access_token, requestBody);
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

}
