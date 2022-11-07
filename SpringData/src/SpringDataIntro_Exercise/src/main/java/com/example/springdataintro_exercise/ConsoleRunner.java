package com.example.springdataintro_exercise;

import com.example.springdataintro_exercise.domain.enums.AgeRestriction;
import com.example.springdataintro_exercise.domain.enums.Constant;
import com.example.springdataintro_exercise.domain.enums.EditionType;
import com.example.springdataintro_exercise.domain.models.Author;
import com.example.springdataintro_exercise.domain.models.Book;
import com.example.springdataintro_exercise.domain.models.Category;
import com.example.springdataintro_exercise.services.author.AuthorService;
import com.example.springdataintro_exercise.services.book.BookService;
import com.example.springdataintro_exercise.services.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;

    @Autowired
    public ConsoleRunner(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAuthors();
        seedCategories();
        seedBooks();

//         1.
//        booksAfter2000Year();

//         2.
//        authorsWithAtLeastOneBookBefore1990();

//        3.
//        authorsOrderedByBookCountDesc();

//        4.
//        booksByAuthorNamesOrderedByReleaseDateDescAndTitle();
    }

    private void booksAfter2000Year() {
        this.bookService.getAllBooksAfterYear2000()
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void authorsWithAtLeastOneBookBefore1990() {
        this.authorService.getAllAuthorsWithAtLeastOneBookBefore1990()
                .forEach(a -> System.out.printf("%s %s%n",
                        a.getFirstName(),
                        a.getLastName()));
    }

    private void authorsOrderedByBookCountDesc() {
        this.authorService.getAllAuthorsOrderedByBookCountDesc()
                .forEach(a -> System.out.printf("%s %s %d%n",
                        a.getFirstName(),
                        a.getLastName(),
                        a.getBooks().size()));
    }

    private void booksByAuthorNamesOrderedByReleaseDateDescAndTitle() {
        this.bookService.getAllBooksByAuthorOrderedByReleaseDateDescAndTitle()
                .forEach(b -> System.out.printf("%s %s %d%n",
                        b.getTitle(),
                        b.getReleaseDate(),
                        b.getCopies()));
    }

    private void seedBooks() throws IOException {
        if (!this.bookService.isDataSeeded()) {
            Files.readAllLines(Path.of(Constant.BOOKS_FILE_PATH))
                    .forEach(row -> {
                        String[] data = row.split("\\s+");
                        Author author = authorService.getRandomAuthor();
                        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                        LocalDate releaseDate = LocalDate.parse(data[1],
                                DateTimeFormatter.ofPattern("d/M/yyyy"));
                        int copies = Integer.parseInt(data[2]);
                        BigDecimal price = new BigDecimal(data[3]);
                        AgeRestriction ageRestriction = AgeRestriction
                                .values()[Integer.parseInt(data[4])];
                        String title = Arrays.stream(data)
                                .skip(5)
                                .collect(Collectors.joining(" "));
                        Set<Category> categories = categoryService.getRandomCategories();
                        Book book = new Book(title, editionType, price, releaseDate,
                                ageRestriction, author, categories, copies);
                        bookService.add(book);
                    });
        }
    }

    private void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {
            Files.readAllLines(Path.of(Constant.AUTHORS_FILE_PATH))
                    .forEach(row -> {
                        String[] data = row.split("\\s+");

                        String firstName = data[0];
                        String lastName = data[1];

                        Author author = new Author(firstName, lastName);

                        authorService.add(author);
                    });
        }
    }

    private void seedCategories() throws IOException {
        if (!this.categoryService.isDataSeeded()) {
            Files.readAllLines(Path.of(Constant.CATEGORIES_FILE_PATH))
                    .forEach(row -> {
                        String[] data = row.split("\\s+");

                        String categoryName = data[0];

                        Category category = new Category(categoryName);

                        categoryService.add(category);
                    });
        }
    }
}
