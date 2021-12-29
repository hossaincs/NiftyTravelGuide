package com.example.niftytravelguide.ui.driver_list.air;

public class AirModel {
    String airPostId, airPostImage, airName, airDescription, airLocation ,airRatting,airPrice,airCounter,airNumber;

    public AirModel() {
    }

    public AirModel(String airPostId, String airPostImage, String airName, String airDescription, String airLocation, String airRatting, String airPrice, String airCounter, String airNumber) {
        this.airPostId = airPostId;
        this.airPostImage = airPostImage;
        this.airName = airName;
        this.airDescription = airDescription;
        this.airLocation = airLocation;
        this.airRatting = airRatting;
        this.airPrice = airPrice;
        this.airCounter = airCounter;
        this.airNumber = airNumber;
    }

    public String getAirPostId() {
        return airPostId;
    }

    public void setAirPostId(String airPostId) {
        this.airPostId = airPostId;
    }

    public String getAirPostImage() {
        return airPostImage;
    }

    public void setAirPostImage(String airPostImage) {
        this.airPostImage = airPostImage;
    }

    public String getAirName() {
        return airName;
    }

    public void setAirName(String airName) {
        this.airName = airName;
    }

    public String getAirDescription() {
        return airDescription;
    }

    public void setAirDescription(String airDescription) {
        this.airDescription = airDescription;
    }

    public String getAirLocation() {
        return airLocation;
    }

    public void setAirLocation(String airLocation) {
        this.airLocation = airLocation;
    }

    public String getAirRatting() {
        return airRatting;
    }

    public void setAirRatting(String airRatting) {
        this.airRatting = airRatting;
    }

    public String getAirPrice() {
        return airPrice;
    }

    public void setAirPrice(String airPrice) {
        this.airPrice = airPrice;
    }

    public String getAirCounter() {
        return airCounter;
    }

    public void setAirCounter(String airCounter) {
        this.airCounter = airCounter;
    }

    public String getAirNumber() {
        return airNumber;
    }

    public void setAirNumber(String airNumber) {
        this.airNumber = airNumber;
    }
}
