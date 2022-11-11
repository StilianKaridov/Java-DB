package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner scanner;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        //1.
//        printBooksTitlesByAgeRestriction();

        //2.
//        printBooksTitlesByEditionTypeAndCopies();

        //3.
//        printBooksTitlesAndPrice();

        //4.
//        printBooksTitlesByYearNotIn();

        //5.
//        printBooksReleaseBeforeDate();

        //6.
//        printAuthorsNames();

        //7.
//        printBooksTitlesContainingGivenString();

        //8.
//        printBooksTitles();

        //9.
//        printCountOfBooksWithTitleLengthGreaterThan();

        //10.
//        printCountOfBooksByAuthor();

        //11.
//        printBookInfoForGivenTitle();

        //12.
//        updateBooksCopiesByDate();

        //13.
//        deleteBooksByCopiesLessThan();

        //14.
//        getCountBooksByAuthor();
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

    private void printBooksTitlesByAgeRestriction() {
        String getRestriction = this.scanner.nextLine();

        AgeRestriction ageRestriction = AgeRestriction.valueOf(getRestriction.toUpperCase());

        this.bookService
                .getTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void printBooksTitlesByEditionTypeAndCopies() {
        this.bookService
                .getTitlesByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void printBooksTitlesAndPrice() {
        BigDecimal min = BigDecimal.valueOf(5);
        BigDecimal max = BigDecimal.valueOf(40);

        this.bookService
                .getBooksByPriceLowerThanOrPriceGreaterThan(min, max)
                .forEach(System.out::println);
    }

    private void printBooksTitlesByYearNotIn() {
        int year = Integer.parseInt(this.scanner.nextLine());

        this.bookService
                .getTitlesByYearNotIn(year)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void printBooksReleaseBeforeDate() {
        LocalDate date = LocalDate.parse(this.scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.bookService
                .getBooksReleasedBeforeDate(date)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void printAuthorsNames() {
        String pattern = this.scanner.nextLine();

        this.authorService
                .getAuthorsNamesByFirstNameEndingWith(pattern)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void printBooksTitlesContainingGivenString() {
        String string = this.scanner.nextLine();

        this.bookService
                .getBooksTitlesByContainingString(string)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void printBooksTitles() {
        String pattern = this.scanner.nextLine();

        this.bookService
                .getBooksTitlesByAuthorsLastNameStartingWith(pattern)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void printCountOfBooksWithTitleLengthGreaterThan() {
        int length = Integer.parseInt(this.scanner.nextLine());

        System.out.println(this.bookService
                .getCountOfBooksWithTitleLengthGreaterThan(length));

        this.scanner.close();
    }

    private void printCountOfBooksByAuthor() {
        this.authorService
                .totalCountCopies()
                .forEach(System.out::println);
    }

    private void printBookInfoForGivenTitle() {
        String titleInput = this.scanner.nextLine();

        System.out.println(
                this.bookService
                        .getBookTitle(titleInput));

        this.scanner.close();
    }

    private void updateBooksCopiesByDate() {
        String[] dateInput = this.scanner.nextLine().split(" ");

        String parsedStringDate = String.join("-", dateInput);

        LocalDate date = LocalDate.parse(parsedStringDate,
                DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

        int numberOfCopies = Integer.parseInt(this.scanner.nextLine());

        Integer numberOfAddedCopies = this.bookService
                .updateBookCopies(date, numberOfCopies) * numberOfCopies;

        System.out.println(numberOfAddedCopies);

        this.scanner.close();
    }

    private void deleteBooksByCopiesLessThan() {
        int copies = Integer.parseInt(this.scanner.nextLine());

        System.out.println(this.bookService.deleteBooksByCopiesLessThan(copies));

        this.scanner.close();
    }

    private void getCountBooksByAuthor() {
        String[] fullName = this.scanner.nextLine().split("\\s+");

        String firstName = fullName[0];
        String lastName = fullName[1];

        int countOfBooks = this.bookService
                .getCountOfBooksByAuthorFirstNameLastName(firstName, lastName);

        System.out.printf("%s %s has written %d books%n",
                firstName,
                lastName,
                countOfBooks);

        this.scanner.close();
    }
}
