package com.example.springdataintro_exercise.services.category;

import com.example.springdataintro_exercise.domain.models.Category;

import java.util.Set;

public interface CategoryService {
    void add(Category category);

    Set<Category> getRandomCategories();

    boolean isDataSeeded();
}
