package me.pramod.coffeemaker.db.storage;

import com.sleepycat.je.DatabaseException;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

import java.io.Serializable;

public interface DBClient {
    public <T extends Serializable> T getItem(String key) throws RecordNotFoundException, DBClientException;

    public <T extends Serializable> void setItem(String key, T item) throws DBClientException;
}
