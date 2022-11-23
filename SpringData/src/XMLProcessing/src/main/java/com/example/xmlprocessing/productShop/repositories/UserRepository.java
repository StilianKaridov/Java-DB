package com.example.xmlprocessing.productShop.repositories;

import com.example.xmlprocessing.productShop.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerFirstName();

    List<User> findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerLastName();
}
