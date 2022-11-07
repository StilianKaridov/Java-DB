package com.example.springdataintro_exercise.services.book;

import com.example.springdataintro_exercise.domain.models.Book;

import java.util.Set;

public interface BookService {
    void add(Book book);

    boolean isDataSeeded();

    Set<Book> getAllBooksAfterYear2000();

    Set<Book> getAllBooksByAuthorOrderedByReleaseDateDescAndTitle();
}
