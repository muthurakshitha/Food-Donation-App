package com.example.projcopy;

public class posts {
    private String foodName, preparation, available,zone,address,locality,addimage,Time;

    public posts() {
        // empty constructor
        // required for Firebase.
    }

    public String getAddimage() {
        return addimage;
    }

    public void setAddimage(String addimage) {
        this.addimage = addimage;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    // Constructor for all variables.
    public posts(String foodName, String preparation, String available,String zone,String address,String locality,String addimage,String Time) {
        this.foodName = foodName;
        this.preparation = preparation;
        this.available = available;
        this.zone=zone;
        this.address=address;
        this.locality=locality;
        this.addimage=addimage;
        this.Time=Time;
    }

    // getter methods for all variables.
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPreparation() {
        return preparation;
    }

    // setter method for all variables.
    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }



}

