package com.example.springdataintro_exercise.domain.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "author", targetEntity = Book.class, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {
        this.books = new ArrayList<>();
    }

    public Author(String lastName) {
        this();
        this.lastName = lastName;
    }

    public Author(String firstName, String lastName) {
        this(lastName);
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        if (this.books.isEmpty()) {
            throw new RuntimeException("The author does not have any books!");
        }

        this.books.remove(book);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }
}
