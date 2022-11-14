package com.example.springdataautomappingobjects_exercise.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToMany
    private Set<Game> games;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(targetEntity = Order.class, mappedBy = "user")
    private Set<Order> orders;

    public User() {
        this.games = new HashSet<>();
        this.orders = new HashSet<>();
    }

    public User(String email, String password, String fullName, boolean isAdmin) {
        this();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public void addGame(Game toAdd) {
        this.games.add(toAdd);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Game> getGames() {
        return Collections.unmodifiableSet(games);
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
