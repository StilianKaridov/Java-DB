package com.example.jsonprocessing.domain.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.jsonprocessing.constants.exceptionMessages.ExceptionsMessages.USER_LAST_NAME_LENGTH;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column
    private int age;

    @OneToMany(targetEntity = Product.class, mappedBy = "seller")
    private List<Product> soldProducts;

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer")
    private List<Product> boughtProducts;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private List<User> friends;

    public User() {
        this.soldProducts = new ArrayList<>();
        this.boughtProducts = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public User(String lastName) {
        this();
        setLastName(lastName);
    }

    public User(String firstName, String lastName, int age) {
        this(lastName);
        this.firstName = firstName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (lastName.length() < 3) {
            throw new IllegalArgumentException(USER_LAST_NAME_LENGTH);
        }

        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Product> getSoldProducts() {
        return Collections.unmodifiableList(soldProducts);
    }

    public List<Product> getBoughtProducts() {
        return Collections.unmodifiableList(boughtProducts);
    }

    public List<User> getFriends() {
        return Collections.unmodifiableList(friends);
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public void setBoughtProducts(List<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }
}
