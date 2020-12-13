package me.pramod.coffeemaker.db.services.exceptions;

public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
