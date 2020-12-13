package me.pramod.coffeemaker.db.repositories;

import com.sleepycat.je.DatabaseException;
import me.pramod.coffeemaker.CoffeeMachine;
import me.pramod.coffeemaker.db.services.IngredientsService;
import me.pramod.coffeemaker.db.services.IngredientsServiceImpl;
import me.pramod.coffeemaker.db.storage.DBClient;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

public class IngredientsRepositoryImpl implements IngredientsRepository {
    private static IngredientsRepository instance;

    private DBClient dbClient;

    private IngredientsRepositoryImpl(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public static IngredientsRepository getInstance(){
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
            synchronized (IngredientsRepositoryImpl.class) {
                if (instance == null) {
                    instance = new IngredientsRepositoryImpl(dbClient);
                }
            }
        }
    }
    public Integer getItem(String key) throws DBClientException, RecordNotFoundException {
        String dbKey = getDBKey(key);
        return dbClient.getItem(dbKey);
    }

    public void setItem(String key, Integer item) throws DBClientException {
        String dbKey = getDBKey(key);
        dbClient.setItem(dbKey, item);
    }

    public String getDBKey(String key) {
        return String.format("Ingredients%s%s", SEPERATOR, key);
    }
}
