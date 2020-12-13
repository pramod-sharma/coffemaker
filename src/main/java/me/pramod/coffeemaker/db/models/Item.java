package me.pramod.coffeemaker.db.models;

import com.google.gson.annotations.SerializedName;

public enum Item {
     @SerializedName("hot_water")
     HotWater("hot_water"),

     @SerializedName("hot_milk")
     HotMilk("hot_milk"),

     @SerializedName("tea_leaves_syrup")
     TealLeavesSyrup("tea_leaves_syrup"),

     @SerializedName("ginger_syrup")
     GingerSyrup("ginger_syrup"),

     @SerializedName("sugar_syrup")
     SugarSyrup("sugar_syrup");

     private String name;

     Item(String name) {
          this.name = name;
     }


     @Override
     public String toString() {
          return name;
     }
}
