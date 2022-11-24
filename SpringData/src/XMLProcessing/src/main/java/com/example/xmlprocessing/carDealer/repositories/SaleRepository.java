package com.example.xmlprocessing.carDealer.repositories;

import com.example.xmlprocessing.carDealer.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findAll();
}
