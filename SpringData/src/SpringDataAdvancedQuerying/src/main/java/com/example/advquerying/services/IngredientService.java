package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.Set;

public interface IngredientService {

    Set<Ingredient> getIngredientsByNameStartingWith(String starting);

    Set<Ingredient> getIngredientsByNames(Set<String> names);

    void deleteIngredientByName(String name);

    void updatePrice();

    void updatePriceByNames(Set<String> names);
}
