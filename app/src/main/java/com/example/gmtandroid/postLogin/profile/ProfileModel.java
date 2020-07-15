package com.example.gmtandroid.postLogin.profile;

public class ProfileModel{
	private Data data;

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ProfileModel{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}
