package me.pramod.coffeemaker.db.services;

import me.pramod.coffeemaker.CoffeeMachine;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.repositories.IngredientsRepository;
import me.pramod.coffeemaker.db.services.exceptions.InsufficientQuantityException;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;
import me.pramod.coffeemaker.db.storage.exceptions.DBClientException;
import me.pramod.coffeemaker.db.storage.exceptions.RecordNotFoundException;

import java.util.Map;

public class IngredientsServiceImpl implements IngredientsService {
    private static IngredientsService instance;

    private IngredientsRepository ingredientsRepository;

    private IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    public static IngredientsService getInstance(){
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(IngredientsRepository ingredientsRepository){
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        if (instance == null) {
            synchronized (IngredientsServiceImpl.class) {
                if (instance == null) {
                    instance = new IngredientsServiceImpl(ingredientsRepository);
                }
            }
        }
    }

    public synchronized void  updateIngredients(Map<Item, Integer> totalItemsQuantity, Operation operation)
            throws UnableToServerOrderException {
        switch (operation) {
            case Add:
                addIngredients(totalItemsQuantity);
                break;
            case Use:
                useIngredients(totalItemsQuantity);
                break;
        }
    }

    private void useIngredients(Map<Item, Integer> itemsQuantity) throws UnableToServerOrderException {
        try {
            validateIngredientsPresence(itemsQuantity);
        } catch (DBClientException | InsufficientQuantityException e) {
            throw new UnableToServerOrderException(e);
        }

        for (Map.Entry<Item, Integer> ingredients: itemsQuantity.entrySet()) {
            Integer currentQuantity = 0;

            try {
                try {
                    currentQuantity = ingredientsRepository.getItem(ingredients.getKey().toString());
                } catch (RecordNotFoundException ex) {
                    // Means Current quantity is 0
                }

                ingredientsRepository.setItem(ingredients.getKey().toString(),
                        currentQuantity - ingredients.getValue());
            } catch (DBClientException e) {
                // Handle it
            }
        }
    }

    private void validateIngredientsPresence(Map<Item, Integer> totalItemsQuantity) throws DBClientException, InsufficientQuantityException {
        for (Map.Entry<Item, Integer> ingredients: totalItemsQuantity.entrySet()) {
            Integer neededQuantity = ingredients.getValue();
            Integer availableQuantity = 0;
            Item item = ingredients.getKey();

            if (item == null) {
                throw new InsufficientQuantityException("Base ingredients is missing from recipie");
            }

            try {
                 availableQuantity = ingredientsRepository.getItem(item.toString());
            } catch (RecordNotFoundException ex) {
                // Means Current quantity is 0
            }

            if (neededQuantity > availableQuantity) {
                String message = String.format("Sufficient Quantity of ingredients is not available for %s", item.toString());
                throw new InsufficientQuantityException(message);
            }
        }
    }

    private void addIngredients(Map<Item, Integer> totalItemsQuantity) {
        for (Map.Entry<Item, Integer> ingredients: totalItemsQuantity.entrySet()) {
            Integer currentQuantity = 0;

            try {
                try {
                    currentQuantity = ingredientsRepository.getItem(ingredients.getKey().toString());
                } catch (RecordNotFoundException ex) {
                    // Means Current quantity is 0
                }

                ingredientsRepository.setItem(ingredients.getKey().toString(),
                        currentQuantity + ingredients.getValue());
            } catch (DBClientException e) {
                System.out.println("LogException");
            }
        }
    }
}
