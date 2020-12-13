package me.pramod.coffeemaker.db.services;

import me.pramod.coffeemaker.CoffeeMachine;
import me.pramod.coffeemaker.CoffeeMachineConfig;
import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.models.Item;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CoffeeServiceImpl implements CoffeeService {
    private static volatile CoffeeService instance;

    private IngredientsService ingredientsService;

    private RecipiesService recipiesService;

    public CoffeeServiceImpl(IngredientsService ingredientsService, RecipiesService recipiesService) {
        this.ingredientsService = ingredientsService;
        this.recipiesService = recipiesService;
    }

    public static CoffeeService getInstance(){
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(IngredientsService ingredientsService, RecipiesService recipiesService){
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        if (instance == null) {
            synchronized (CoffeeServiceImpl.class) {
                if (instance == null) {
                    instance = new CoffeeServiceImpl(ingredientsService, recipiesService);
                }
            }
        }
    }

    public void addIngredients(Map<Item, Integer> totalItemsQuantity) throws UnableToServerOrderException {
        ingredientsService.updateIngredients(totalItemsQuantity, IngredientsService.Operation.Add);
    }

    @Override
    public void addRecipies(Map<Beverage, Map<Item, Integer>> recipies) throws UnableToServerOrderException {
        recipiesService.addRecipies(recipies);
    }

    @Override
    public void createBeverage(Beverage beverage) throws UnableToServerOrderException {
        Map<Item, Integer> recipie = recipiesService.getRecipie(beverage);
        ingredientsService.updateIngredients(recipie, IngredientsService.Operation.Use);
        System.out.println("Created Beverage : " + beverage.toString());
    }
}
