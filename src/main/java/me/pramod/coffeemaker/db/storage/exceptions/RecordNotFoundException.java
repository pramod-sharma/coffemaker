package me.pramod.coffeemaker.db.storage.exceptions;

public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(String message) {
        super(message);
    }
}
