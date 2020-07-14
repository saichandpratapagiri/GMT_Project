package com.example.gmtandroid.postLogin.unconfirmed_funds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnconfirmedFundsRequest {

    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}

class Attributes {

    @SerializedName("fund_id")
    @Expose
    private Integer fundId;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("pic")
    @Expose
    private String pic;

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}

