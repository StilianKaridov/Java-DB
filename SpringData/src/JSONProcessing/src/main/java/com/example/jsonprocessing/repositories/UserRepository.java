package com.example.jsonprocessing.repositories;

import com.example.jsonprocessing.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users " +
            "order by rand() " +
            "limit 1", nativeQuery = true)
    User getRandomUser();

    @Query(value = "select * from users " +
            "order by rand() " +
            "limit 3", nativeQuery = true)
    List<User> getRandomFriends();

    List<User> findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerFirstName();
}
