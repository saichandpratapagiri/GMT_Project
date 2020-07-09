package com.example.gmtandroid.postLogin.photo_upload_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUploadUrlResponse {
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getUploadurl() {
        return getData().get(0).getUploadUrl();
    }

    public String getImageurl() {
        return getData().get(0).getImageUrl();
    }

}

