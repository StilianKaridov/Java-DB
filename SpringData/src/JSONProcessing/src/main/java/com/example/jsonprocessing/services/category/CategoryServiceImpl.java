package com.example.jsonprocessing.services.category;

import com.example.jsonprocessing.domain.entities.Category;
import com.example.jsonprocessing.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getRandomCategories() {
        return this.categoryRepository.getRandomCategories();
    }
}
