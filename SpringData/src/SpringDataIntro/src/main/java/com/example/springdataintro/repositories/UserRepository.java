package com.example.springdataintro.repositories;

import com.example.springdataintro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    Optional<User> getUserByUsername(String username);

    User getUserByAge(Integer age);

    List<User> getUsersByAgeBetween(Integer min, Integer max);
}
