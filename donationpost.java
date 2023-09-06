package com.example.projcopy;

public class donationpost {
    String foodName,preparation,available;

    public donationpost(String foodName, String preparation, String available) {
        this.foodName = foodName;
        this.preparation = preparation;
        this.available = available;
    }
    public donationpost(){

    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
