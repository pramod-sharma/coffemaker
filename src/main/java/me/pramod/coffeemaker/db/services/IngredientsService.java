package me.pramod.coffeemaker.db.services;

import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;

import java.util.Map;

public interface IngredientsService {
    public enum Operation {
        Add,
        Use
    }

    public void updateIngredients(Map<Item, Integer> totalItemsQuantity, Operation operation) throws UnableToServerOrderException;
}
