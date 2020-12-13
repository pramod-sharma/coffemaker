package me.pramod.coffeemaker.db.services;

import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;

import java.util.Map;

public interface CoffeeService {
    public void addIngredients(Map<Item, Integer> totalItemsQuantity) throws UnableToServerOrderException;

    public void addRecipies(Map<Beverage, Map<Item, Integer>> recipies) throws UnableToServerOrderException;

    public void createBeverage(Beverage beverage) throws UnableToServerOrderException;
}
