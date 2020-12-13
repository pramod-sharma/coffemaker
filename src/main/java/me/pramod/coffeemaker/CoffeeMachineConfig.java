package me.pramod.coffeemaker;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.models.Item;

import java.util.Map;

public class CoffeeMachineConfig {
    @Getter
    @Setter
    @SerializedName("machine")
    public MachineConfig Machine;

    class MachineConfig {
        @Getter
        @Setter
        @SerializedName("outlets")
        public OutletsConfig Outlets;

        @Getter
        @Setter
        @SerializedName("total_items_quantity")
        public Map<Item, Integer> TotalItemsQuantity;

        @Getter
        @Setter
        @SerializedName("beverages")
        public Map<Beverage, Map<Item, Integer>> Beverages;

        class OutletsConfig {
            @Getter
            @Setter
            @SerializedName("count_n")
            public int OutletsCount;
        }
    }
}
