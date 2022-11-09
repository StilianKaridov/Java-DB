package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.Set;

public interface ShampooService {

    Set<Shampoo> getShampoosBySizeOrderedById(Size size);

    Set<Shampoo> getShampoosBySizeOrLabelIdOrderedByPrice(Size size, Long id);

    Set<Shampoo> getShampoosWithPriceHigherThan(BigDecimal price);

    Integer getCountByPriceLessThan(BigDecimal price);

    Set<Shampoo> getShampoosByIngredients(Set<String> ingredientsNames);

    Set<Shampoo> getShampoosByIngredientsCountLessThan(Integer count);
}
