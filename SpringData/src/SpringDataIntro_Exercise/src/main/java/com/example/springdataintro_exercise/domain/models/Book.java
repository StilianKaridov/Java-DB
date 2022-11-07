package com.example.springdataintro_exercise.domain.models;

import com.example.springdataintro_exercise.domain.enums.AgeRestriction;
import com.example.springdataintro_exercise.domain.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;

    public Book() {
        this.categories = new HashSet<>();
    }

    public Book(String title, EditionType editionType,
                BigDecimal price, int copies, AgeRestriction ageRestriction,
                Author author, Set<Category> categories) {
        this();
        this.title = title;
        this.editionType = editionType;
        this.price = price;
        this.copies = copies;
        this.ageRestriction = ageRestriction;
        this.author = author;
        this.categories = categories;
    }

    public Book(String title, EditionType editionType, BigDecimal price,
                LocalDate releaseDate, AgeRestriction ageRestriction,
                Author author, Set<Category> categories, int copies) {
        this(title, editionType, price, copies, ageRestriction, author, categories);
        this.releaseDate = releaseDate;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        if (this.categories.isEmpty()) {
            throw new RuntimeException("There are no categories for this book!");
        }

        this.categories.remove(category);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }
}
