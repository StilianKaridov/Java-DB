package com.example.springdataintro_exercise.repositories;

import com.example.springdataintro_exercise.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Set<Category> getCategoriesByIdIn(List<Integer> ids);
}
