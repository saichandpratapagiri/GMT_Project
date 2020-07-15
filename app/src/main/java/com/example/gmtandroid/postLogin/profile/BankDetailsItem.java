package com.example.gmtandroid.postLogin.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetailsItem{
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("acc_no")
    @Expose
    private Integer accNo;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("iban")
    @Expose
    private String iban;

    public BankDetailsItem() {
    }

    public BankDetailsItem(String bankName, Integer accNo, String branch, String iban) {
        this.bankName = bankName;
        this.accNo = accNo;
        this.branch = branch;
        this.iban = iban;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getAccNo() {
        return accNo;
    }

    public void setAccNo(Integer accNo) {
        this.accNo = accNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }


}
