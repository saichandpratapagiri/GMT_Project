package com.example.gmtandroid.postLogin.fundingDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FundingDetailsModel {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("map_segmentation")
    @Expose
    private List<MapSegmentation> mapSegmentation = null;
    @SerializedName("plantation_size")
    @Expose
    private String plantationSize;
    @SerializedName("planting_density")
    @Expose
    private String plantingDensity;
    @SerializedName("total_no_of_trees")
    @Expose
    private Integer totalNoOfTrees;
    @SerializedName("community")
    @Expose
    private String community;
    @SerializedName("female_emp_target")
    @Expose
    private Integer femaleEmpTarget;
    @SerializedName("planting_balance")
    @Expose
    private Integer plantingBalance;
    @SerializedName("conservation_balance")
    @Expose
    private Integer conservationBalance;
    @SerializedName("funds_raised")
    @Expose
    private Integer fundsRaised;
    @SerializedName("total_funding_target")
    @Expose
    private Integer totalFundingTarget;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<MapSegmentation> getMapSegmentation() {
        return mapSegmentation;
    }

    public void setMapSegmentation(List<MapSegmentation> mapSegmentation) {
        this.mapSegmentation = mapSegmentation;
    }

    public String getPlantationSize() {
        return plantationSize;
    }

    public void setPlantationSize(String plantationSize) {
        this.plantationSize = plantationSize;
    }

    public String getPlantingDensity() {
        return plantingDensity;
    }

    public void setPlantingDensity(String plantingDensity) {
        this.plantingDensity = plantingDensity;
    }

    public Integer getTotalNoOfTrees() {
        return totalNoOfTrees;
    }

    public void setTotalNoOfTrees(Integer totalNoOfTrees) {
        this.totalNoOfTrees = totalNoOfTrees;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Integer getFemaleEmpTarget() {
        return femaleEmpTarget;
    }

    public void setFemaleEmpTarget(Integer femaleEmpTarget) {
        this.femaleEmpTarget = femaleEmpTarget;
    }

    public Integer getPlantingBalance() {
        return plantingBalance;
    }

    public void setPlantingBalance(Integer plantingBalance) {
        this.plantingBalance = plantingBalance;
    }

    public Integer getConservationBalance() {
        return conservationBalance;
    }

    public void setConservationBalance(Integer conservationBalance) {
        this.conservationBalance = conservationBalance;
    }

    public Integer getFundsRaised() {
        return fundsRaised;
    }

    public void setFundsRaised(Integer fundsRaised) {
        this.fundsRaised = fundsRaised;
    }

    public Integer getTotalFundingTarget() {
        return totalFundingTarget;
    }

    public void setTotalFundingTarget(Integer totalFundingTarget) {
        this.totalFundingTarget = totalFundingTarget;
    }

}


class MapSegmentation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("coordinates")
    @Expose
    private List<Coordinate> coordinates = null;
    @SerializedName("plantingArea")
    @Expose
    private Double plantingArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Double getPlantingArea() {
        return plantingArea;
    }

    public void setPlantingArea(Double plantingArea) {
        this.plantingArea = plantingArea;
    }

}

class Coordinate {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}





