package com.example.springdataautomappingobjects_exercise.repositories;

import com.example.springdataautomappingobjects_exercise.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "select g.title, g.price from Game g")
    List<String> getAllGamesTitleAndPrice();

    @Query(value = "select g from Game g " +
            "where g.title like :title")
    Optional<Game> findGameByTitle(String title);
}
