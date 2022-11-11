package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> getTitlesByAgeRestriction(AgeRestriction ageRestriction);

    List<String> getTitlesByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<String> getBooksByPriceLowerThanOrPriceGreaterThan(BigDecimal min, BigDecimal max);

    List<String> getTitlesByYearNotIn(int year);

    List<String> getBooksReleasedBeforeDate(LocalDate date);

    List<String> getBooksTitlesByContainingString(String string);

    List<String> getBooksTitlesByAuthorsLastNameStartingWith(String pattern);

    Integer getCountOfBooksWithTitleLengthGreaterThan(int length);

    String getBookTitle(String title);

    Integer updateBookCopies(LocalDate date, int copies);

    Integer deleteBooksByCopiesLessThan(int copies);

    Integer getCountOfBooksByAuthorFirstNameLastName(String firstName, String lastName);
}
