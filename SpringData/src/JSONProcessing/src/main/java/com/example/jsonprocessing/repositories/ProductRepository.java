package com.example.jsonprocessing.repositories;

import com.example.jsonprocessing.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByPriceBetweenAndBuyerNullOrderByPrice(BigDecimal low, BigDecimal high);
}
