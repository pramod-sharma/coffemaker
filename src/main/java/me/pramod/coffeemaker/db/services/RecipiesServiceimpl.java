package me.pramod.coffeemaker.db.services;

import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.repositories.RecipiesRepository;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

import java.util.Map;

public class RecipiesServiceimpl implements RecipiesService {
    private static RecipiesService instance;

    private RecipiesRepository recipiesRepository;

    private RecipiesServiceimpl(RecipiesRepository recipiesRepository) {
        this.recipiesRepository = recipiesRepository;
    }

    public static RecipiesService getInstance(){
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(RecipiesRepository recipiesRepository){
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        if (instance == null) {
            synchronized (RecipiesServiceimpl.class) {
                if (instance == null) {
                    instance = new RecipiesServiceimpl(recipiesRepository);
                }
            }
        }
    }


    @Override
    public void addRecipies(Map<Beverage, Map<Item, Integer>> recipies) throws UnableToServerOrderException {
        try {
            for (Map.Entry<Beverage, Map<Item, Integer>> recipie: recipies.entrySet()) {
                recipiesRepository.setItem(recipie.getKey().toString(), recipie.getValue());
            }
        } catch (DBClientException ex) {
            throw new UnableToServerOrderException("Unable to load recipies. Can't continue.");
        }
    }

    @Override
    public Map<Item, Integer> getRecipie(Beverage beverage) throws UnableToServerOrderException {
        try {
            return recipiesRepository.getItem(beverage.toString());
        } catch (RecordNotFoundException ex) {
            throw new UnableToServerOrderException(String.format("Recipie for beverage %s not found", beverage));
        } catch (DBClientException ex) {
            throw new UnableToServerOrderException("Unable to connect with DB. can't serve order right now");
        }
    }
}
