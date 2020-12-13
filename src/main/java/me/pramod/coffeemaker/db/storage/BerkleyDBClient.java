package me.pramod.coffeemaker.db.storage;

import com.sleepycat.je.*;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.Serializable;

public class BerkleyDBClient implements DBClient {
    private static volatile BerkleyDBClient instance;

    private static final String DbName = "CoffeeMachine";

    private Environment dbEnvironment;

    private Database database = null;

    private BerkleyDBClient() throws DatabaseException {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        this.dbEnvironment = new Environment(new File("./data/db"), envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        this.database = dbEnvironment.openDatabase(null, DbName, dbConfig);
    }

    public static  BerkleyDBClient getInstance() throws DatabaseException {
        if (instance == null){
            synchronized (BerkleyDBClient.class) {
                if (instance == null){
                    instance = new BerkleyDBClient();
                }
            }
        }
        return instance;
    }

    public <T extends Serializable> T getItem (String key) throws RecordNotFoundException, DBClientException {
        T foundData = null;
        DatabaseEntry theKey = new DatabaseEntry(SerializationUtils.serialize(key));
        DatabaseEntry theData = new DatabaseEntry();

        try {
            // Call get() to query the database
            if (database.get(null, theKey, theData, LockMode.DEFAULT) ==
                    OperationStatus.SUCCESS) {

                // Translate theData into a String.
                byte[] retData = theData.getData();
                foundData = SerializationUtils.deserialize(retData);
                return foundData;
            }
        } catch (DatabaseException ex) {
            throw new DBClientException(ex.getMessage());
        }

        throw new RecordNotFoundException("No record found with key :- " + key);
    }

    public <T extends Serializable> void setItem(String key, T item) throws DBClientException {
        try {
            DatabaseEntry theKey = new DatabaseEntry(SerializationUtils.serialize(key));
            DatabaseEntry theData = new DatabaseEntry(SerializationUtils.serialize(item));
            database.put(null, theKey, theData);
            dbEnvironment.sync();
        } catch (DatabaseException ex) {
            throw new DBClientException(ex.getMessage());
        }
    }
}
