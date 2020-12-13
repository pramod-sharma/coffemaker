package me.pramod.coffeemaker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sleepycat.je.DatabaseException;
import me.pramod.coffeemaker.db.models.Beverage;
import me.pramod.coffeemaker.db.repositories.IngredientsRepository;
import me.pramod.coffeemaker.db.repositories.IngredientsRepositoryImpl;
import me.pramod.coffeemaker.db.repositories.RecipiesRepository;
import me.pramod.coffeemaker.db.repositories.RecipiesRepositoryImpl;
import me.pramod.coffeemaker.db.services.*;
import me.pramod.coffeemaker.db.services.exceptions.UnableToServerOrderException;
import me.pramod.coffeemaker.db.storage.BerkleyDBClient;
import me.pramod.coffeemaker.db.storage.DBClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class CoffeeMakerTest {
    public static void main(String[] args) throws DatabaseException, FileNotFoundException, UnableToServerOrderException {
        DBClient dbClient = BerkleyDBClient.getInstance();

        IngredientsRepositoryImpl.init(dbClient);
        IngredientsRepository ingredientsRepository = IngredientsRepositoryImpl.getInstance();

        RecipiesRepositoryImpl.init(dbClient);
        RecipiesRepository recipiesRepository = RecipiesRepositoryImpl.getInstance();

        IngredientsServiceImpl.init(ingredientsRepository);
        IngredientsService ingredientsService = IngredientsServiceImpl.getInstance();

        RecipiesServiceimpl.init(recipiesRepository);
        RecipiesService recipiesService = RecipiesServiceimpl.getInstance();

        CoffeeServiceImpl.init(ingredientsService, recipiesService);
        CoffeeService coffeeService = CoffeeServiceImpl.getInstance();

        CoffeeMachineConfig coffeeMachineConfig = loadCoffeeMachineConfig();

        CoffeeMachine.init(coffeeService, coffeeMachineConfig);
        CoffeeMachine.getInstance().requestFor(Beverage.BlackTea);
        CoffeeMachine.getInstance().requestFor(Beverage.GreenTea);
        CoffeeMachine.getInstance().requestFor(Beverage.HotMilk);
    }

    private static CoffeeMachineConfig loadCoffeeMachineConfig() throws FileNotFoundException {
        final Type COFFEE_MACHINE_CONFIG_TYPE = new TypeToken<CoffeeMachineConfig>() {}.getType();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("./data/testdata.json"));
        CoffeeMachineConfig config = gson.fromJson(reader, COFFEE_MACHINE_CONFIG_TYPE); // contains the whole reviews list
        return config;
    }
}
