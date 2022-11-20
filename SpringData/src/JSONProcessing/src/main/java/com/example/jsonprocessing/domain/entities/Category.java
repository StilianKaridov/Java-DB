package com.example.jsonprocessing.domain.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.*;

import static com.example.jsonprocessing.constants.exceptionMessages.ExceptionsMessages.CATEGORY_NAME_LENGTH;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @ManyToMany(targetEntity = Product.class, mappedBy = "categories")
    private Set<Product> products;

    public Category() {
        this.products = new HashSet<>();
    }

    public Category(String name) {
        this();
        setName(name);
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
        if (name.length() < 3 || name.length() > 15) {
            throw new IllegalArgumentException(CATEGORY_NAME_LENGTH);
        }

        this.name = name;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }
}
