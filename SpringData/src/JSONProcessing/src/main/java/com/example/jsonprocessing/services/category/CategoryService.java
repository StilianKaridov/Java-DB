package com.example.jsonprocessing.services.category;

import com.example.jsonprocessing.domain.dtos.category.CategoryDTO;
import com.example.jsonprocessing.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Set<Category> getRandomCategories();

    Set<CategoryDTO> getAllCategories();
}
