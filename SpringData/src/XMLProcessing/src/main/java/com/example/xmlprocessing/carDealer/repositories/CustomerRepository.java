package com.example.xmlprocessing.carDealer.repositories;

import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
