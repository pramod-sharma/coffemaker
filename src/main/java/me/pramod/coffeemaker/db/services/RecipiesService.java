package me.pramod.coffeemaker.db.services;

import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

import java.util.Map;

public interface RecipiesService {
    public void addRecipies(Map<Beverage, Map<Item, Integer>> recipies) throws UnableToServerOrderException;

    public Map<Item, Integer> getRecipie(Beverage beverage) throws UnableToServerOrderException;
}
