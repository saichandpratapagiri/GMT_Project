package com.example.gmtandroid;

import com.example.gmtandroid.login.*;
import com.example.gmtandroid.postLogin.fundingDetails.FundingDetailsModel;
import com.example.gmtandroid.postLogin.profile.ProfileModel;
import com.example.gmtandroid.postLogin.home.ProjectList;
import com.example.gmtandroid.postLogin.profile.ProfileRequestBody;
import com.example.gmtandroid.postLogin.project_story.ProjectStoryCaptions;
import com.example.gmtandroid.postLogin.unconfirmed_funds.UnconfirmedFunds;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @Headers({
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"})
    @POST("/api/tf/user/login")
    Call<User> getLoginRespone(@Body LoginRequestBody requestBody);

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept-Encoding: gzip,deflate",
            "Accept: application/json"
            })
    @GET("v1/planter-project-list")
    Call<ProjectList> getProjectLists(@Header("Authorization") String header);

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept-Encoding: gzip,deflate",
            "Accept: application/json"
    })
    @GET("v1/planter-user")
    Call<ProfileModel> getProfile(@Header("Authorization") String header);

    @Headers({
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"})
    @PUT("v1/planter-user")
    Call<ProfileRequestBody> setProfile(@Header("Authorization") String header, @Body ProfileRequestBody profileRequestBody);

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept-Encoding: gzip,deflate",
            "Accept: application/json"
    })
    @GET("v1/planter-project-details/{id}")
    Call<FundingDetailsModel> getFundingDetails(@Header("Authorization") String header, @Path("id") int id);

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept-Encoding: gzip,deflate",
            "Accept: application/json"
    })
    @GET("v1/planter-logFunds-send")
    Call<UnconfirmedFunds> getUnconfirmedFunds(@Header("Authorization") String header);

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept-Encoding: gzip,deflate",
            "Accept: application/json"
    })
    @GET("v1/planter-captions")
    Call<ProjectStoryCaptions> getCaptionsForProjectStory(@Header("Authorization") String header);
}
