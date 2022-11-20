package com.example.jsonprocessing.repositories;

import com.example.jsonprocessing.domain.dtos.category.CategoryDTO;
import com.example.jsonprocessing.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select * from categories " +
            "order by rand() " +
            "limit 3", nativeQuery = true)
    Set<Category> getRandomCategories();

    @Query("select new com.example.jsonprocessing.domain.dtos.category.CategoryDTO" +
            "(c.name, count(p.id), avg(p.price), sum(p.price)) " +
            "from Product p " +
            "join p.categories c " +
            "group by c.id " +
            "order by count(p.id)")
    Set<CategoryDTO> getAllOrderByProductsSize();
}
