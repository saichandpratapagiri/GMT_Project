package com.example.gmtandroid.postLogin.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("first_name")
	@Expose
	private String firstName;
	@SerializedName("last_name")
	@Expose
	private String lastName;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("phone_no")
	@Expose
	private String phoneNo;
	@SerializedName("pic")
	@Expose
	private String pic;
	@SerializedName("addr")
	@Expose
	private String addr;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("total_funds")
	@Expose
	private Double totalFunds;
	@SerializedName("bankDetails")
	@Expose
	private List<BankDetailsItem> bankDetails = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(Double totalFunds) {
		this.totalFunds = totalFunds;
	}

	public List<BankDetailsItem> getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(List<BankDetailsItem> bankDetails) {
		this.bankDetails = bankDetails;
	}

}
