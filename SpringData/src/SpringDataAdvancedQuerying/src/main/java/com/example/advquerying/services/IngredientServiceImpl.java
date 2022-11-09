package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<Ingredient> getIngredientsByNameStartingWith(String starting) {
        return this.ingredientRepository.getIngredientsByNameStartingWith(starting);
    }

    @Override
    public Set<Ingredient> getIngredientsByNames(Set<String> names) {
        return this.ingredientRepository.getIngredientsByNameInOrderByPrice(names);
    }

    @Override
    public void deleteIngredientByName(String name) {
        this.ingredientRepository.deleteByName(name);
    }

    @Override
    public void updatePrice() {
        this.ingredientRepository.updatePrice();
    }

    @Override
    public void updatePriceByNames(Set<String> names) {
        this.ingredientRepository.updatePriceByNames(names);
    }
}
