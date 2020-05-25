package com.example.gmtandroid.PostLogin.unconfirmed_funds;

public class UnconfirmedFunds {
    private String imageUrl;
    private String projectName;
    private String fundedDate;
    private String fundedAmount;
    private String projectDesc;

    public UnconfirmedFunds() {
    }

    public UnconfirmedFunds(String imageUrl, String projectName, String fundedDate, String fundedAmount, String projectDesc) {
        this.imageUrl = imageUrl;
        this.projectName = projectName;
        this.fundedDate = fundedDate;
        this.fundedAmount = fundedAmount;
        this.projectDesc = projectDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFundedDate() {
        return fundedDate;
    }

    public void setFundedDate(String fundedDate) {
        this.fundedDate = fundedDate;
    }

    public String getFundedAmount() {
        return fundedAmount;
    }

    public void setFundedAmount(String fundedAmount) {
        this.fundedAmount = fundedAmount;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    @Override
    public String toString() {
        return "UnconfirmedFunds{" +
                "imageUrl='" + imageUrl + '\'' +
                ", projectName='" + projectName + '\'' +
                ", fundedDate='" + fundedDate + '\'' +
                ", fundedAmount='" + fundedAmount + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                '}';
    }
}
