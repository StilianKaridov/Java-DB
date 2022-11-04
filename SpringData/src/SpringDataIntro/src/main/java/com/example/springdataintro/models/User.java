package com.example.springdataintro.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @Column
    private int age;

    @OneToMany
    private List<Account> accounts;

    public User() {
        this.accounts = new ArrayList<>();
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(this.accounts);
    }
}
