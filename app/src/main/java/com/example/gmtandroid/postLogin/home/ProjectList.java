package com.example.gmtandroid.postLogin.home;

import com.google.gson.annotations.SerializedName;

public class ProjectList {

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}
}