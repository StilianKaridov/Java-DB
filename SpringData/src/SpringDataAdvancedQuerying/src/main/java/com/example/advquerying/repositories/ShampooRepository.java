package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    Set<Shampoo> getShampoosBySizeOrderById(Size size);

    Set<Shampoo> getShampoosBySizeOrLabelIdOrderByPrice(Size size, Long id);

    Set<Shampoo> getShampoosByPriceIsGreaterThanOrderByPriceDesc(BigDecimal price);

    Integer countByPriceLessThan(BigDecimal price);

    @Query(value = "select s from Shampoo s " +
            "join s.ingredients i " +
            "where i.name IN (:ingredientsNames)")
    Set<Shampoo> getShampoosByIngredientsIn(Set<String> ingredientsNames);

    @Query(value = "select s from Shampoo s " +
            "where s.ingredients.size < :count")
    Set<Shampoo> getShampoosByIngredientsCountLessThan(Integer count);
}
