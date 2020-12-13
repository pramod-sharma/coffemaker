package me.pramod.coffeemaker.db.models;

import com.google.gson.annotations.SerializedName;

public enum Beverage {
    @SerializedName("hot_tea")
    HotTea("hot_tea"),

    @SerializedName("hot_coffee")
    HotCoffee("hot_coffee"),

    @SerializedName("black_tea")
    BlackTea("black_tea"),

    @SerializedName("green_tea")
    GreenTea("green_tea"),

    @SerializedName("hot_water")
    HotWater("hot_water"),

    @SerializedName("hot_milk")
    HotMilk("hot_milk");

    private String name;

    Beverage(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
