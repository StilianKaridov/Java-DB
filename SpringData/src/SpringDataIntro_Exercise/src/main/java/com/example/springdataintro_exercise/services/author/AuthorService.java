package com.example.springdataintro_exercise.services.author;

import com.example.springdataintro_exercise.domain.models.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    void add(Author author);

    Author getRandomAuthor();

    boolean isDataSeeded();

    Set<Author> getAllAuthorsWithAtLeastOneBookBefore1990();

    List<Author> getAllAuthorsOrderedByBookCountDesc();
}
