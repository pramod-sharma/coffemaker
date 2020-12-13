package me.pramod.coffeemaker.db.repositories;

import com.sleepycat.je.DatabaseException;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

public interface Repository<T> {
    public static final String SEPERATOR = "|";

    public T getItem(String key) throws DBClientException, RecordNotFoundException;

    public void setItem(String key, T item) throws DBClientException;
}
