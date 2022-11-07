package com.example.springdataintro_exercise.services.book;

import com.example.springdataintro_exercise.domain.enums.Constant;
import com.example.springdataintro_exercise.domain.models.Book;
import com.example.springdataintro_exercise.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void add(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public Set<Book> getAllBooksAfterYear2000() {
        return this.bookRepository.getBooksByReleaseDateAfter(Constant.YEAR_AFTER);
    }

    @Override
    public Set<Book> getAllBooksByAuthorOrderedByReleaseDateDescAndTitle() {
        return this.bookRepository
                .getBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(Constant.AUTHOR_FIRST_NAME, Constant.AUTHOR_LAST_NAME);
    }

    @Override
    public boolean isDataSeeded() {
        return this.bookRepository.count() > 0;
    }
}
