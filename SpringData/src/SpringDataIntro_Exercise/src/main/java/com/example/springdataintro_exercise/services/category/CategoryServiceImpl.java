package com.example.springdataintro_exercise.services.category;

import com.example.springdataintro_exercise.domain.models.Category;
import com.example.springdataintro_exercise.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void add(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public Set<Category> getRandomCategories() {
        int count = (int) this.categoryRepository.count();

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ids.add(new Random().nextInt(count) + 1);
        }

        return this.categoryRepository.getCategoriesByIdIn(ids);
    }

    @Override
    public boolean isDataSeeded() {
        return this.categoryRepository.count() > 0;
    }
}
