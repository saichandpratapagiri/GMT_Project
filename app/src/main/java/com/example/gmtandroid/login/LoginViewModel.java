package com.example.gmtandroid.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gmtandroid.ApiService;
import com.example.gmtandroid.BaseViewmodel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewmodel {
    private MutableLiveData<User> user;
    private LoginRequestBody loginRequestBody;

    public LoginViewModel() {}

    void setLoginRequestBody(String email, String password) {
        loginRequestBody = new LoginRequestBody();
        loginRequestBody.setEmail(email);
        loginRequestBody.setPassword(password);
    }

    MutableLiveData<User> getUser() {
        user = new MutableLiveData<>();
        loadUser();
        Log.i("API CALLS USER", user.toString());
        return user;
    }

    private void loadUser() {
        ApiService apiService = loginGetApiService();
        final Call<User> userCall = apiService.getLoginRespone(loginRequestBody);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() < 400) {
                    user.setValue(response.body());
                } else {
                    setErrorMessage(response.message());
                    user.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                setErrorMessage(t.getLocalizedMessage());
                user.setValue(null);
            }
        });
    }

}
