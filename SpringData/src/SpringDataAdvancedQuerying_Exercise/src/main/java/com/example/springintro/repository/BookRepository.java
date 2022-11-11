package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> getBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> getBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> getBooksByPriceLessThanOrPriceGreaterThan(BigDecimal minPrice, BigDecimal maxPrice);

    @Query(value = "select b.title from Book b" +
            " where YEAR(b.releaseDate) not in :year")
    List<String> getBooksByReleaseDateYearNotLike(int year);

    List<Book> getBooksByReleaseDateBefore(LocalDate date);

    List<Book> getBooksByTitleContainsIgnoreCase(String string);

    @Query(value = "select b.title from Book b " +
            "join b.author a " +
            "where a.lastName like concat(:pattern, '%')")
    List<String> getBooksByAuthor_LastNameStartsWith(String pattern);

    @Query(value = "select count(b) from Book b " +
            "where length(b.title) > :length")
    Integer countBooksByTitleLengthGreaterThan(int length);

    @Query(value = "select b.title, b.editionType, b.ageRestriction, b.price " +
            "from Book b " +
            "where b.title like :title")
    String getBookByTitle(String title);

    @Query(value = "update Book b " +
            "set b.copies = b.copies + :copies " +
            "where b.releaseDate > :date")
    @Modifying
    Integer updateBookCopies(LocalDate date, int copies);

    Integer deleteBooksByCopiesLessThan(int copies);

    @Procedure(procedureName = "count_books_by_author")
    Integer getCountOfBooksByAuthor(String firstName, String lastName);


}
