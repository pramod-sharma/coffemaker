package me.pramod.coffeemaker;

import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.services.CoffeeService;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CoffeeMachine {
    private static volatile CoffeeMachine instance;

    private CoffeeService coffeeService;

    private ExecutorService executorService;

    private CoffeeMachine(CoffeeService coffeeService, CoffeeMachineConfig config ) {
        this.coffeeService = coffeeService;
        this.executorService = new ThreadPoolExecutor(config.Machine.Outlets.OutletsCount,
                config.Machine.Outlets.OutletsCount, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(100));
        loadIngredients(config.Machine.TotalItemsQuantity);
        loadBeveragesRecipie(config.Machine.Beverages);
    }

    public static CoffeeMachine getInstance(){
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(CoffeeService coffeeService, CoffeeMachineConfig config){
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        if (instance == null) {
            synchronized (CoffeeMachine.class) {
                if (instance == null) {
                    instance = new CoffeeMachine(coffeeService, config);
                }
            }
        }
    }

    private void loadIngredients(Map<Item, Integer> totalItemsQuantity) {
        try {
            coffeeService.addIngredients(totalItemsQuantity);
        } catch (UnableToServerOrderException ex) {
            System.out.println("Unable to add new items");
        }
    }

    private void loadBeveragesRecipie(Map<Beverage, Map<Item, Integer>> recipies) {
        try {
            coffeeService.addRecipies(recipies);
        } catch (UnableToServerOrderException ex) {
            System.out.println("Unable to add new items");
        }
    }


    public void requestFor(final Beverage beverage) throws UnableToServerOrderException {
        executorService.submit(() -> {
            try {
                coffeeService.createBeverage(beverage);
            } catch (UnableToServerOrderException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
