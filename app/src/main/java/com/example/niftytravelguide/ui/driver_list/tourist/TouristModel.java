package com.example.niftytravelguide.ui.driver_list.tourist;

public class TouristModel {
    String touristPostId, touristImageUrl, touristName, touristDescription, touristLocation , touristRatting,touristprice,touristCounter,touristNumber;

    public TouristModel() {
    }

    public TouristModel(String touristPostId, String touristImageUrl, String touristName, String touristDescription, String touristLocation, String touristRatting, String touristprice, String touristCounter, String touristNumber) {
        this.touristPostId = touristPostId;
        this.touristImageUrl = touristImageUrl;
        this.touristName = touristName;
        this.touristDescription = touristDescription;
        this.touristLocation = touristLocation;
        this.touristRatting = touristRatting;
        this.touristprice = touristprice;
        this.touristCounter = touristCounter;
        this.touristNumber = touristNumber;
    }

    public String getTouristPostId() {
        return touristPostId;
    }

    public void setTouristPostId(String touristPostId) {
        this.touristPostId = touristPostId;
    }

    public String getTouristImageUrl() {
        return touristImageUrl;
    }

    public void setTouristImageUrl(String touristImageUrl) {
        this.touristImageUrl = touristImageUrl;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getTouristDescription() {
        return touristDescription;
    }

    public void setTouristDescription(String touristDescription) {
        this.touristDescription = touristDescription;
    }

    public String getTouristLocation() {
        return touristLocation;
    }

    public void setTouristLocation(String touristLocation) {
        this.touristLocation = touristLocation;
    }

    public String getTouristRatting() {
        return touristRatting;
    }

    public void setTouristRatting(String touristRatting) {
        this.touristRatting = touristRatting;
    }

    public String getTouristprice() {
        return touristprice;
    }

    public void setTouristprice(String touristprice) {
        this.touristprice = touristprice;
    }

    public String getTouristCounter() {
        return touristCounter;
    }

    public void setTouristCounter(String touristCounter) {
        this.touristCounter = touristCounter;
    }

    public String getTouristNumber() {
        return touristNumber;
    }

    public void setTouristNumber(String touristNumber) {
        this.touristNumber = touristNumber;
    }
}
