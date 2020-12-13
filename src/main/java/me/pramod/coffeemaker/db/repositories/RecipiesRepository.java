package me.pramod.coffeemaker.db.repositories;

import me.pramod.coffeemaker.db.models.Item;

import java.util.Map;

public interface RecipiesRepository extends Repository<Map<Item, Integer>> {
}
