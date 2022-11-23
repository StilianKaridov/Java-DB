package com.example.xmlprocessing.productShop.repositories;

import com.example.xmlprocessing.productShop.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetweenAndBuyerNullOrderByPrice(BigDecimal low, BigDecimal high);
}
