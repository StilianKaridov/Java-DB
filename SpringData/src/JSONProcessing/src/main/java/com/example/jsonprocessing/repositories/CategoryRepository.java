package com.example.jsonprocessing.repositories;

import com.example.jsonprocessing.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select * from categories " +
            "order by rand() " +
            "limit 3", nativeQuery = true)
    List<Category> getRandomCategories();
}
