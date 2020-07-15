package com.example.gmtandroid.postLogin.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ACTIVEFUNDINGItem implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("thumbnail_url")
	private String thumbnailUrl;

	@SerializedName("funding_status")
	private String fundingStatus;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setThumbnailUrl(String thumbnailUrl){
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getThumbnailUrl(){
		return thumbnailUrl;
	}

	public void setFundingStatus(String fundingStatus){
		this.fundingStatus = fundingStatus;
	}

	public String getFundingStatus(){
		return fundingStatus;
	}
}