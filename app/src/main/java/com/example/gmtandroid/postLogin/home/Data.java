package com.example.gmtandroid.postLogin.home;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("ACTIVE_FUNDING")
	private List<ACTIVEFUNDINGItem> aCTIVEFUNDING;

	@SerializedName("ACTIVE_MANAGEMENT")
	private List<ACTIVEFUNDINGItem> aCTIVEMANAGEMENT;

	@SerializedName("NOT_YET_DEPLOYED")
	private List<ACTIVEFUNDINGItem> nOTYETDEPLOYED;

	public void setACTIVEFUNDING(List<ACTIVEFUNDINGItem> aCTIVEFUNDING){
		this.aCTIVEFUNDING = aCTIVEFUNDING;
	}

	public List<ACTIVEFUNDINGItem> getACTIVEFUNDING(){
		return aCTIVEFUNDING;
	}

	public void setACTIVEMANAGEMENT(List<ACTIVEFUNDINGItem> aCTIVEMANAGEMENT){
		this.aCTIVEMANAGEMENT = aCTIVEMANAGEMENT;
	}

	public List<ACTIVEFUNDINGItem> getACTIVEMANAGEMENT(){
		return aCTIVEMANAGEMENT;
	}

	public void setNOTYETDEPLOYED(List<ACTIVEFUNDINGItem> nOTYETDEPLOYED){
		this.nOTYETDEPLOYED = nOTYETDEPLOYED;
	}

	public List<ACTIVEFUNDINGItem> getNOTYETDEPLOYED(){
		return nOTYETDEPLOYED;
	}
}