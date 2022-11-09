package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> getIngredientsByNameStartingWith(String starting);

    Set<Ingredient> getIngredientsByNameInOrderByPrice(Set<String> names);

    @Transactional
    @Modifying
    void deleteByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Ingredient " +
            "set price = price * 1.10")
    void updatePrice();

    @Transactional
    @Modifying
    @Query(value = "update Ingredient " +
            "set price = price * 1.10 " +
            "where name IN :names")
    void updatePriceByNames(Set<String> names);
}
