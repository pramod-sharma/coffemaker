package me.pramod.coffeemaker.db.repositories;

import me.pramod.coffeemaker.CoffeeMachine;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.storage.DBClient;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RecipiesRepositoryImpl implements RecipiesRepository {
    private static RecipiesRepository instance;

    private DBClient dbClient;

    private RecipiesRepositoryImpl(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public static RecipiesRepository getInstance(){
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(DBClient dbClient){
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        if (instance == null) {
            synchronized (RecipiesRepositoryImpl.class) {
                if (instance == null) {
                    instance = new RecipiesRepositoryImpl(dbClient);
                }
            }
        }
    }
    @Override
    public Map<Item, Integer> getItem(String key) throws DBClientException, RecordNotFoundException {
        String dbKey = getDBKey(key);
        return dbClient.getItem(dbKey);
    }

    @Override
    public void setItem(String key, Map<Item, Integer> item) throws DBClientException {
        String dbKey = getDBKey(key);
        dbClient.setItem(dbKey, (HashMap<Item, Integer>) item);
    }

    public String getDBKey(String key) {
        return String.format("Recipies%s%s", SEPERATOR, key);
    }
}
