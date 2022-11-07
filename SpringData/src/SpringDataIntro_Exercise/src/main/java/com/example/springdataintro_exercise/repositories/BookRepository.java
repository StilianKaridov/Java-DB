package com.example.springdataintro_exercise.repositories;

import com.example.springdataintro_exercise.domain.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Set<Book> getBooksByReleaseDateAfter(LocalDate date);

    Set<Book> getBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName);
}
