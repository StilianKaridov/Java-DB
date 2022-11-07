package com.example.springdataintro_exercise.repositories;

import com.example.springdataintro_exercise.domain.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author getAuthorById(int id);

    Set<Author> findDistinctByBooksReleaseDateBefore(LocalDate date);
}
