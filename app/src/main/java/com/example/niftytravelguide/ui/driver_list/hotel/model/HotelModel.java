package com.example.niftytravelguide.ui.driver_list.hotel.model;

public class HotelModel {
    String hotelpostid, hotelpostimage, hotelname, hoteldescription, hotellocation, hotelratting,hotelprice,hotelCounter,hotelNumber;



    public HotelModel() {
    }

    public String getHotelPostId() {
        return hotelpostid;
    }

    public void setHotelPostId(String hotelPostId) {
        this.hotelpostid = hotelPostId;
    }

    public String getHotelpostimage() {
        return hotelpostimage;
    }

    public void setHotelpostimage(String hotelpostimage) {
        this.hotelpostimage = hotelpostimage;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHoteldescription() {
        return hoteldescription;
    }

    public void setHoteldescription(String hoteldescription) {
        this.hoteldescription = hoteldescription;
    }

    public String getHotellocation() {
        return hotellocation;
    }

    public void setHotellocation(String hotellocation) {
        this.hotellocation = hotellocation;
    }

    public String getHotelratting() {
        return hotelratting;
    }

    public void setHotelratting(String hotelratting) {
        this.hotelratting = hotelratting;
    }

    public String getHotelprice() {
        return hotelprice;
    }

    public void setHotelprice(String hotelprice) {
        this.hotelprice = hotelprice;
    }

    public String getHotelCounter() {
        return hotelCounter;
    }

    public void setHotelCounter(String hotelCounter) {
        this.hotelCounter = hotelCounter;
    }

    public String getHotelNumber() {
        return hotelNumber;
    }

    public void setHotelNumber(String hotelNumber) {
        this.hotelNumber = hotelNumber;
    }

    public HotelModel(String hotelPostId, String hotelpostimage, String hotelname, String hoteldescription, String hotellocation, String hotelratting, String hotelprice, String hotelCounter, String hotelNumber) {
        this.hotelpostid = hotelPostId;
        this.hotelpostimage = hotelpostimage;
        this.hotelname = hotelname;
        this.hoteldescription = hoteldescription;
        this.hotellocation = hotellocation;
        this.hotelratting = hotelratting;
        this.hotelprice = hotelprice;
        this.hotelCounter = hotelCounter;
        this.hotelNumber = hotelNumber;
    }}
