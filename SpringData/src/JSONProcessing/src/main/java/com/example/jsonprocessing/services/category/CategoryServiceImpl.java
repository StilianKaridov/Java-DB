package com.example.jsonprocessing.services.category;

import com.example.jsonprocessing.domain.dtos.category.CategoryDTO;
import com.example.jsonprocessing.domain.dtos.product.ProductInRangeDTO;
import com.example.jsonprocessing.domain.entities.Category;
import com.example.jsonprocessing.domain.entities.Product;
import com.example.jsonprocessing.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public Set<Category> getRandomCategories() {
        return this.categoryRepository.getRandomCategories();
    }

    @Override
    public Set<CategoryDTO> getAllCategories() {
        Set<CategoryDTO> all = this.categoryRepository.getAllOrderByProductsSize();

        return new HashSet<>(all);
    }
}
