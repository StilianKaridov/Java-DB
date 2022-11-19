package com.example.jsonprocessing.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.jsonprocessing.constants.exceptionMessages.ExceptionsMessages.PRODUCT_NAME_LENGTH;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    @ManyToMany
    private List<Category> categories;

    public Product() {
        this.categories = new ArrayList<>();
    }

    public Product(String name, BigDecimal price, User seller) {
        this();
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    public Product(String name, BigDecimal price, User buyer, User seller) {
        this(name, price, seller);
        this.buyer = buyer;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException(PRODUCT_NAME_LENGTH);
        }

        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyerId) {
        this.buyer = buyerId;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User sellerId) {
        this.seller = sellerId;
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
