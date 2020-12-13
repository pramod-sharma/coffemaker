package me.pramod.coffeemaker.db.services.exceptions;

public class UnableToServerOrderException extends Exception {
    public UnableToServerOrderException(String message) {
        super(message);
    }

    public UnableToServerOrderException(Exception e) {
        super(e);
    }
}
