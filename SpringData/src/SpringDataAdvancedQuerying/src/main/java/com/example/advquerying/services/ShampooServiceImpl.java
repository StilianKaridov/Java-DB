package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public Set<Shampoo> getShampoosBySizeOrderedById(Size size) {
        return this.shampooRepository.getShampoosBySizeOrderById(size);
    }

    @Override
    public Set<Shampoo> getShampoosBySizeOrLabelIdOrderedByPrice(Size size, Long id) {
        return this.shampooRepository.getShampoosBySizeOrLabelIdOrderByPrice(size, id);
    }

    @Override
    public Set<Shampoo> getShampoosWithPriceHigherThan(BigDecimal price) {
        return this.shampooRepository.getShampoosByPriceIsGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public Integer getCountByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public Set<Shampoo> getShampoosByIngredients(Set<String> ingredientsNames) {
        return this.shampooRepository.getShampoosByIngredientsIn(ingredientsNames);
    }

    @Override
    public Set<Shampoo> getShampoosByIngredientsCountLessThan(Integer count) {
        return this.shampooRepository.getShampoosByIngredientsCountLessThan(count);
    }
}
