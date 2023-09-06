package com.example.projcopy.Model;

import java.util.Date;

public class Post extends PostId{

    private String image , user , FoodName,Preparation,Available,Address;
    private Date time;

    public String getImage() {
        return image;
    }

    public String getUser() {
        return user;
    }

    public String getFoodName() {
        return FoodName;
    }

    public String getPreparation() {
        return Preparation;
    }

    public String getAvailable() {
        return Available;
    }

    public String getAddress() {
        return Address;
    }

    public Date getTime() {
        return time;
    }
}
