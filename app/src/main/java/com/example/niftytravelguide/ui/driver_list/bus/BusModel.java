package com.example.niftytravelguide.ui.driver_list.bus;

public class BusModel {
    String busPostId, busPostImage, busName, busDescription, busLocation ,busRatting,busprice,busCounter,busNumber;

    public BusModel() {
    }

    public BusModel(String busPostId, String busPostImage, String busName, String busDescription, String busLocation, String busRatting, String busprice, String busCounter, String busNumber) {
        this.busPostId = busPostId;
        this.busPostImage = busPostImage;
        this.busName = busName;
        this.busDescription = busDescription;
        this.busLocation = busLocation;
        this.busRatting = busRatting;
        this.busprice = busprice;
        this.busCounter = busCounter;
        this.busNumber = busNumber;
    }

    public String getBusPostId() {
        return busPostId;
    }

    public void setBusPostId(String busPostId) {
        this.busPostId = busPostId;
    }

    public String getBusPostImage() {
        return busPostImage;
    }

    public void setBusPostImage(String busPostImage) {
        this.busPostImage = busPostImage;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusDescription() {
        return busDescription;
    }

    public void setBusDescription(String busDescription) {
        this.busDescription = busDescription;
    }

    public String getBusLocation() {
        return busLocation;
    }

    public void setBusLocation(String busLocation) {
        this.busLocation = busLocation;
    }

    public String getBusRatting() {
        return busRatting;
    }

    public void setBusRatting(String busRatting) {
        this.busRatting = busRatting;
    }

    public String getBusprice() {
        return busprice;
    }

    public void setBusprice(String busprice) {
        this.busprice = busprice;
    }

    public String getBusCounter() {
        return busCounter;
    }

    public void setBusCounter(String busCounter) {
        this.busCounter = busCounter;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }}
